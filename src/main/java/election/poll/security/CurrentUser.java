package election.poll.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.*;

/**
 * this class is basically to give access to the currently authenticated user in the controller
 *
 * reduces the dependency on Spring Security. So if we decide to remove Spring Security from our project,
 * we can easily do it by simply changing the CurrentUser annotation-
 */

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal //this easily back up the authentication principal
public @interface CurrentUser {

}
