package board.server.domain.board.entity;

import board.server.common.converter.BooleanToYnConverter;
import board.server.common.entitiy.BaseTime;
import board.server.domain.user.entitiy.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "del_yn")
    @Convert(converter = BooleanToYnConverter.class)
    private Boolean isDeleted;

    // 조회수
//    @Column(name = "view_count")
//    private int viewCount;


    @Builder
    public Board(String title, String content, User user, Boolean isDeleted) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isDeleted = isDeleted;
    }

    // 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 삭제 처리
    public void delete() {
        this.isDeleted = true;
    }
}
