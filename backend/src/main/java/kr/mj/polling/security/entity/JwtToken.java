package kr.mj.polling.security.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "jwt_token")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class JwtToken {

    @Id
    @Column(name = "jwt_token_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "update_at", nullable = false)
    private LocalDateTime updateAt;

    @Builder
    public JwtToken(Long id, String refreshToken, String userEmail) {
        this.id = id;
        this.refreshToken = refreshToken;
        this.userEmail = userEmail;
    }

}
