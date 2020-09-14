import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class PollDao {

    private EntityManager entityManager;

    public PollDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Poll> getAllPolls() {
        Query query = entityManager.createNamedQuery("Poll.findAll", Poll.class);
        return query.getResultList();
    }

    public Poll addPoll(String summary, Person person, Boolean isPublic) {
        String timestamp = TimeStamp.getTimeStamp();

        Poll poll = Poll.builder()
                .summary(summary)
                .created_time(timestamp)
                .updated_time(timestamp)
                .isPublic(isPublic)
                .person(person)
                .build();

        entityManager.getTransaction().begin();
        entityManager.persist(poll);
        entityManager.getTransaction().commit();

        return poll;
    }

    public void deletePoll(Long pollId) {
        Poll poll = getPollById(pollId);
        entityManager.getTransaction().begin();
        entityManager.remove(poll);
        entityManager.getTransaction().commit();
    }

    public void updatePoll(Long pollId, PollUpdateRequest pur) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Poll poll = getPollById(pollId);

        // Iterate over the input to see what we want to update
        if (pur.getSummary() != null) poll.setSummary(pur.getSummary());
        if (pur.getIsPublic() != null) poll.setIsPublic(pur.getIsPublic());

        poll.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        entityManager.persist(poll);
        entityManager.getTransaction().commit();
    }

    public Poll getPollById(Long pollId) {
        return entityManager.find(Poll.class, pollId);
    }

}
