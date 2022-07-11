package kr.mj.gollaba.unit.security.repository;

import kr.mj.gollaba.security.JwtTokenProvider;
import kr.mj.gollaba.security.entity.UserToken;
import kr.mj.gollaba.security.repository.UserTokenRepository;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.security.factory.UserTokenFactory;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import kr.mj.gollaba.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Import(value = JwtTokenProvider.class)
class UserTokenRepositoryTest extends RepositoryTest {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("회원 토큰 객체 추가")
    @Test
    void save() throws Exception {
        //given
        UserToken userToken = UserTokenFactory.create(jwtTokenProvider);

        //when
        UserToken savedUserToken = userTokenRepository.save(userToken);

        flushAndClear();

        UserToken foundUserToken = userTokenRepository.findById(savedUserToken.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(foundUserToken.getId()).isEqualTo(savedUserToken.getId());
        assertThat(foundUserToken.getAccessToken()).isEqualTo(savedUserToken.getAccessToken());
        assertThat(foundUserToken.getRefreshToken()).isEqualTo(savedUserToken.getRefreshToken());
    }

    @DisplayName("회원 토큰 객체 수정")
    @Test
    void save_update() throws Exception {
        //given
        UserToken savedUserToken = userTokenRepository.save(UserTokenFactory.create(jwtTokenProvider));
        String newAccessToken = jwtTokenProvider.createAccessToken(UserTokenFactory.TEST_UNIQUE_ID, UserTokenFactory.TEST_NICK_NAME);
        String newRefreshToken = jwtTokenProvider.createRefreshToken();

        flushAndClear();

        //when
        savedUserToken.updateAccessToken(newAccessToken);
        savedUserToken.updateRefreshToken(newRefreshToken);

        userTokenRepository.save(savedUserToken);
        UserToken foundUserToken = userTokenRepository.findById(savedUserToken.getId())
                .orElseThrow(IllegalArgumentException::new);

        //then
        assertThat(foundUserToken.getId()).isEqualTo(savedUserToken.getId());
        assertThat(newAccessToken).isEqualTo(savedUserToken.getAccessToken());
        assertThat(newRefreshToken).isEqualTo(savedUserToken.getRefreshToken());
    }

    @DisplayName("회원 토큰 객체 삭제 by refreshToken")
    @Test
    void delete_by_refresh_token() throws Exception {
        //given
        UserToken savedUserToken = userTokenRepository.save(UserTokenFactory.create(jwtTokenProvider));

        flushAndClear();

        //when
        userTokenRepository.deleteByRefreshToken(savedUserToken.getRefreshToken());

        flushAndClear();

        UserToken foundUserToken = userTokenRepository.findById(savedUserToken.getId())
                .orElseGet(() -> null);

        //then
        assertThat(foundUserToken).isNull();
    }

}