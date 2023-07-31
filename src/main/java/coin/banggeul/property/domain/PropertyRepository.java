package coin.banggeul.property.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query(value = "select p from Property p where p.address.bcode = :bcode and p.roomType = :homeType")
    List<Property> findAllByBcodeAndHomeType(String bcode, RoomType homeType);

    @Query(value = "select p from Property p where p.address.bcode  = :bcode")
    List<Property> findAllByBcode(String bcode);
}
