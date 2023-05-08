package board.server.domain.user.entitiy;

import board.server.common.converter.BooleanToYnConverter;
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

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "user_name", unique = true, nullable = false, length = 20)
    private String userName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "del_yn")
    @Convert(converter = BooleanToYnConverter.class)
    private Boolean isDeleted;

    @Builder
    public User(String email, String password, String userName, Role role, Boolean isDeleted) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.isDeleted = isDeleted;
    }
}
