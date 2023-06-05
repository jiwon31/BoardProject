package board.server.domain.like.repository;

import board.server.domain.board.entity.Board;
import board.server.domain.like.entity.Like;
import board.server.domain.user.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndBoard(User user, Board board);

    Optional<Like> findByUserAndBoard(User user, Board board);
}
