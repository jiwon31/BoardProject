package board.server.domain.user.entitiy;

import board.server.common.entitiy.BaseTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken extends BaseTime {

    @Id
    @Column(name = "rt_key")
    private String key;

    private String token;

    @Builder
    public RefreshToken(String key, String token) {
        this.key = key;
        this.token = token;
    }

    public void updateRefreshToken(String refreshToken) {
        this.token = refreshToken;
    }
}
