package kr.mj.gollaba.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
import kr.mj.gollaba.auth.service.AuthService;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.exception.GollabaErrorCode;
import kr.mj.gollaba.exception.GollabaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static kr.mj.gollaba.config.SecurityConfig.REFRESH_TOKEN_HEADER;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "User")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "로그인")
    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.login(request));
    }

    @ApiOperation(value = "로그아웃")
    @PostMapping(path = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> logout(@RequestHeader(value = REFRESH_TOKEN_HEADER) String refreshToken) {
        authService.logout(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @ApiOperation(value = "OAuth 회원 연동 철회 - Kakao")
    @PostMapping(path = "/oauth/kakao/deregister", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> withdrawKaKao(@RequestParam("user_id") String providerId,
                                           @RequestHeader(AUTHORIZATION) String authorization) {
        var key = authorization.replace("KakaoAK ", "");

        authenticateKakao(key);
        authService.withdrawKaKao(providerId);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    private void authenticateKakao(String key) {
        var adminKey = "";

        if (key.equals(adminKey) == false) {
            throw new GollabaException(GollabaErrorCode.UNAUTHORIZED);
        }
    }
}
