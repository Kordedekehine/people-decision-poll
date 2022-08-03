package election.poll.entity;

import election.poll.auditDate.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String middlename;
    private String username;

    @Email
    private String email;

    //yarn them the email bug
    private String phonenumber;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles = new HashSet<>();


    public User(String firstname, String lastname,String middlename, String username, String email, String phonenumber, String password) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.username = username;
        this.email = email;
        this.phonenumber = phonenumber;
        this.password = password;
    }
}
