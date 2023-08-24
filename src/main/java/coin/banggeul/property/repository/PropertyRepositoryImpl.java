package coin.banggeul.property.repository;

import coin.banggeul.property.domain.Property;
import coin.banggeul.property.domain.PropertyCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PropertyRepositoryImpl implements PropertyCustomRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Property> findAllWithFilter() {
        return new ArrayList<>();
    }
}
