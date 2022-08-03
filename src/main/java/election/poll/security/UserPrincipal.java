package election.poll.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import election.poll.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@EqualsAndHashCode
public class UserPrincipal implements UserDetails {

    private Long id;

    private String firstname;

    private String lastname;

    private String middlename;


    private String username;

    private String phoneNumber;


    private String email;


    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new
                SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMiddlename(),
                user.getUsername(),
                user.getEmail(),
                user.getPhonenumber(),
                user.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }



    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
