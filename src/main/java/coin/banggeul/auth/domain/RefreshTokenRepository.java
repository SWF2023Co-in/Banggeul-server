package coin.banggeul.auth.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByUsername(String username);
    RefreshToken findByRefreshToken(String refreshToken);
    RefreshToken findByUsernameAndRefreshToken(String username, String refreshToken);
}
