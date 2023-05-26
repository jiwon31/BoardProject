package board.server.domain.board.entity;

import board.server.common.entitiy.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardFile extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false)
    private String originFileName;

    @Column(nullable = false)
    private String storeFileName;

    @Column(nullable = false)
    private String uploadDir;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Board board;

    @Builder
    public BoardFile(String originFileName, String storeFileName, String uploadDir, Board board) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
        this.uploadDir = uploadDir;
        this.board = board;
    }
}
