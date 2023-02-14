package kr.mj.gollaba.unit.auth.repository;

import kr.mj.gollaba.auth.JwtTokenProvider;
import kr.mj.gollaba.auth.entity.UserToken;
import kr.mj.gollaba.auth.repository.UserTokenRepository;
import kr.mj.gollaba.unit.auth.factory.UserTokenFactory;
import kr.mj.gollaba.unit.common.RepositoryTest;
import kr.mj.gollaba.unit.user.factory.UserFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(value = JwtTokenProvider.class)
class UserTokenRepositoryTest extends RepositoryTest {

    @Autowired
    private UserTokenRepository userTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @DisplayName("회원 토큰 객체 존재 여부 확인 by refreshToken")
    @Test
    void exists_by_refresh_token() throws Exception {
        //given
        UserToken savedUserToken = userTokenRepository.save(UserTokenFactory.create(jwtTokenProvider));

        flushAndClear();

        //when
        final boolean result1 = userTokenRepository.existsByRefreshToken(savedUserToken.getRefreshToken());
        final boolean result2 = userTokenRepository.existsByRefreshToken("AnyToken");

        //then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @DisplayName("회원 토큰 객체 조회 by refreshToken")
    @Test
    void find_by_refresh_token() throws Exception {
        //given
        UserToken savedUserToken = userTokenRepository.save(UserTokenFactory.create(jwtTokenProvider));

        flushAndClear();

        //when
        UserToken fountToken = userTokenRepository.findByRefreshToken(savedUserToken.getRefreshToken())
                .orElseThrow(() -> new IllegalArgumentException());

        //then
        assertThat(fountToken.getId()).isEqualTo(savedUserToken.getId());
        assertThat(fountToken.getAccessToken()).isEqualTo(savedUserToken.getAccessToken());
        assertThat(fountToken.getRefreshToken()).isEqualTo(savedUserToken.getRefreshToken());
    }

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
        String newAccessToken = jwtTokenProvider.createAccessToken(UserFactory.createWithId());
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