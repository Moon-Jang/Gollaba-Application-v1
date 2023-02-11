package kr.mj.gollaba.auth;

import kr.mj.gollaba.auth.dto.FaceBookUserInfo;
import kr.mj.gollaba.auth.dto.KakaoUserInfo;
import kr.mj.gollaba.auth.dto.NaverUserInfo;
import kr.mj.gollaba.auth.dto.OAuth2UserInfo;
import kr.mj.gollaba.auth.types.ProviderType;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo create(ProviderType providerType, OAuth2User oAuth2User) {
        switch (providerType) {
            case FACEBOOK: return new FaceBookUserInfo(providerType, oAuth2User);
            case KAKAO: return new KakaoUserInfo(providerType, oAuth2User);
            case NAVER: return new NaverUserInfo(providerType, oAuth2User);
            default: throw new IllegalArgumentException("Invalid Provider Type.");
        }
    }

}
