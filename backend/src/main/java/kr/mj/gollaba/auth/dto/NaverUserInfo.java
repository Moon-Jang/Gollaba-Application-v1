package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.types.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo {

    private final ProviderType providerType;

    private final String providerId;

    private final String email;

    private final String name;

    private final String profileImageUrl;

    public NaverUserInfo(ProviderType providerType, OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        var response = (Map<String, Object>) attributes.get("response");

        this.providerType = providerType;
        this.providerId = valueToString(response.get("id"));
        this.email = valueToString(response.get("email"));
        this.name = valueToString(response.get("name"));
        this.profileImageUrl = valueToString(response.get("profile_image"));
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
        return this.profileImageUrl;
    }

    @Override
    public String getProviderId() {
        return this.providerId;
    }

    @Override
    public ProviderType getProviderType() {
        return this.providerType;
    }

    private String valueToString(Object value) {
        if (value == null) return null;

        return value.toString();
    }
}
