package election.poll.utils;

import election.poll.dto.ChoiceResponseDto;
import election.poll.dto.PollResponseDto;
import election.poll.dto.UserResponseDto;
import election.poll.entity.User;
import election.poll.model.Poll;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModelMapper {

    public static PollResponseDto mapPollToPollResponse(Poll poll, Map<Long, Long> choiceVotesMap, User creator, Long userVote) {
        PollResponseDto pollResponse = new PollResponseDto();
        pollResponse.setId(poll.getId());
        pollResponse.setQuestion(poll.getQuestion());
        pollResponse.setCreationDateTime(poll.getCreatedAt());
        pollResponse.setExpirationDateTime(poll.getExpirationDateTime());
        Instant now = Instant.now();
        pollResponse.setIsExpired(poll.getExpirationDateTime().isBefore(now));

       List<ChoiceResponseDto> choiceResponses = poll.getChoices().stream().map(choice -> {
            ChoiceResponseDto choiceResponse = new ChoiceResponseDto();
            choiceResponse.setId(choice.getId());
            choiceResponse.setText(choice.getOpinion());

            if(choiceVotesMap.containsKey(choice.getId())) {
                choiceResponse.setVoteCount(choiceVotesMap.get(choice.getId()));
            } else {
                choiceResponse.setVoteCount(0);
            }
            return choiceResponse;
        }).collect(Collectors.toList());

        pollResponse.setChoices(choiceResponses);
        UserResponseDto creatorSummary = new UserResponseDto(creator.getId(), creator.getUsername(), creator.getFirstname());
        pollResponse.setCreatedBy(creatorSummary);

        if(userVote != null) {
            pollResponse.setSelectedChoice(userVote);
        }

        long totalVotes = pollResponse.getChoices().stream().mapToLong(ChoiceResponseDto::getVoteCount).sum();
        pollResponse.setTotalVotes(totalVotes);

        return pollResponse;
    }
}
