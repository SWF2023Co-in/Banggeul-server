package coin.banggeul.property.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PropertyImage {

    @Id @Column(name = "property_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image_name")
    private String originalName;

    @Column(name = "image_url")
    private String url;

    @Column(name = "image_index")
    private Long index;

    @JoinColumn(name = "property_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;

    public PropertyImage(String originalName, String url, Long index, Property property) {
        this.originalName = originalName;
        this.url = url;
        this.index = index;
        this.property = property;
    }
}
