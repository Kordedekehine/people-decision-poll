package election.poll.emailService;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Setter
@Getter
@Component
public class EmailConfig {

    @NotNull
    @Value("${spring.mail.host}")
    private String host;

    @NotNull
    @Value("${spring.mail.port}")
    private int port;

    @NotNull
    @Value("${spring.mail.username}")
    private String username;

    @NotNull
    @Value("${spring.mail.password}")
    private String password;

}
