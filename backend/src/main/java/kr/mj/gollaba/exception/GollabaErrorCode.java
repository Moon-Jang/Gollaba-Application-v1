package kr.mj.gollaba.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum GollabaErrorCode {
    /* 0 ~ 9999  common & auth */
    DATA_NOT_FOUND(0, HttpStatus.BAD_REQUEST, "존재하지 않는 데이터입니다."),
    INVALID_JWT_TOKEN(1, HttpStatus.UNAUTHORIZED, "유효하지 않은 jwt 토큰입니다."),
    NOT_EXIST_JWT_TOKEN(2, HttpStatus.UNAUTHORIZED, "jwt 토큰이 존재 하지 않습니다."),
    NOT_MATCHED_PASSWORD(3, HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED,"인증 정보가 없거나 부족합니다."),
    FORBIDDEN(403, HttpStatus.FORBIDDEN,"해당 자원에 접근할 수 없습니다."),
    UNKNOWN_ERROR(9999, HttpStatus.BAD_REQUEST, "알 수 없는 에러입니다. 관리자에게 문의해주세요."),

    /* 10000 ~ 19999 user */
    ALREADY_EXIST_USER(10000, HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다."),
    NOT_EXIST_USER_BY_UNIQUE_ID(10001, HttpStatus.BAD_REQUEST, "해당 아이디로 가입된 회원이 존재하지 않습니다.");


    private final int code;
    private final HttpStatus httpStatus;
    private final String description;

}
