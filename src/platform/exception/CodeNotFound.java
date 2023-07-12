package platform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
public class CodeNotFound extends RuntimeException {

    public CodeNotFound() {
    }

    public CodeNotFound(String message) {
        super(message);
    }

    public CodeNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeNotFound(Throwable cause) {
        super(cause);
    }

    public CodeNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
