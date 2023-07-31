package coin.banggeul.property.dto;

import coin.banggeul.property.domain.Property;
import coin.banggeul.property.domain.PropertyImage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyImageSaveDto {

    private String fileName;
    private Long index;

    public static PropertyImage toEntity(String originalName, String url, Long index, Property property) {
        return new PropertyImage(originalName, url, index, property);
    }
}
