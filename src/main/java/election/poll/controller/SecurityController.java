package election.poll.controller;

import election.poll.dto.JwtAuthenticationResponseDto;
import election.poll.dto.SignUpRequestDto;
import election.poll.dto.UserLoginRequestDto;
import election.poll.dto.UserLoginResponseDto;
import election.poll.entity.Role;
import election.poll.entity.RoleName;
import election.poll.entity.User;
import election.poll.exception.GeneralServiceException;
import election.poll.repository.RoleRepository;
import election.poll.repository.UserRepository;
import election.poll.security.JwtTokenProvider;
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequestDto signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new UserLoginResponseDto(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new UserLoginResponseDto(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getPhoneNumber())) {
            return new ResponseEntity(new UserLoginResponseDto(false, "Phone Number already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
//        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),signUpRequest.getPhoneNumber(),
//                signUpRequest.getEmail(), signUpRequest.getPassword());
        User user = new User(signUpRequest.getName(),signUpRequest.getUsername(),signUpRequest.getEmail(),
                signUpRequest.getPhoneNumber(),signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.USER)
                .orElseThrow(() -> new GeneralServiceException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.created(location).body(new UserLoginResponseDto(true, "User registered successfully"));
    }
}
