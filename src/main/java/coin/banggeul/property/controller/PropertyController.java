package coin.banggeul.property.controller;

import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ResponseUtil;
import coin.banggeul.member.MemberService;
import coin.banggeul.property.dto.PropertyListResponseDto;
import coin.banggeul.property.dto.PropertyResponse;
import coin.banggeul.property.dto.PropertySaveRequest;
import coin.banggeul.property.service.PropertyImageService;
import coin.banggeul.property.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {
    private final PropertyService propertyService;
    private final PropertyImageService propertyImageService;
    private final MemberService memberService;

    @PostMapping
    public BasicResponse<String> createProperty(@RequestPart List<MultipartFile> files, @RequestPart @Valid PropertySaveRequest request) {
        List<String> urls = propertyImageService.uploadPropertyImages(request.getImages(), files);
        Long propertyId = propertyService.registerProperty(memberService.findCurrentMember(), request);
        propertyImageService.savePropertyImages(propertyId, urls, request.getImages());
        return ResponseUtil.success("매물 등록 성공");
    }

    @GetMapping("/entire")
    public BasicResponse<PropertyListResponseDto> getEntireProperties(@RequestParam String bcode,
                                                                      @RequestParam String homeType,
                                                                      @RequestParam String sortBy) {
        log.info("bcode: "+ bcode+", homeType: "+ homeType +", sortBy: "+sortBy);
        return ResponseUtil.success(propertyService.getPropertiesWithoutFilter(bcode, homeType, sortBy));
    }

    @GetMapping("/info")
    public BasicResponse<PropertyResponse> getPropertyInfo(@RequestParam Long id) throws IOException, SAXException {
        PropertyResponse propertyInfo = propertyService.getPropertyInfo(id);
        return ResponseUtil.success(propertyInfo);
    }
}
