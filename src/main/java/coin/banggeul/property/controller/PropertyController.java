package coin.banggeul.property.controller;

import coin.banggeul.common.response.BasicResponse;
import coin.banggeul.common.response.ResponseUtil;
import coin.banggeul.member.MemberService;
import coin.banggeul.property.domain.Property;
import coin.banggeul.property.dto.PropertyListResponseDto;
import coin.banggeul.property.dto.PropertyResponse;
import coin.banggeul.property.dto.PropertySaveRequest;
import coin.banggeul.property.service.PropertyService;
import coin.banggeul.property.service.S3Service;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/properties")
@RequiredArgsConstructor
@Slf4j
public class PropertyController {

    private final PropertyService propertyService;
    private final MemberService memberService;
    private final S3Service s3Service;

    @PostMapping
    public BasicResponse<String> createProperty(@RequestPart List<MultipartFile> files, @RequestPart @Valid PropertySaveRequest request) {
        Property property = propertyService.registerProperty(memberService.findCurrentMember(), request);
        s3Service.saveImages(property, request.getImages(), files);
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
    public BasicResponse<PropertyResponse> getPropertyInfo(@RequestParam Long id) {

        return ResponseUtil.success(propertyService.getPropertyInfo(id));
        // TODO: 부동산 시세 연결해서 합치기
    }
}
