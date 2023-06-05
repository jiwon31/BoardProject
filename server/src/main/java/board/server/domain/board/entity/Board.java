package board.server.domain.board.entity;

import board.server.common.converter.BooleanToYnConverter;
import board.server.common.entitiy.BaseTime;
import board.server.domain.board.dto.BoardDto;
import board.server.domain.user.entitiy.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(name = "del_yn")
    @Convert(converter = BooleanToYnConverter.class)
    private Boolean isDeleted;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "like_count")
    private int likeCount;

    @Builder
    public Board(String title, String content, User user, Boolean isDeleted, int viewCount, int likeCount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isDeleted = isDeleted;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
    }

    // 수정
    public void update(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }

    // 삭제 처리
    public void delete() {
        this.isDeleted = true;
    }

    // 조회수 증가
    public void increaseViewCount() {
        this.viewCount += 1;
    }

    // 좋아요수 증가
    public void increaseLikeCount() {
        this.likeCount += 1;
    }

    // 좋아요수 감소
    public void decreaseLikeCount() {
        this.likeCount -= 1;
    }
}
