package coin.banggeul.property.dto;

import coin.banggeul.property.domain.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PropertyResponseInList {
    // 매물 식별자, 태그 리스트, 썸네일, rentalType, deposit, rentalFee, area, roomFloor, roadNameAddress

    private Long propertyId;
    private List<String> tags;
    private String thumbnail;
    private String rentalType;
    private Double deposit;
    private Double rentalFee;
    private Double area;
    private Long roomFloor;
    private String roadNameAddress;

    public static PropertyResponseInList toDto(Property property, List<String> tags, String thumbnail) {
        return new PropertyResponseInList(
                property.getId(),
                tags,
                thumbnail,
                property.getRentalType().getCode(),
                property.getDeposit(),
                property.getRentalFee(),
                property.getArea(),
                property.getRoomFloor(),
                property.getAddress().getRoadNameAddress()
        );
    }
}
