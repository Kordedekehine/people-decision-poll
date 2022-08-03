package election.poll.dto;

import lombok.Data;

@Data
public class ChoiceResponseDto {

    private long id;
    private String text;
    private long voteCount;
}
