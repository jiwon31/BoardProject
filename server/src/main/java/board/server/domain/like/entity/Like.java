package board.server.domain.like.entity;

import board.server.common.entitiy.BaseTime;
import board.server.domain.board.entity.Board;
import board.server.domain.user.entitiy.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "board_like")
@Getter
@NoArgsConstructor
public class Like extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id", nullable = false,
            foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @Builder
    public Like(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}