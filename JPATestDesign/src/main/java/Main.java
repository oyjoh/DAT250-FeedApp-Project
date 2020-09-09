import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PERSISTENCE_UNIT_NAME = "poll";
    private static EntityManagerFactory factory;

    public static void main(String[] args) {
        System.out.println("Hello World!");

        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        // Create User
        Brukar brukar = new Brukar()
                .setName("Gunnar")
                .setEmail("@")
                .setHash("ThisIsTheHash")
                .setSalt("ThisIsTheSalt")
                .setCreated_time("ThisIsCreatedTime")
                .setUpdated_time("ThisIsUpdatedTime");
        // Create Poll
        Poll poll = new Poll()
                .setSummary("ThisIsASummary")
                .setCreated_time("ThisIsCreatedTime")
                .setUpdated_time("ThisIsUpdatedTime")
                .setBrukar(brukar);
        List<Poll> pollList = new ArrayList<>();
        pollList.add(poll);
        // Add the poll to the user
        brukar.setPolls(pollList);
        // Create an entry in the poll
        Entry entry = new Entry()
                .setPoll(poll)
                .setNumber(1)
                .setValue(Value.YES)
                .setTime_submitted("Now")
                .setBrukar(brukar);
        // Add the entry to the poll
        List<Entry> entryList = new ArrayList<>();
        entryList.add(entry);
        poll.setEntries(entryList);

        em.persist(brukar);
        em.persist(poll);
        em.persist(entry);

        em.getTransaction().commit();

        em.close();
    }
}
