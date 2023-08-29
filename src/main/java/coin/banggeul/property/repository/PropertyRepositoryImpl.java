package coin.banggeul.property.repository;

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
                .where()
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
        return Double.parseDouble(String.format(".2f", pyeong/3.3));
    }

    private Predicate optionsExisted(List<String> options) {
        if (options == null)
            return null;
        return qProperty.options.isNotNull();
    }

}
