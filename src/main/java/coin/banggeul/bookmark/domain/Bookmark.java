package coin.banggeul.bookmark.domain;

import coin.banggeul.member.domain.Member;
import coin.banggeul.property.domain.Property;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @Column(name = "bookmark_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_member")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookmark_property")
    private Property property;

    public Bookmark(Member member, Property property) {
        this.member = member;
        this.property = property;
    }
}
