package coin.banggeul.property.dto;

import coin.banggeul.property.domain.Property;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyResponse {

    // 식별자, 전/월세, 도로명/지번 주소, 면적, 가격, 관리비, 방향, 구조, 보증금, 메모, 대출/반려동물/주차/엘리베이터, 옵션
    private Long propertyId;
    private String rentalType;
    private String roadNameAddress;
    private String lotNumberAddress;
    private Double area;
    private Long price;
    private Long maintenanceFee;
    private String direction;
    private Long deposit;
    private String message;

    private String loan;
    private String pet;
    private String parking;
    private String elevator;

    private OptionResponse options;
    private List<String> tags;

    private String average;

    public static PropertyResponse getResponse(Property property, List<String> tags, String average) {

        return new PropertyResponse(
                property.getId(),
                property.getRentalType().getCode(),
                property.getAddress().getRoadNameAddress(),
                property.getAddress().getLotNumberAddress(),
                property.getArea(),
                property.getRentalFee(),
                property.getMaintenanceFee(),
                property.getDirection().getName(),
                property.getDeposit(),
                property.getMessage(),
                property.getOthers().getLoan().getCode(),
                property.getOthers().getPet().getCode(),
                property.getOthers().getParking().getCode(),
                property.getOthers().getElevator().getCode(),
                OptionResponse.toResponse(property.getOptions()),
                tags,
                average
        );

    }

}
