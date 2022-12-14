package election.poll.controller;

import com.sun.xml.bind.v2.TODO;
import election.poll.dto.JwtAuthenticationResponseDto;
import election.poll.dto.SignUpRequestDto;
import election.poll.dto.UserLoginRequestDto;
import election.poll.dto.UserLoginResponseDto;
import election.poll.emailService.EmailService;
import election.poll.entity.Role;
import election.poll.entity.RoleName;
import election.poll.entity.User;
import election.poll.exception.GeneralServiceException;
import election.poll.exception.MessagingException;
import election.poll.repository.RoleRepository;
import election.poll.repository.UserRepository;
import election.poll.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/api/secure")
public class SecurityController {

    private static Logger logger = LoggerFactory.getLogger(SecurityController.class);
    
    //{
    //	"accessToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjU5MDg4NzcwLCJleHAiOjE2NTk3MzI1NzB9.k0nWZTN_MhQHsDVN9Ye_SROqUjMRq0wrXEK11RA49S7yymiW1dmDRIMfjhXEAUPdD4f8STJTKjHTRBrmedhDfw",
    //	"tokenType": "Bearer "
    //}

    // TODO: 7/29/2022   validation

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    EmailService emailService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsernameOrEmail(),
                        loginRequestDto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponseDto(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequest) throws MessagingException {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new UserLoginResponseDto(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new UserLoginResponseDto(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        //todo implement check for the phone number


        User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(),signUpRequest.getMiddlename(),signUpRequest.getUsername(),signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        emailService.sendRegistrationSuccessfulEmail(user);


        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new GeneralServiceException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));


        User result = userRepository.save(user);

        logger.info(signUpRequest.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new UserLoginResponseDto(true, "User registered successfully"));

    }
}
