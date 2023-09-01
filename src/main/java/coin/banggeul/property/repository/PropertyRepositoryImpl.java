package coin.banggeul.property.repository;

import coin.banggeul.common.YesNo;
import coin.banggeul.property.domain.*;
import coin.banggeul.property.dto.FilterValue;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyCustomRepository {

    private final JPAQueryFactory query;
    QProperty qProperty = QProperty.property;

    @Override
    public List<Property> findAllWithFilter(FilterValue filter) {
        BooleanBuilder builder = new BooleanBuilder();

        return query
                .selectFrom(qProperty)
                .where(rentalTypeEq(filter.getRentalType()),
                        homeTypeEq(filter.getHomeType()),
                        areaInRange(filter.getAreaMin(), filter.getAreaMax()),
                        depositInRange(filter.getDepositMin(), filter.getDepositMax()),
                        optionsExisted(filter.getOptions())
                )
                .fetch();
    }

    private Predicate rentalTypeEq(String rentalTypeParam) {
        RentalType rentalType = RentalType.of(rentalTypeParam);
        return rentalType != null? qProperty.rentalType.eq(rentalType) : null;
    }

    private Predicate homeTypeEq(String roomTypeParam) {
        RoomType roomType = RoomType.of(roomTypeParam);
        return roomType != null? qProperty.roomType.eq(roomType) : null;
    }

    private Predicate areaInRange(Long minPyeong, Long maxPyeong) {
        if (minPyeong == null || maxPyeong == null)
            return null;

        return qProperty.area.between(toM2(minPyeong), toM2(maxPyeong));
    }

    private Double toM2(Long pyeong) {
        return Double.parseDouble(String.format("%.2f", pyeong/3.3));
    }

    private Predicate depositInRange(Double depositMin, Double depositMax) {
        if (depositMin == null || depositMax == null)
            return null;
        return qProperty.deposit.between(depositMin, depositMax);
    }

    private Predicate optionsExisted(List<String> options) {
        if (options == null)
            return null;
        BooleanBuilder query = new BooleanBuilder();
        for (String option : options) {
            query.and(matchOption(option));
        }
        return query;
    }

    private Predicate matchOption(String option) {
        return switch (option) {
            case "airConditioner" -> qProperty.options.airConditioner.eq(YesNo.YES);
            case "fridge" -> qProperty.options.fridge.eq(YesNo.YES);
            case "laundry" -> qProperty.options.laundry.eq(YesNo.YES);
            case "induction" -> qProperty.options.induction.eq(YesNo.YES);
            case "gasStove" -> qProperty.options.gasStove.eq(YesNo.YES);
            case "microwave" -> qProperty.options.microwave.eq(YesNo.YES);
            case "desk" -> qProperty.options.desk.eq(YesNo.YES);
            case "bookshelf" -> qProperty.options.bookshelf.eq(YesNo.YES);
            case "bed" -> qProperty.options.bed.eq(YesNo.YES);
            case "closet" -> qProperty.options.closet.eq(YesNo.YES);
            case "sink" -> qProperty.options.sink.eq(YesNo.YES);
            default -> null;
        };
    }

}
