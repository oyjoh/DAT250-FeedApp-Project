import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

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

    public Poll addPoll (String summary, Brukar brukar, boolean isPublic) {
        String timestamp = TimeStamp.getTimeStamp();

        Poll poll = Poll.builder()
                .setSummary(summary)
                .setCreated_time(timestamp)
                .setUpdated_time(timestamp)
                .setIsPublic(isPublic)
                .setBrukar(brukar)
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

    public void updatePoll(Long pollId, Map<String, String> inputMap) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Poll poll = getPollById(pollId);

        // Iterate over the input to see what we want to update
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            switch (e.getKey()) {
                case "summary" : poll.setSummary(e.getValue());
                break;
                case "isPublic" : poll.setIsPublic(e.getValue().equals("true"));
                break;
            }
        }

        poll.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        entityManager.persist(poll);
        entityManager.getTransaction().commit();

    }

    private Poll getPollById(Long pollId) {
        return entityManager.find(Poll.class, pollId);
    }

}
