package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.types.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;

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

}
