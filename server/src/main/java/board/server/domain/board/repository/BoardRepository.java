package board.server.domain.board.repository;

import board.server.domain.board.entity.Board;
import board.server.domain.user.entitiy.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
