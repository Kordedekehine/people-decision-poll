package election.poll.controller;

import election.poll.dto.PagedResponseDto;
import election.poll.dto.PollRequestDto;
import election.poll.dto.PollResponseDto;
import election.poll.dto.VoteBoxRequestDto;
import election.poll.model.Poll;
import election.poll.repository.PollRepository;
import election.poll.repository.UserRepository;
import election.poll.repository.VoteBoxRepository;
import election.poll.security.CurrentUser;
import election.poll.security.UserPrincipal;
import election.poll.service.PollService;
import election.poll.utils.ApiResponse;
import election.poll.utils.AppsConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteBoxRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollService pollService;

    private static final Logger logger = LoggerFactory.getLogger(PollController.class);


    @GetMapping
    public PagedResponseDto<PollResponseDto> getPolls(@CurrentUser UserPrincipal currentUser,
                                                      @RequestParam(value = "page", defaultValue = AppsConstant.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppsConstant.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getAllPolls(currentUser, page, size);
    }


    //user_ids: ["bbbbbbbbbb","aaaaaaaaaa","987654321","123456789"]
    @PostMapping
    //@PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createPoll(@Valid @RequestBody PollRequestDto pollRequest) {
        Poll poll = pollService.createPoll(pollRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{pollId}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Poll Created Successfully"));
    }


    @GetMapping("/{pollId}")
    public PollResponseDto getPollById(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "pollId") Long pollId){
        return pollService.getPollById(pollId, currentUser);
    }

    @PostMapping("/{pollId}/votes")
    @PreAuthorize("hasRole('USER')")
    public PollResponseDto castVote(@CurrentUser UserPrincipal currentUser, @PathVariable(value = "pollId") Long pollId,
                                 @Valid @RequestBody VoteBoxRequestDto voteRequest) {
        return pollService.castVoteAndGetUpdatedPoll(pollId, voteRequest, currentUser);
    }


}
