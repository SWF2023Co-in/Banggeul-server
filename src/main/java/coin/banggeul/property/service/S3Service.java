package coin.banggeul.property.service;

import coin.banggeul.auth.exception.S3ErrorCode;
import coin.banggeul.auth.exception.S3Exception;
import coin.banggeul.property.domain.Property;
import coin.banggeul.property.domain.PropertyImage;
import coin.banggeul.property.domain.PropertyImageRepository;
import coin.banggeul.property.dto.PropertyImageSaveDto;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final PropertyImageRepository propertyImageRepository;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Transactional
    public void saveImages(Property property, List<PropertyImageSaveDto> meta, List<MultipartFile> files) {
        log.info("meta size: {}, multipart file size: {}", meta.size(), files.size());
        if (meta.size() == 0)
            return;
        for (MultipartFile file: files) {
            log.info("file name: {}", file.getOriginalFilename());
            PropertyImageSaveDto saveDto = meta.stream().filter(dto -> dto.getFileName().equals(file.getOriginalFilename())).findFirst()
                    .orElseThrow(() -> new S3Exception(S3ErrorCode.IMAGE_FILE_NAME_NOT_MATCHED));
            String url = uploadImage(file);
            PropertyImage imageEntity = PropertyImageSaveDto.toEntity(saveDto.getFileName(), url, saveDto.getIndex(), property);
            propertyImageRepository.save(imageEntity);
        }
    }

    private String uploadImage(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = "properties/" + storeFileName;

        return putObject(multipartFile, key);
    }

    private String putObject(MultipartFile multipartFile, String key) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType("image/png");
        objectMetadata.setContentLength(multipartFile.getSize());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException ex) {
            log.error("이미지 업로드 IOException");
            throw new S3Exception(S3ErrorCode.IMAGE_UPLOAD_FAILED);
        }

        return amazonS3Client.getUrl(bucket, key).toString();
    }
}
