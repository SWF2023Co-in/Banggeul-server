package coin.banggeul.property.domain;

import coin.banggeul.common.YesNo;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaintenanceOptions {

    private YesNo electricity;
    private YesNo gas;
    private YesNo water;
    private YesNo internet;
}
