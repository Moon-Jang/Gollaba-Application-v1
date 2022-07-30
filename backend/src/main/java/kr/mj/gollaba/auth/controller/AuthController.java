package kr.mj.gollaba.auth.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.auth.dto.LoginRequest;
import kr.mj.gollaba.auth.dto.LoginResponse;
import kr.mj.gollaba.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> logout(@RequestHeader(value = "GA-Refresh-Token") String refreshToken) {
        authService.logout(refreshToken);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
