package kr.mj.gollaba.exception;

import kr.mj.gollaba.common.ErrorAPIResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class GollabaException extends RuntimeException {

    private GollabaErrorCode errorCode;

    public GollabaException(Exception e) {
        super(e);
    }

    public GollabaException(GollabaErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public static ResponseEntity<ErrorAPIResponse> getResult(final Exception e) {
        if (e instanceof GollabaException) {
            GollabaErrorCode errorCode = ((GollabaException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else if (e.getCause() instanceof GollabaException) {
            GollabaErrorCode errorCode = ((GollabaException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else {
            GollabaErrorCode errorCode = GollabaErrorCode.UNKNOWN_ERROR;
            String errorMessage = errorCode.getDescription() + "  detail: " + e.getMessage();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode, errorMessage), errorCode.getHttpStatus());
        }
    }

}