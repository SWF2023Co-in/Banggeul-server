package coin.banggeul.property.domain;

import coin.banggeul.common.YesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Others {

    @Enumerated(EnumType.STRING)
    private YesNo loan;
    @Enumerated(EnumType.STRING)
    private YesNo pet;
    @Enumerated(EnumType.STRING)
    private YesNo parking;
    @Enumerated(EnumType.STRING)
    private YesNo elevator;
}
