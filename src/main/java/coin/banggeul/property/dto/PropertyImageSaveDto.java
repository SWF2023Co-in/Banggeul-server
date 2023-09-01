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

    public PropertyImage toEntity(String url, Property property) {
        return new PropertyImage(this.getFileName(), url, this.getIndex(), property);
    }

    public int compareTo(PropertyImageSaveDto obj) {
        return (int) (this.index - obj.getIndex());
    }
}
