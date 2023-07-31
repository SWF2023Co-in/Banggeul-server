package coin.banggeul.property.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PropertyImageRepository extends JpaRepository<PropertyImage, Long> {

    @Query(value = "select i from PropertyImage i where i.property = :property and i.index = 1")
    Optional<PropertyImage> findThumbnail(Property property);
}
