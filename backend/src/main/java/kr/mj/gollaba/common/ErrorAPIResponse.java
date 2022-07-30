package kr.mj.gollaba.common;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.mj.gollaba.exception.GollabaErrorCode;
import lombok.AllArgsConstructor;
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

    @ApiModelProperty(position = 0, example = "true")
    private final boolean error = true;

    @ApiModelProperty(position = 1, example = "1")
    private final int code;

    @ApiModelProperty(position = 2, example = "에러 문구")
    private final String message;

    @ApiModelProperty(position = 3, example = "[]")
    private final List<InvalidParameter> invalidParameters = new ArrayList<>();

    @ApiModelProperty(position = 4, hidden = true)
    private ErrorDetail details;

    public ErrorAPIResponse(GollabaErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
    }

    public ErrorAPIResponse(GollabaErrorCode errorCode, String message) {
        this.code = errorCode.getCode();
        this.message = message;
    }

    public ErrorAPIResponse(GollabaErrorCode errorCode, ErrorDetail detail) {
        this.code = errorCode.getCode();
        this.message = errorCode.getDescription();
        this.details = detail;
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

    class Error {

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

    @Getter
    @AllArgsConstructor
    public static class ErrorDetail {

        @JsonValue
        private final String details;

    }
}
