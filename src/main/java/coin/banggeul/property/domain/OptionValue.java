package coin.banggeul.property.domain;

import coin.banggeul.common.YesNo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionValue {

    @Enumerated(EnumType.STRING)
    private YesNo airConditioner;
    @Enumerated(EnumType.STRING)
    private YesNo fridge;
    @Enumerated(EnumType.STRING)
    private YesNo laundry;
    @Enumerated(EnumType.STRING)
    private YesNo induction;
    @Enumerated(EnumType.STRING)
    private YesNo gasStove;
    @Enumerated(EnumType.STRING)
    private YesNo microwave;
    @Enumerated(EnumType.STRING)
    private YesNo desk;
    @Enumerated(EnumType.STRING)
    private YesNo bookshelf;
    @Enumerated(EnumType.STRING)
    private YesNo bed;
    @Enumerated(EnumType.STRING)
    private YesNo closet;
    @Enumerated(EnumType.STRING)
    private YesNo sink;

}
