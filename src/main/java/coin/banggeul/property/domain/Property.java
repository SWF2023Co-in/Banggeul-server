package coin.banggeul.property.domain;

import coin.banggeul.common.BaseTimeEntity;
import coin.banggeul.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Property extends BaseTimeEntity {

    @Id @Column(name = "property_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Member landlord;

    @Enumerated(EnumType.STRING)
    private RentalType rentalType;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "property_area")
    private Double area;

    private Double rentalFee;
    private Double maintenanceFee;

    @Enumerated(EnumType.STRING)
    private NSEW direction;

    @Enumerated(EnumType.STRING)
    private OS structure;

    private Double deposit;

    private String message;

    @Embedded
    private Others others;

    private LocalDate movingInDate;

    private Long roomFloor;
    private Long buildingFloor;

    @Embedded
    private OptionValue options;

    @Embedded
    private DocumentRegistrationYn documentYn;

    @Builder
    public Property(Member landlord,
                    RentalType rentalType,
                    Address address,
                    RoomType roomType,
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
                    DocumentRegistrationYn documentYn) {

        this.landlord = landlord;
        this.rentalType = rentalType;
        this.address = address;
        this.roomType = roomType;
        this.area = area;
        this.rentalFee = rentalFee;
        this.maintenanceFee = maintenanceFee;
        this.direction = direction;
        this.structure = structure;
        this.deposit = deposit;
        this.message = message;
        this.others = others;
        this.movingInDate = movingInDate;
        this.roomFloor = roomFloor;
        this.buildingFloor = buildingFloor;
        this.options = options;
        this.documentYn = documentYn;
    }
}
