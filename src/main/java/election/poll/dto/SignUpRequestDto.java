package election.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class SignUpRequestDto {


    private String name;


    private String username;


    private String email;

    private String phoneNumber;


    private String password;
}
