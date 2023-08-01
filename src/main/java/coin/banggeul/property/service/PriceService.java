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
}
