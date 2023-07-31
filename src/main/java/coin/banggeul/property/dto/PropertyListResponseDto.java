package coin.banggeul.property.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyListResponseDto {

    private Long number; // 결과 개수
    private List<PropertyResponseInList> properties;

    public PropertyListResponseDto(Long number) {
        this.number = number;
        this.properties = new ArrayList<>();
    }
}
