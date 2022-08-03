package election.poll.model;

import election.poll.auditDate.DateAudit;
import election.poll.entity.User;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "votebox")
@Data
public class VoteBox extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Poll poll;

    @ManyToOne
    private Choice choice;

    @ManyToOne
    private User user;
}
