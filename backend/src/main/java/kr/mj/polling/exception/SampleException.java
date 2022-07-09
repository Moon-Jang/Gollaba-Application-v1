package kr.mj.polling.exception;

import kr.mj.polling.common.ErrorAPIResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SampleException extends RuntimeException {

    private SampleErrorCode errorCode;

    public SampleException(Exception e) {
        super(e);
    }

    public SampleException(SampleErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public static ResponseEntity<ErrorAPIResponse> getResult(final Exception e) {
        if (e instanceof SampleException) {
            SampleErrorCode errorCode = ((SampleException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else if (e.getCause() instanceof SampleException) {
            SampleErrorCode errorCode = ((SampleException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else {
            SampleErrorCode errorCode = SampleErrorCode.UNKNOWN_ERROR;
            String errorMessage = errorCode.getDescription() + "  detail: " + e.getMessage();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode, errorMessage), errorCode.getHttpStatus());
        }
    }

}