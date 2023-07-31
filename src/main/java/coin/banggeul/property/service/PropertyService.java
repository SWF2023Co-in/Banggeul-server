package coin.banggeul.property.service;

import coin.banggeul.common.BaseTimeEntity;
import coin.banggeul.common.EnumErrorCode;
import coin.banggeul.common.EnumException;
import coin.banggeul.common.YesNo;
import coin.banggeul.member.domain.Member;
import coin.banggeul.property.domain.*;
import coin.banggeul.property.dto.PropertyListResponseDto;
import coin.banggeul.property.dto.PropertyResponse;
import coin.banggeul.property.dto.PropertyResponseInList;
import coin.banggeul.property.dto.PropertySaveRequest;
import coin.banggeul.property.exception.PropertyErrorCode;
import coin.banggeul.property.exception.PropertyException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final TagRepository tagRepository;
    private final PropertyImageRepository propertyImageRepository;

    /**
     * Member landlord,
     *                     RentalType rentalType,
     *                     RoomType roomType,
     *                     Address address,
     *                     Long area,
     *                     Long rentalFee,
     *                     Long maintenanceFee,
     *                     NSEW direction,
     *                     OS structure,
     *                     Long deposit,
     *                     String message,
     *                     Others others,
     *                     LocalDate movingInDate,
     *                     String roomFloor,
     *                     String buildingFloor,
     *                     OptionValue options,
     *                     DocumentRegistrationYn documentYn
     * */

    @Transactional
    public Property registerProperty(Member landlord, PropertySaveRequest dto) {
        Property property = propertyRepository.save(getPropertyWithLandlord(landlord, dto));
        for (String tag : dto.getTags()) {
            tagRepository.save(new Tag(tag, property));
        }
        return property;

    }

    @Transactional
    public PropertyListResponseDto getPropertiesWithoutFilter(String bcode, String homeType, String sortBy) {
        List<Property> propertyList;
        log.info("bcode: "+ bcode+", homeType: "+ homeType +", sortBy: "+sortBy);
        // 쿼리
        if (homeType.equals("none"))
            propertyList = propertyRepository.findAllByBcode(bcode);
        else
            propertyList = propertyRepository.findAllByBcodeAndHomeType(bcode, RoomType.of(homeType));

        //정렬: recent(내림차순), price(오름차순), area(오름차순)
        if (sortBy.equals("recent"))
            propertyList.sort((a,b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
        else if (sortBy.equals("price"))
            propertyList.sort(Comparator.comparing(Property::getRentalFee));
        else if (sortBy.equals("area"))
            propertyList.sort(Comparator.comparing(Property::getArea));
        else
            throw new EnumException(EnumErrorCode.ENUM_INVALID_STRING);

        PropertyListResponseDto response = new PropertyListResponseDto((long) propertyList.size());

        // dto 변환
        for (Property property : propertyList) {
            List<String> tags = tagRepository.findAllByProperty(property).stream().map(Tag::getName).collect(Collectors.toList());
            PropertyImage thumbnail = propertyImageRepository.findThumbnail(property)
                    .orElseThrow(() -> new PropertyException(PropertyErrorCode.THUMBNAIL_NOT_FOUND));
            response.getProperties().add(PropertyResponseInList.toDto(property, tags, thumbnail.getUrl()));
        }
        return response;
    }

    @Transactional
    public PropertyResponse getPropertyInfo(Long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyException(PropertyErrorCode.PROPERTY_NOT_FOUND));
        List<String> tags = tagRepository.findAllByProperty(property).stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        return PropertyResponse.getResponse(property, tags);
    }

    private Property getPropertyWithLandlord(Member landlord, PropertySaveRequest dto) {
        return PropertySaveRequest.toEntity(
                RentalType.of(dto.getRentalType()),
                RoomType.of(dto.getHomeType()),
                new Address(dto.getRoadNameAddress(), dto.getLotNumberAddress(), dto.getDetailedAddress(), dto.getBcode()),
                dto.getArea(),
                dto.getRentalFee(),
                dto.getMaintenanceFee(),
                NSEW.of(dto.getDirection()),
                OS.of(dto.getStructure()),
                dto.getDeposit(),
                dto.getMessage(),
                new Others(
                        YesNo.of(dto.getLoan()),
                        YesNo.of(dto.getPet()),
                        YesNo.of(dto.getParking()),
                        YesNo.of(dto.getElevator())
                ),
                dto.getMovingInDate(),
                dto.getRoomFloor(),
                dto.getBuildingFloor(),
                new OptionValue(
                        YesNo.of(dto.getAirConditioner()),
                        YesNo.of(dto.getFridge()),
                        YesNo.of(dto.getLaundry()),
                        YesNo.of(dto.getInduction()),
                        YesNo.of(dto.getGasStove()),
                        YesNo.of(dto.getMicrowave()),
                        YesNo.of(dto.getDesk()),
                        YesNo.of(dto.getBookshelf()),
                        YesNo.of(dto.getBed()),
                        YesNo.of(dto.getCloset()),
                        YesNo.of(dto.getSink())
                ),
                new DocumentRegistrationYn(
                        YesNo.of(dto.getRegistryYn()),
                        YesNo.of(dto.getBuildingLedgerYn())
                ),
                landlord
        );
    }
}
