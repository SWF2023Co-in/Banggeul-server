package coin.banggeul.member.domain;

import coin.banggeul.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String password;

    @Column(name = "member_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String username, String password, String name, Provider provider, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.role = role;
    }
}
