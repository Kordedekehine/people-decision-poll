package election.poll.emailService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class EmailResponse {

    private String message;

    private String status;
}
