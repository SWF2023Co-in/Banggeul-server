package coin.banggeul.property.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id @Column(name = "tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "property")
    private Property property;

    public Tag(String name, Property property) {
        this.name = name;
        this.property = property;
    }
}
