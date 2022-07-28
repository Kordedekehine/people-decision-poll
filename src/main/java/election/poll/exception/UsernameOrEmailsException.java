package election.poll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsernameOrEmailsException extends RuntimeException{

    public UsernameOrEmailsException() {
        super();
    }

    public UsernameOrEmailsException(String message) {
        super(message);
    }

    public UsernameOrEmailsException(String message, Throwable cause) {
        super(message, cause);
    }
}
