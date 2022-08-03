package election.poll.dto;

import lombok.Data;

import java.util.List;

@Data
public class PollRequestDto {


    private String question;


    private List<ChoiceRequestDto> choices;


    private PollLengthOfTimeDto pollLength;
}
