package coin.banggeul.property.dto;

import coin.banggeul.member.domain.Member;
import coin.banggeul.property.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertySaveRequest {

    // STEP 1
    @NotBlank
    private String rentalType;
    @NotBlank
    private String homeType;
    @NotBlank
    private String roadNameAddress;
    @NotBlank
    private String lotNumberAddress;
    @NotBlank
    private String bcode;
    @NotBlank
    private String detailedAddress;

    // STEP 2
    @NotNull
    private Double deposit;
    @NotNull
    private Double rentalFee;
    @NotBlank
    private String structure;
    @NotBlank
    private String direction;

    @NotNull
    private Double maintenanceFee;
    @NotBlank
    private String electricity;
    @NotBlank
    private String gas;
    @NotBlank
    private String water;
    @NotBlank
    private String internet;

    @NotNull
    private Double area;
    private Long buildingFloor;
    @NotNull
    private Long roomFloor;

    @NotBlank
    private String airConditioner;
    @NotBlank
    private String fridge;
    @NotBlank
    private String laundry;
    @NotBlank
    private String induction;
    @NotBlank
    private String gasStove;
    @NotBlank
    private String microwave;
    @NotBlank
    private String desk;
    @NotBlank
    private String bookshelf;
    @NotBlank
    private String bed;
    @NotBlank
    private String closet;
    @NotBlank
    private String sink;

    @NotBlank
    private String loan;
    @NotBlank
    private String pet;
    @NotBlank
    private String parking;
    @NotBlank
    private String elevator;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate movingInDate;

    // STEP 3
    @NotBlank
    private String registryYn;
    @NotBlank
    private String buildingLedgerYn;

    // STEP 4
    private String message;
    private List<String> tags;

    private List<PropertyImageSaveDto> images;

    public static Property toEntity(RentalType rentalType,
                                    RoomType roomType,
                                    Address address,
                                    Double area,
                                    Double rentalFee,
                                    Double maintenanceFee,
                                    NSEW direction,
                                    OS structure,
                                    Double deposit,
                                    String message,
                                    Others others,
                                    LocalDate movingInDate,
                                    Long roomFloor,
                                    Long buildingFloor,
                                    OptionValue options,
                                    DocumentRegistrationYn documentYn,
                                    Member member) {
        return Property.builder()
                .rentalType(rentalType)
                .roomType(roomType)
                .address(address)
                .area(area)
                .rentalFee(rentalFee)
                .maintenanceFee(maintenanceFee)
                .direction(direction)
                .structure(structure)
                .deposit(deposit)
                .message(message)
                .others(others)
                .movingInDate(movingInDate)
                .roomFloor(roomFloor)
                .buildingFloor(buildingFloor)
                .options(options)
                .documentYn(documentYn)
                .landlord(member)
                .build();
    }
}
