package coin.banggeul.property.domain;

import java.util.List;

public interface PropertyCustomRepository {

    List<Property> findAllWithFilter();
}
