package coin.banggeul.property.domain;

import coin.banggeul.property.dto.FilterValue;

import java.util.List;

public interface PropertyCustomRepository {

    List<Property> findAllWithFilter(FilterValue filter);
}
