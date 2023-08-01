package coin.banggeul.property.openapi.other;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OtherItem {

    @JsonProperty("갱신요구권사용")
    private String userRequestRenewalContractRight;
    @JsonProperty("건축년도")
    private Long buildYear;
    @JsonProperty("계약구분")
    private String contractType;
    @JsonProperty("계약기간")
    private String termOfContract;
    @JsonProperty("년")
    private Long dealYear;
    @JsonProperty("법정동")
    private String dong;
    @JsonProperty("보증금액")
    private String deposit;
    @JsonProperty("연립다세대")
    private String apartmentName;
    @JsonProperty("월")
    private Long dealMonth;
    @JsonProperty("월세금액")
    private Long monthlyRent;
    @JsonProperty("일")
    private Long dealDay;
    @JsonProperty("전용면적")
    private Double areaForExclusiveUse;
    @JsonProperty("종전계약보증금")
    private String previousDeposit;
    @JsonProperty("종전계약월세")
    private String previousMonthlyRent;
    @JsonProperty("지번")
    private String jibun;
    @JsonProperty("지역코드")
    private Long regionalCode;
    @JsonProperty("층")
    private Long floor;

    /**
     * response
     *      header
     *          resultCode
     *          resultMsg
     *      body
     *          items
     *              item (리스트)
     *                  갱신요구권사용 string
     *                  건축년도 long
     *                  계약구분 string
     *                  계약기간 string
     *                  년 long
     *                  단지 string
     *                  법정동 string
     *                  보증금 string
     *                  시군구 string
     *                  월 long
     *                  월세 long
     *                  일 string
     *                  전용면적 double
     *                  종전계약보증금 string
     *                  종전계약월세 string
     *                  지번 string
     *                  지역코드 long
     *                  층 long
     *          numOfRows
     *          pageNo
     *          totalCount
     * */
}
