package election.poll.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

    private String searchName;
    private String fieldName;
    private Object fieldValue;

    public NotFoundException( String searchName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", searchName, fieldName, fieldValue));
        this.searchName = searchName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
