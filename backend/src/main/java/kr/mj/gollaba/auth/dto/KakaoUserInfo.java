package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.types.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo {

    private final ProviderType providerType;

    private final String providerId;

    private final String email;

    private final String name;

    private final String profileImageUrl;

    public KakaoUserInfo(ProviderType providerType, OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        var accountInfo = (Map<String, Object>) attributes.get("kakao_account");
        var profile = (Map<String, Object>) accountInfo.get("profile");
        this.providerType = providerType;
        this.providerId = valueToString(attributes.get("id"));
        this.email = valueToString(accountInfo.get("email"));
        this.name = valueToString(profile.get("nickname"));
        this.profileImageUrl = valueToString(profile.get("profile_image_url"));
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
