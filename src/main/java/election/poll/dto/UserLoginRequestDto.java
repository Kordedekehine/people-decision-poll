package election.poll.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequestDto {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
