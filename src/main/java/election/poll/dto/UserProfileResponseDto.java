package election.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserProfileResponseDto {

    private Long id;
    private String username;
    private String firstname;
    private String secondname;
    private String middlename;
    private Instant joinedAt;
    private Long pollCount;
    private Long voteCount;
}
