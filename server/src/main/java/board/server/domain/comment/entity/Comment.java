package board.server.domain.comment.entity;

import board.server.common.converter.BooleanToYnConverter;
import board.server.common.entitiy.BaseTime;
import board.server.domain.board.entity.Board;
import board.server.domain.user.entitiy.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "board_comment")
@Getter
@NoArgsConstructor
public class Comment extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Column(name = "del_yn")
    @Convert(converter = BooleanToYnConverter.class)
    private Boolean isDeleted;

    @Builder
    public Comment(String content, User user, Board board, Boolean isDeleted) {
        this.content = content;
        this.user = user;
        this.board = board;
        this.isDeleted = isDeleted;
    }

    // 수정
    public void update(String content) {
        this.content = content;
    }

    // 삭제 처리
    public void delete() {
        this.isDeleted = true;
    }
}
