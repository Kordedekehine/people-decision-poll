package election.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String firstname;

}
