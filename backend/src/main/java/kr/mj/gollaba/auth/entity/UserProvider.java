package kr.mj.gollaba.auth.entity;

import kr.mj.gollaba.auth.dto.OAuth2UserInfo;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.common.entity.BaseTimeEntity;
import kr.mj.gollaba.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProvider extends BaseTimeEntity {

    @Id
    @Column(name = "user_provider_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private UserProvider(ProviderType providerType, String providerId, User user) {
        this.providerType = providerType;
        this.providerId = providerId;
        this.user = user;
    }

    public static UserProvider of(OAuth2UserInfo oAuth2UserInfo, User user) {
        return builder()
            .providerType(oAuth2UserInfo.getProviderType())
            .providerId(oAuth2UserInfo.getProviderId())
            .user(user)
            .build();
    }
}
