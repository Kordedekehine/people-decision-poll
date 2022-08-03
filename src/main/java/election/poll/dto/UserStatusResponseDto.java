package election.poll.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.bytebuddy.asm.Advice;

@Data
@AllArgsConstructor
public class UserStatusResponseDto {

    public Boolean isUserAvailable;


}
