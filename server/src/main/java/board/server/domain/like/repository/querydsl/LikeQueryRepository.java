package board.server.domain.like.repository.querydsl;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static board.server.domain.like.entity.QLike.*;

@Repository
public class LikeQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    public LikeQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<BoardDto> findUserLikedBoardList(Long userId) {
        List<Board> boardList = query
                .select(like.board)
                .from(like)
                .where(like.user.id.eq(userId))
                .fetch();
        return boardMapper.toDtoList(boardList);
    }
}
