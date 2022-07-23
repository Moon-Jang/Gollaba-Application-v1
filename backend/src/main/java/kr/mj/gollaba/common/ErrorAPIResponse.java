package kr.mj.gollaba.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.exception.GollabaErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Getter
@ApiModel(value = "ErrorAPIResponse")
public class ErrorAPIResponse {

    @ApiModelProperty(position = 1, example = "1")
    private final int code;

    @ApiModelProperty(position = 2, example = "존재하지 않는 데이터입니다.")
    private final String message;

    @ApiModelProperty(position = 3, example = "[]")
    private final List<InvalidParameter> invalidParameters = new ArrayList<>();

    public ErrorAPIResponse(GollabaErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public ErrorAPIResponse(GollabaErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }

    @SuppressWarnings("serial")
    public ErrorAPIResponse(MethodArgumentNotValidException validationError) {
        this.code = GollabaErrorCode.INVALID_PARAMS.getCode();
        this.message = GollabaErrorCode.INVALID_PARAMS.getDescription();
        validationError.getBindingResult()
                .getAllErrors()
                .stream()
                .map(m -> (FieldError) m)
                .forEach(m -> invalidParameters.add(new InvalidParameter(m.getCode(), m.getDefaultMessage(), m.getField())));
    }

    @SuppressWarnings("serial")
    public ErrorAPIResponse(BindException bindingError) {
        this.code = GollabaErrorCode.INVALID_PARAMS.getCode();
        this.message = GollabaErrorCode.INVALID_PARAMS.getDescription();
        bindingError.getBindingResult()
                .getAllErrors()
                .stream()
                .map(m -> (FieldError) m)
                .forEach(m -> invalidParameters.add(new InvalidParameter(m.getCode(), m.getDefaultMessage(), m.getField())));
    }

    @Getter
    @RequiredArgsConstructor
    class InvalidParameter {

        @ApiModelProperty(example = "NotBlank")
        private final String code;

        @ApiModelProperty(example = "공백일 수 없습니다")
        private final String message;

        @ApiModelProperty(example = "name")
        private final String field;

    }
}
