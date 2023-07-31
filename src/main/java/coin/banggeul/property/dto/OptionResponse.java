package coin.banggeul.property.dto;

import coin.banggeul.property.domain.OptionValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionResponse {

    private String airConditioner;
    private String fridge;
    private String laundry;
    private String induction;
    private String gasStove;
    private String microwave;
    private String desk;
    private String bookshelf;
    private String bed;
    private String closet;
    private String sink;

    public static OptionResponse toResponse(OptionValue option) {
        return new OptionResponse(
                option.getAirConditioner().getCode(),
                option.getFridge().getCode(),
                option.getLaundry().getCode(),
                option.getInduction().getCode(),
                option.getGasStove().getCode(),
                option.getMicrowave().getCode(),
                option.getDesk().getCode(),
                option.getBookshelf().getCode(),
                option.getBed().getCode(),
                option.getCloset().getCode(),
                option.getSink().getCode()
                );
    }

}
