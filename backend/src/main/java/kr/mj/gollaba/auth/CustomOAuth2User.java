package kr.mj.gollaba.auth;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface CustomOAuth2User extends OAuth2User {

    Long getUserId();

}
