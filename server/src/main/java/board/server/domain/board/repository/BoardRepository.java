package board.server.domain.board.repository;

import board.server.domain.board.entity.Board;
import board.server.domain.user.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByUser(User user);
}
