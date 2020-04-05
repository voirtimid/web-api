package mk.metalkat.webapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotValidException extends RuntimeException {

    public UserNotValidException(String message) {
        super(message);
    }
}
