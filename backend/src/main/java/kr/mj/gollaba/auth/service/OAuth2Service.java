package kr.mj.gollaba.auth.service;

import kr.mj.gollaba.auth.OAuth2UserInfoFactory;
import kr.mj.gollaba.auth.repository.UserProviderRepository;
import kr.mj.gollaba.auth.types.ProviderType;
import kr.mj.gollaba.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2Service extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final UserProviderRepository userProviderRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        var providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
        var userInfo = OAuth2UserInfoFactory.create(providerType, oAuth2User);
        var user = userRepository.findByEmail(userInfo.getEmail());
        var userProvider = userProviderRepository.findByProviderId(userInfo.getProviderId());

        if (user.isEmpty()) {
            var savedUser = userRepository.save(userInfo.toUserEntity());
            userProviderRepository.save(userInfo.toUserProviderEntity(savedUser));
            return oAuth2User;
        }

        if (userProvider.isEmpty()) {
            userProviderRepository.save(userInfo.toUserProviderEntity(user.get()));
        }

        return oAuth2User;
    }
}
