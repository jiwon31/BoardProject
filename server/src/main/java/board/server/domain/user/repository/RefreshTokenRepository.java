package board.server.domain.user.repository;

import board.server.domain.user.entitiy.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByKey(String key);

    void deleteByKey(String key);
}
