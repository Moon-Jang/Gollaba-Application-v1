package kr.mj.polling.user.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponse {

    private Long userId;

    @Builder
    public SignupResponse(Long userId) {
        this.userId = userId;
    }

}
