package coin.banggeul.property.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyFilteredListWithPage {

    private Long number;
    private Long pages;
    private Long pageNum;
    private List<PropertyResponseInList> result;
}
