package board.server.domain.user.repository;

import board.server.domain.user.entitiy.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByKey(String key);
}
