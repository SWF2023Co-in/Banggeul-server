package coin.banggeul.common.utils;

import coin.banggeul.auth.exception.S3ErrorCode;
import coin.banggeul.auth.exception.S3Exception;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private static final String PROPERTIES_PATH = "properties/";

    @Transactional
    public List<String> uploadImages(List<MultipartFile> files) {
        ArrayList<String> urls = new ArrayList<>();
        if (files.size() == 0)
            return urls;
        files.forEach(file -> {
            log.info("file name: {}", file.getOriginalFilename());
            urls.add(uploadPropertyImage(file));
        });
        return urls;
    }

    private String uploadPropertyImage(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String storeFileName = UUID.randomUUID() + "." + ext;
        String key = PROPERTIES_PATH + storeFileName;

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
