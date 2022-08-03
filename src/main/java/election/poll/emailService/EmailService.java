package election.poll.emailService;


import election.poll.dto.SignUpRequestDto;
import election.poll.entity.User;
import election.poll.exception.MessagingException;

public interface EmailService {

    void sendRegistrationSuccessfulEmail(User userEmail) throws MessagingException;

}
