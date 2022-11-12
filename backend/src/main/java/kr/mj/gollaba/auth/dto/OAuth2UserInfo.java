package kr.mj.gollaba.auth.dto;

import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.user.entity.User;

public interface OAuth2UserInfo {

    String getEmail();

    String getName();

    String getProfileImageUrl();

    String getProviderId();

    ProviderType getProviderType();

    User toUserEntity();

    UserProvider toUserProviderEntity(User user);

}
