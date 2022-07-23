package kr.mj.gollaba.exception;

import kr.mj.gollaba.common.ErrorAPIResponse;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

@Getter
public class GollabaException extends RuntimeException {

    private GollabaErrorCode errorCode;

    public GollabaException(Exception e) {
        super(e);
    }

    private static final int ERROR_STACK_LIMIT = 10;

    public GollabaException(GollabaErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public GollabaException(GollabaErrorCode errorCode, String description) {
        super(description);
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
            String errorMessage = errorCode.getDescription() + "  detail: " + createDetails(e);
            return new ResponseEntity<>(new ErrorAPIResponse(errorCode, errorMessage), errorCode.getHttpStatus());
        }
    }

    private static String createDetails(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return Arrays.stream(errors.toString().split("\r\n\tat"))
                .limit(ERROR_STACK_LIMIT)
                .collect(Collectors.joining("\n"));
    }

}