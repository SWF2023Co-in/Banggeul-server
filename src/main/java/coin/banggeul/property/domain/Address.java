package coin.banggeul.property.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @Column(name = "road_name_address")
    private String roadNameAddress; // 도로명 주소

    @Column(name = "lot_number_address")
    private String lotNumberAddress; // 지번 주소

    @Column(name = "detailed_address")  // 상세주소
    private String detailedAddress;

    @Column(name = "b_code")
    private String bcode;    // 법정동 코드

}
