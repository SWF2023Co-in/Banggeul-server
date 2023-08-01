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
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PriceService {

    @Value("${open.key}")
    private String key;
    @Value("${open.url.apt}")
    private String aptUrl;
    @Value("${open.url.officetel}")
    private String officetelUrl;
    @Value("${open.url.other}")
    private String otherUrl;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    @Transactional
    public String getAptPrice(RentalType rentalType, String code, String date) throws IOException {
        List<AptItem> items =  parseAptJson(getResponse(aptUrl, code, date));
        return getAptAverage(rentalType, items);
    }

    @Transactional
    public String getOfficetelPrice(RentalType rentalType, String code, String date) throws IOException {
        List<OtherItem> items = parseOtherJson(getResponse(officetelUrl, code, date));
        return getAverage(rentalType, items);

    }

    @Transactional
    public String getPrice(RentalType rentalType, String code, String date) throws IOException {
        List<OtherItem> items  = parseOtherJson(getResponse(otherUrl, code, date));
        return getAverage(rentalType, items);
    }

    private String getAverage(RentalType rentalType, List<OtherItem> items) {
        if (rentalType == RentalType.LUMPSUM)
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() == 0)
                    .mapToLong(i -> Long.parseLong(i.getDeposit().replace(",", ""))).average().getAsDouble());
        else
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() != 0)
                    .mapToLong(OtherItem::getMonthlyRent).average().getAsDouble());
    }

    private String getAptAverage(RentalType rentalType, List<AptItem> items) {
        if (rentalType == RentalType.LUMPSUM)
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() == 0)
                    .mapToLong(i -> Long.parseLong(i.getDeposit().replace(",", ""))).average().getAsDouble());
        else
            return String.format("%.2f", items.stream()
                    .filter(i -> i.getMonthlyRent() != 0)
                    .mapToLong(AptItem::getMonthlyRent).average().getAsDouble());
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

    private List<AptItem> parseAptJson(String text) throws IOException{
        OpenApiResponseApt value = mapper.readValue(text, OpenApiResponseApt.class);
        log.info(value.getResponse().getHeader().getResultMsg());
        return value.getResponse().getBody().getItems().getItem();
    }

    private List<OtherItem> parseOtherJson(String text) throws IOException{
        OpenApiResponseOther value = mapper.readValue(text, OpenApiResponseOther.class);
        log.info(value.getResponse().getHeader().getResultMsg());
        return value.getResponse().getBody().getItems().getItem();
    }
}
