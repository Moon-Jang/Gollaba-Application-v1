package kr.mj.gollaba.security.controller;

import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.security.dto.LoginRequest;
import kr.mj.gollaba.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Const.ROOT_URL)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@Validated @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
