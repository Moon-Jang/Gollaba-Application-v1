package kr.mj.polling.exception;

import kr.mj.polling.common.ErrorAPIResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class PollingAppException extends RuntimeException {

    private PollingAppErrorCode errorCode;

    public PollingAppException(Exception e) {
        super(e);
    }

    public PollingAppException(PollingAppErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public static ResponseEntity<ErrorAPIResponse> getResult(final Exception e) {
        if (e instanceof PollingAppException) {
            PollingAppErrorCode errorCode = ((PollingAppException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else if (e.getCause() instanceof PollingAppException) {
            PollingAppErrorCode errorCode = ((PollingAppException) e).getErrorCode();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode), errorCode.getHttpStatus());
        } else {
            PollingAppErrorCode errorCode = PollingAppErrorCode.UNKNOWN_ERROR;
            String errorMessage = errorCode.getDescription() + "  detail: " + e.getMessage();
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode, errorMessage), errorCode.getHttpStatus());
        }
    }

}