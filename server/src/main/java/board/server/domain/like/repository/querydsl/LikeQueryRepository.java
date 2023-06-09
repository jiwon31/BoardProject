package board.server.domain.like.repository.querydsl;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static board.server.domain.like.entity.QLike.*;
import static com.querydsl.core.types.Order.*;

@Repository
public class LikeQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    public LikeQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<BoardDto> findUserLikedBoardList(Long userId, Pageable pageable) {
        List<Board> boards = query
                .select(like.board)
                .from(like)
                .where(like.user.id.eq(userId))
                .orderBy(new OrderSpecifier<>(DESC, like.createdAt))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(like.board.count())
                .from(like)
                .where(like.user.id.eq(userId));

        List<BoardDto> boardList = boardMapper.toDtoList(boards);
        return PageableExecutionUtils.getPage(boardList, pageable, countQuery::fetchOne);
    }
}
