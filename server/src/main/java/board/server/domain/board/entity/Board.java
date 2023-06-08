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

    @Column(name = "view_cnt")
    private int viewCount;

    @Column(name = "like_cnt")
    private int likeCount;

    @Column(name = "comment_cnt")
    private int commentCount;

    @Transient
    private boolean isLikedByUser = false;

    @Builder
    public Board(String title, String content, User user, Boolean isDeleted,
                 int viewCount, int likeCount, int commentCount) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.isDeleted = isDeleted;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public boolean getIsLikedByUser() {
        return this.isLikedByUser;
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

    // 댓글수 증가
    public void increaseCommentCount() {
        this.commentCount += 1;
    }

    // 댓글수 감소
    public void decreaseCommentCount() {
        this.commentCount -= 1;
    }

    // 게시글에 대한 유저의 좋아요 상태 변경
    public void updateIsLikedByUser(boolean isLikedByUser) {
        this.isLikedByUser = isLikedByUser;
    }
}
