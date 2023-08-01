package coin.banggeul.property.service;

import coin.banggeul.common.exception.EnumErrorCode;
import coin.banggeul.common.exception.EnumException;
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

import java.io.IOException;
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

    private final PriceService priceService;

    @Transactional
    public Property registerProperty(Member landlord, PropertySaveRequest dto) {
        Property property = propertyRepository.save(getPropertyWithLandlord(landlord, dto));
        dto.getTags().forEach(tag -> tagRepository.save(new Tag(tag, property)));
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
            propertyList.sort((a, b) -> b.getCreatedAt().compareTo(a.getCreatedAt()));
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
    public PropertyResponse getPropertyInfo(Long propertyId) throws IOException{
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new PropertyException(PropertyErrorCode.PROPERTY_NOT_FOUND));
        List<String> tags = tagRepository.findAllByProperty(property).stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
        String average;

        if (property.getRoomType() == RoomType.APT) {
            average = priceService.getAptPrice(
                    property.getRentalType(),
                    property.getAddress().getBcode().substring(0, 5), "202307"
            );
        }
        else if (property.getRoomType() == RoomType.OFFICE){
            average = priceService.getOfficetelPrice(
                    property.getRentalType(),
                    property.getAddress().getBcode().substring(0, 5), "202307"
            );
        } else if (property.getRoomType().equals(RoomType.VILLA) || property.getRoomType().equals(RoomType.ONEROOM)){
            average = priceService.getPrice(
                    property.getRentalType(),
                    property.getAddress().getBcode().substring(0, 5), "202307"
            );
        } else {
            throw new PropertyException(PropertyErrorCode.ROOM_TYPE_NOT_FOUND);
        }

        return PropertyResponse.getResponse(property, tags, average);
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
