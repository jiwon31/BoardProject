package board.server.domain.board.repository.querydsl;

import board.server.domain.board.dto.BoardDto;
import board.server.domain.board.entity.Board;
import board.server.domain.board.mapper.BoardMapper;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static board.server.domain.board.entity.QBoard.*;
import static board.server.domain.user.entitiy.QUser.*;
import static com.querydsl.core.types.Order.DESC;
import static org.springframework.data.domain.Sort.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class BoardQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;
    private final BoardMapper boardMapper = Mappers.getMapper(BoardMapper.class);

    public BoardQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<BoardDto> search(String title, String content, Pageable pageable) {
        List<Board> boards = query
                .selectFrom(board)
                .join(board.user, user)
                .where(titleContains(title), contentContains(content))
                .orderBy(boardSort(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = query
                .select(board.count())
                .from(board)
                .where(titleContains(title), contentContains(content));

        List<BoardDto> boardList = boardMapper.toDtoList(boards);
        return PageableExecutionUtils.getPage(boardList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleContains(String title) {
        if (!hasText(title)) {
            return null;
        }
        return board.title.contains(title);
    }

    private BooleanExpression contentContains(String content) {
        if (!hasText(content)) {
            return null;
        }
        return board.content.contains(content);
    }

    private OrderSpecifier<?> boardSort(Pageable pageable) {
        for (Order order : pageable.getSort()) {
            if (order.getProperty().equals("likeCnt")) {
                return new OrderSpecifier<>(DESC, board.likeCount);
            }
        }
        return new OrderSpecifier<>(DESC, board.createdAt);
    }
}
