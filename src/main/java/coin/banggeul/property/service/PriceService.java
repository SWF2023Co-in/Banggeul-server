package coin.banggeul.property.service;

import coin.banggeul.property.domain.RentalType;
import coin.banggeul.property.domain.RoomType;
import coin.banggeul.property.exception.PropertyErrorCode;
import coin.banggeul.property.exception.PropertyException;
import coin.banggeul.property.openapi.apt.AptItem;
import coin.banggeul.property.openapi.apt.OpenApiResponseApt;
import coin.banggeul.property.openapi.other.OpenApiResponseOther;
import coin.banggeul.property.openapi.other.OtherItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.*;

@Slf4j
@Service
public class PriceService {

    public PriceService(RestTemplate restTemplate) {
        Map<String, String> tagNames = new HashMap<>();
        tagNames.put("보증금액", "deposit");
        tagNames.put("월세금액", "monthlyRent");
        tagNames.put("전용면적", "area");

        this.restTemplate = restTemplate;
    }

    @Value("${open.key}")
    private String key;

    private final RestTemplate restTemplate;

    // 시세 가져오기 getPrice
    // 칼럼명 영어로 바꾸기
    // 현재 연월 가져와서 입력
    // 전월세 필터, 면적 필터(오차범위)
    @Transactional
    public String getAptPrice(RentalType rentalType, RoomType roomType, String code, String date) throws IOException {
        String s = getResponse(
                "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent",
                code, date
        );
        assert s != null;
        log.info("test: {}", s);
        List<AptItem> items = null;
        if (roomType.equals(RoomType.APT)) {
            items = parseAptJson(getResponse(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcAptRent",
                    code, date
            ));
        } else {
            throw new PropertyException(PropertyErrorCode.ROOM_TYPE_NOT_FOUND);
        }
        //응답 string으로 가져와서 파싱해서 필요한 정보만 저장한 객체 리스트 반환
        // 조건에 맞게 필터링해서 리스트로 collect
        // 남은 원소들의 price average
        //(result.stream().map(Item::getMonthlyRent).toList());
        if (rentalType == RentalType.LUMPSUM)
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() == 0)
                    .mapToLong(i -> Long.parseLong(i.getDeposit().replace(",", ""))).average().getAsDouble());
        else
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() != 0)
                    .mapToLong(AptItem::getMonthlyRent).average().getAsDouble());
    }

    @Transactional
    public String getPrice(RentalType rentalType, RoomType roomType, String code, String date) throws IOException {
        String s = getResponse(
                "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent",
                code, date
        );
        assert s != null;
        log.info("test: {}", s);
        List<OtherItem> items = null;
        if (roomType.equals(RoomType.OFFICE)) {
            items = parseOtherJson(getResponse(
                    "http://openapi.molit.go.kr/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcOffiRent",
                    code, date
            ));
        } else if (roomType.equals(RoomType.VILLA) || roomType.equals(RoomType.ONEROOM)) {
            items = parseOtherJson(getResponse(
                    "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvcRHRent",
                    code, date
            ));
        }  else {
            throw new PropertyException(PropertyErrorCode.ROOM_TYPE_NOT_FOUND);
        }

        if (rentalType == RentalType.LUMPSUM)
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() == 0)
                    .mapToLong(i -> Long.parseLong(i.getDeposit().replace(",", ""))).average().getAsDouble());
        else
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() != 0)
                    .mapToLong(OtherItem::getMonthlyRent).average().getAsDouble());
    }

    private String getResponse(String url, String code, String date) {

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);


        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("LAWD_CD", code)
                .queryParam("DEAL_YMD", date)
                .queryParam("serviceKey", key)
                .build(true)
                .toUri();

        try {
            log.info("uri: {}", uri);
            ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                return response.getBody();
            }
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException();
        }
        return null;
    }

    public List<AptItem> parseAptJson(String text) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        OpenApiResponseApt value = mapper.readValue(text, OpenApiResponseApt.class);
        log.info(value.getResponse().getHeader().getResultMsg());
        return value.getResponse().getBody().getItems().getItem();
    }

    public List<OtherItem> parseOtherJson(String text) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        OpenApiResponseOther value = mapper.readValue(text, OpenApiResponseOther.class);
        log.info(value.getResponse().getHeader().getResultMsg());
        return value.getResponse().getBody().getItems().getItem();
    }

/**
    public List<Item> parse(String text) throws IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        ArrayList<Item> itemList = new ArrayList<>();
        Document doc =  builder.parse(new InputSource(new StringReader(text)));
        NodeList items = doc.getElementsByTagName("items");
        for (Node item = items.item(0).getFirstChild(); item != null; item.getNextSibling()) {
            Long deposit = null;
            Long monthlyRent = null;
            Double area = null;
            if (item.getNodeName().equals("보증금액")) {
                deposit = Long.parseLong(item.getFirstChild().toString());
            }
            if (item.getNodeName().equals("월세금액")) {
                monthlyRent = Long.parseLong(item.getFirstChild().toString());
            }
            if (item.getNodeName().equals("전용면적")) {
                area = Double.parseDouble(item.getFirstChild().toString());
            }
            Item element = new Item(deposit, monthlyRent, area);
            itemList.add(element);
            log.info("하나의 항목: " + element);
        }

        return itemList;
    }
 */
}
