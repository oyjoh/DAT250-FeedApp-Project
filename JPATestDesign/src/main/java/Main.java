import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "poll";
    private static final String ENVIRONMENT_JDBC_URL = "jdbc_url";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrideFromEnv());

        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        // Create User
        Brukar brukar = Brukar.builder()
                .setName("Gunnar")
                .setEmail("user@mail.com")
                .setHash("ThisIsTheHash")
                .setSalt("ThisIsTheSalt")
                .setCreated_time("ThisIsCreatedTime")
                .setUpdated_time("ThisIsUpdatedTime")
                .build();
        // Create Poll
        Poll poll = Poll.builder()
                .setSummary("ThisIsASummary")
                .setCreated_time("ThisIsCreatedTime")
                .setUpdated_time("ThisIsUpdatedTime")
                .setBrukar(brukar)
                .build();
        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        // Add the poll to the user
        brukar.setPolls(pollList);
        // Create an entry in the poll
        List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Entry entry = Entry.builder()
                    .setNumber((int) (Math.random() * 100))
                    .setValue(Value.NO)
                    .setTime_submitted("Now")
                    .setBrukar(brukar)
                    .setPoll(poll)
                    .build();
            em.persist(entry);
            entryList.add(entry);
        }
        poll.setEntries(entryList);
        // Add the entry to the poll

        em.persist(brukar);
        em.persist(poll);


        em.getTransaction().commit();

        Query query = em.createNamedQuery("Entry.findByPoll", Entry.class);
        query.setParameter("poll", poll.getPoll_id());

        @SuppressWarnings("unchecked")
        List<Entry> results = query.getResultList();
        for (Entry entry : results) {
            System.out.println(entry.toString());
        }

        em.close();
    }

    public static Map<String, Object> configOverrideFromEnv() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", env.get(ENVIRONMENT_JDBC_URL));

        return configOverrides;
    }
}
