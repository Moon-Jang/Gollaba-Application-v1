package kr.mj.gollaba.user.dto;


import kr.mj.gollaba.common.BaseApiResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupResponse implements BaseApiResponse {

    private Long userId;

    @Builder
    public SignupResponse(Long userId) {
        this.userId = userId;
    }

}
