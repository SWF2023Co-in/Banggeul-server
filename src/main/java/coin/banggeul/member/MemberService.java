package coin.banggeul.member;

import coin.banggeul.member.domain.Member;
import coin.banggeul.member.domain.MemberRepository;
import coin.banggeul.member.exception.MemberErrorCode;
import coin.banggeul.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member findCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return member;
    }
}
