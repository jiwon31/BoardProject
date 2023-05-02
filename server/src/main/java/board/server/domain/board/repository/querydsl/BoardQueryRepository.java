package board.server.domain.board.repository.querydsl;

import board.server.domain.board.entity.Board;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static board.server.domain.board.entity.QBoard.*;
import static board.server.domain.user.entitiy.QUser.*;
import static com.querydsl.core.types.Order.DESC;
import static org.springframework.util.StringUtils.*;

@Repository
//@Slf4j
public class BoardQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public BoardQueryRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Board> search(String title, String content, Pageable pageable) {
        return query
                .selectFrom(board)
                .join(board.user, user)
                .where(titleContains(title), contentContains(content))
                .orderBy(boardSort(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
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

    private OrderSpecifier<?> boardSort(Sort sort) {
        // TODO: 조회수, 좋아요 등 정렬 조건 추가
        return new OrderSpecifier<>(DESC, board.createdAt);
    }
}
