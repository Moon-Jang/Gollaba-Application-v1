package kr.mj.gollaba.auth;

import kr.mj.gollaba.auth.dto.OAuth2UserInfo;
import kr.mj.gollaba.auth.entity.UserProvider;
import kr.mj.gollaba.auth.entity.UserToken;
import kr.mj.gollaba.auth.repository.UserProviderRepository;
import kr.mj.gollaba.auth.repository.UserTokenRepository;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.common.util.CookieUtils;
import kr.mj.gollaba.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;

import static kr.mj.gollaba.auth.AuthorizationRequestRepositoryImpl.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME;
import static kr.mj.gollaba.auth.AuthorizationRequestRepositoryImpl.REDIRECT_URI_PARAM_COOKIE_NAME;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.REFRESH_TOKEN;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenRepository userTokenRepository;
    private final UserProviderRepository userProviderRepository;
    @Value("${security.jwt.refresh-expiration-time}")
    private long refreshExpirationTime;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var redirectUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(getDefaultTargetUrl());
        var oAuth2UserInfo = getOAuth2UserInfo(authentication);
        var user = userProviderRepository.findByProviderId(oAuth2UserInfo.getProviderId())
            .map(UserProvider::getUser)
            .orElse(null);

        if (user == null) {
            var signUpUrl = generateSignupUrl(redirectUrl, oAuth2UserInfo);
            getRedirectStrategy().sendRedirect(request, response, signUpUrl);
            return;
         }

        var accessToken = jwtTokenProvider.createAccessToken(user);
        var refreshToken = saveUserToken(user, accessToken);

        var cookieMaxAge = (int) refreshExpirationTime / 1000;
        CookieUtils.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtils.addCookie(response, REFRESH_TOKEN, refreshToken, cookieMaxAge);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + redirectUrl);
            return;
        }

        var targetUrl = UriComponentsBuilder.fromUriString(redirectUrl)
            .queryParam("accessToken", accessToken)
            .queryParam("protectHash", "v")
            .build()
            .toUriString();
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    private OAuth2UserInfo getOAuth2UserInfo(Authentication authentication) {
        var authToken = (OAuth2AuthenticationToken) authentication;
        var providerType = ProviderType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
        return OAuth2UserInfoFactory.create(providerType, authToken.getPrincipal());
    }

    private String generateSignupUrl(String redirectUrl, OAuth2UserInfo oAuth2UserInfo) {
        var queryParams = new LinkedMultiValueMap<String, String>();
        queryParams.add("name", oAuth2UserInfo.getName());
        queryParams.add("email", oAuth2UserInfo.getEmail());
        queryParams.add("providerId", oAuth2UserInfo.getProviderId());
        queryParams.add("providerType", oAuth2UserInfo.getProviderType().name());
        queryParams.add("profileImageUrl",
            oAuth2UserInfo.getProfileImageUrl() != null
                ? oAuth2UserInfo.getProfileImageUrl()
                : ""
        );
        queryParams.add("protectHash", "v");

        var result = UriComponentsBuilder.fromUriString(redirectUrl)
            .queryParams(queryParams)
            .encode()
            .build()
            .toUriString();

        return result;
    }

    private String saveUserToken(User user, String accessToken) {
        var refreshToken = jwtTokenProvider.createRefreshToken();
        var userToken = userTokenRepository.findByUserId(user.getId())
            .orElse(UserToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .user(user)
                .build());

        return userTokenRepository.save(userToken)
                .getRefreshToken();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, "JSESSIONID");
    }
}
