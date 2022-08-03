package election.poll.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "choices")
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String opinion;

    @ManyToOne
    private Poll poll;

    public Choice(String opinion) {
        this.opinion = opinion;
    }
}
