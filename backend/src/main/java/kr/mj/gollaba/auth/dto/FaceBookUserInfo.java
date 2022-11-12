package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.user.entity.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static kr.mj.gollaba.user.type.UserRoleType.ROLE_USER;

public class FaceBookUserInfo implements OAuth2UserInfo {

    private final ProviderType providerType;

    private final String providerId;

    private final String email;

    private final String name;

    public FaceBookUserInfo(ProviderType providerType, OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        this.providerType = providerType;
        this.providerId = attributes.get("id").toString();
        this.email = attributes.get("email").toString();
        this.name = attributes.get("name").toString();
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getProfileImageUrl() {
        return null;
    }

    @Override
    public String getProviderId() {
        return this.providerId;
    }

    @Override
    public ProviderType getProviderType() {
        return this.providerType;
    }

    @Override
    public User toUserEntity() {
        return User.builder()
                .email(email)
                .nickName(name)
                .password(null)
                .profileImageUrl(null)
                .backgroundImageUrl(null)
                .userRole(ROLE_USER)
                .build();
    }

    @Override
    public UserProvider toUserProviderEntity(User user) {
        return UserProvider.builder()
                .providerType(providerType)
                .providerId(providerId)
                .user(user)
                .build();
    }
}
