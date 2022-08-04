package election.poll.controller;

import election.poll.dto.*;
import election.poll.entity.User;
import election.poll.exception.NotFoundException;
import election.poll.repository.PollRepository;
import election.poll.repository.UserRepository;
import election.poll.repository.VoteBoxRepository;
import election.poll.security.CurrentUser;
import election.poll.security.UserPrincipal;
import election.poll.service.PollService;
import election.poll.utils.AppsConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework. security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteBoxRepository voteRepository;

    @Autowired
    private PollService pollService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponseDto getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserResponseDto userSummary = new UserResponseDto(currentUser.getId(), currentUser.getUsername(), currentUser.getFirstname());
        return userSummary;
    }

    @GetMapping("/user/checkUserStatus")
    public UserStatusResponseDto checkUserStatus(@RequestParam(value = "username") String username) {
        Boolean isUserAvailable = !userRepository.existsByUsername(username);
        return new UserStatusResponseDto(isUserAvailable);
    }

    @GetMapping("/user/checkUserEmailStatus")
    public UserStatusResponseDto checkUserEmailStatus(@RequestParam(value = "email") String email) {
        Boolean isUserAvailable = !userRepository.existsByEmail(email);
        return new UserStatusResponseDto(isUserAvailable);
    }

    @GetMapping("/users/{username}")
    public UserProfileResponseDto getUserProfile(@PathVariable(value = "username") String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User", "username", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        UserProfileResponseDto userProfile = new UserProfileResponseDto(user.getId(), user.getUsername(), user.getFirstname(),
                user.getLastname(),user.getMiddlename(), user.getCreatedAt(), pollCount, voteCount);

        return userProfile;
    }

    @GetMapping("/users/{username}/polls")
    public PagedResponseDto<PollResponseDto> getPollsCreatedBy(@PathVariable(value = "username") String username,
                                                               @CurrentUser UserPrincipal currentUser,
                                                               @RequestParam(value = "page", defaultValue = AppsConstant.DEFAULT_PAGE_NUMBER) int page,
                                                               @RequestParam(value = "size", defaultValue = AppsConstant.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsCreatedBy(username, currentUser, page, size);
    }


    @GetMapping("/users/{username}/votes")
    public PagedResponseDto<PollResponseDto> getPollsVotedBy(@PathVariable(value = "username") String username,
                                                       @CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppsConstant.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppsConstant.DEFAULT_PAGE_SIZE) int size) {
        return pollService.getPollsVotedBy(username, currentUser, page, size);
    }

}
