package kr.mj.polling.user.controller;

import kr.mj.polling.common.Const;
import kr.mj.polling.user.dto.SignupRequest;
import kr.mj.polling.user.dto.SignupResponse;
import kr.mj.polling.user.service.UserService;
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
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SignupResponse> signup(@Validated @RequestBody SignupRequest request) {
        SignupResponse response = userService.save(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
