package coin.banggeul.property.service;

import coin.banggeul.auth.exception.S3ErrorCode;
import coin.banggeul.auth.exception.S3Exception;
import coin.banggeul.common.utils.S3ImageUploader;
import coin.banggeul.property.domain.Property;
import coin.banggeul.property.domain.PropertyImage;
import coin.banggeul.property.domain.PropertyImageRepository;
import coin.banggeul.property.domain.PropertyRepository;
import coin.banggeul.property.dto.PropertyImageSaveDto;
import coin.banggeul.property.exception.PropertyErrorCode;
import coin.banggeul.property.exception.PropertyException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyImageService {

    private final S3ImageUploader imageUploader;
    private final PropertyImageRepository propertyImageRepository;
    private final PropertyRepository propertyRepository;

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

    @Transactional
    public void savePropertyImages(Long propertyId, List<String> urlList, List<PropertyImageSaveDto> dtoList) {
        dtoList.sort(PropertyImageSaveDto::compareTo);
        Property property = getPropertyEntity(propertyId);
        for (String url : urlList) {
            PropertyImageSaveDto saveDto = dtoList.get(urlList.indexOf(url));
            propertyImageRepository.save(saveDto.toEntity(url, property));
        }
    }

    private Property getPropertyEntity(Long propertyId) {
        return propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyException(PropertyErrorCode.PROPERTY_NOT_FOUND));
    }
}
