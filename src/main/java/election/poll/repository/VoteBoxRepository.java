package election.poll.repository;

import election.poll.model.ChoiceVoteCount;
import election.poll.model.VoteBox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteBoxRepository extends JpaRepository<VoteBox,Long> {

    @Query("SELECT NEW election.poll.model.ChoiceVoteCount(v.choice.id,count(v.id)) FROM " +
            "VoteBox v WHERE v.poll.id in :pollIds GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdInGroupByChoiceId(@Param("pollIds") List<Long> pollIds);

    //evoke from the choice count class the id  of the voter in the Vote class and group by id
    @Query("SELECT NEW election.poll.model.ChoiceVoteCount(v.choice.id, count(v.id)) FROM VoteBox v WHERE" +
            " v.poll.id = :pollId GROUP BY v.choice.id")
    List<ChoiceVoteCount> countByPollIdGroupByChoiceId(@Param("pollId") Long pollId);

    // evoke from vote where the id is...then collate the user and the vote id
    @Query("SELECT v FROM VoteBox v where v.user.id = :userId and v.poll.id in :pollIds")
    List<VoteBox> findByUserIdAndPollIdIn(@Param("userId") Long userId, @Param("pollIds") List<Long> pollIds);

    @Query("SELECT v FROM VoteBox v where v.user.id = :userId and v.poll.id = :pollId")
    VoteBox findByUserIdAndPollId(@Param("userId") Long userId, @Param("pollId") Long pollId);

    @Query("SELECT COUNT(v.id) from VoteBox v where v.user.id = :userId")
    long countByUserId(@Param("userId") Long userId);

    @Query("SELECT v.poll.id FROM VoteBox v WHERE v.user.id = :userId")
    Page<Long> findVotedPollIdsByUserId(@Param("userId") Long userId, Pageable pageable);
}
