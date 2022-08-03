package election.poll.emailService;

import election.poll.entity.User;
import election.poll.exception.MessagingException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;
    private final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    private final String REGISTRATION_ERROR_MESSAGE = "Registration Not Successful";

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendRegistrationSuccessfulEmail(User userEmail) throws MessagingException {
           // String link = "http://localhost:9090" + token;
            simpleMailMessage.setTo(userEmail.getEmail());
            simpleMailMessage.setSubject("Account Activation");
            //simpleMailMessage.setFrom("abraham.ariyo@autox.africa");
            simpleMailMessage.setFrom("salamikehinde345@gmail.com");
//        simpleMailMessage.setText("AutoX");
            String template = "Dear [[name]],\n"
                    + "Thanks for registering on People's Decision Poll.\n"
                   // + "Kindly use the code below to validate your account and activate your account:\n"
                   // + "[[code]]\n"
                    + "Now you can decide the future of your Country.For a Better Nigeria"
                    + "Thank you.\n"
                    + "AkoredeTheFirst team";
            template = template.replace("[[name]]", userEmail.getFirstname());
          //  template = template.replace("[[code]]", token);
            simpleMailMessage.setText(template);

//        javaMailSender.send(simpleMailMessage);
            try{
                javaMailSender.send(simpleMailMessage);
            }catch (Exception exception){
                exception.printStackTrace();
                throw new MessagingException(String.format(REGISTRATION_ERROR_MESSAGE));
            }


        }
    }




