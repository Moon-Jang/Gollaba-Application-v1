package kr.mj.gollaba.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.mj.gollaba.common.Const;
import kr.mj.gollaba.user.dto.SignupRequest;
import kr.mj.gollaba.user.dto.SignupResponse;
import kr.mj.gollaba.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Const.ROOT_URL)
@Api(tags = "User")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 가입")
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> signup(@Validated @RequestBody SignupRequest request) {
        SignupResponse response = userService.save(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
