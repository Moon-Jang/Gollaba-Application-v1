package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.types.ProviderType;

public interface OAuth2UserInfo {

    String getEmail();

    String getName();

    String getProfileImageUrl();

    String getProviderId();

    ProviderType getProviderType();

}
