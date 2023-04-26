package board.server.domain.user.entitiy;

import board.server.common.entitiy.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "user_name", length = 20, unique = true)
    private String userName;

    @Column(name = "del_yn")
    private Boolean isDeleted;

    @Builder
    public User(String email, String password, String userName, Boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.isDeleted = isDeleted;
    }
}
