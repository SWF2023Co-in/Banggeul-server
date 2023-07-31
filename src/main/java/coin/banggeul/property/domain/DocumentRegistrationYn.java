package coin.banggeul.property.domain;

import coin.banggeul.common.YesNo;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DocumentRegistrationYn {

    @Enumerated(EnumType.STRING)
    private YesNo registryInfoYn;
    @Enumerated(EnumType.STRING)
    private YesNo buildingLedgerYn;
}
