package coin.banggeul.property.service;

import coin.banggeul.auth.exception.S3ErrorCode;
import coin.banggeul.auth.exception.S3Exception;
import coin.banggeul.common.utils.S3ImageUploader;
import coin.banggeul.property.dto.PropertyImageSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final S3ImageUploader imageUploader;

    @Transactional
    public List<String> uploadPropertyImages(List<PropertyImageSaveDto> dtoList, List<MultipartFile> files) {
        validateDtoList(dtoList, files);
        return imageUploader.uploadImages(files);
    }

    private void validateDtoList(List<PropertyImageSaveDto> dtoList, List<MultipartFile> files) {
        dtoList.sort(PropertyImageSaveDto::compareTo);
        for (PropertyImageSaveDto dto : dtoList) {
            files.stream().filter(file -> file.getOriginalFilename().equals(dto.getFileName())).findFirst()
                    .orElseThrow(() -> new S3Exception(S3ErrorCode.IMAGE_FILE_NAME_NOT_MATCHED));
        }
    }
}
