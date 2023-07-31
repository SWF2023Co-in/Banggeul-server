package coin.banggeul.member.domain;

import lombok.*;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("GUEST", "손님"),
    USER("USER", "일반 사용자"),
    ADMIN("ADMIN", "관리자");

    private final String code;
    private final String title;

    public static Role of(String name) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getCode().equals(name))
                .findAny()
                .orElse(GUEST);
    }
}
