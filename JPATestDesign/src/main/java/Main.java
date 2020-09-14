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


        // Create User
        BrukarDao brukarDao = new BrukarDao(em);
        Brukar brukar = brukarDao.addBrukar("Øyvind", "ø@mail.com", "passord1234");



        // Create Poll
        /*
        Poll poll = Poll.builder()
                .setSummary("ThisIsASummary")
                .setCreated_time("ThisIsCreatedTime")
                .setUpdated_time("ThisIsUpdatedTime")
                .setBrukar(brukar)
                .build();

         */
        PollDao pollDao = new PollDao(em);
        Poll poll = pollDao.addPoll("ThisIsSummary", brukar, true);
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

        em.getTransaction().begin();
        //em.persist(brukar);
        em.persist(poll);


        em.getTransaction().commit();

        Query query = em.createNamedQuery("Entry.findByPoll", Entry.class);
        query.setParameter("poll", poll.getPoll_id());

        @SuppressWarnings("unchecked")
        List<Entry> results = query.getResultList();
        for (Entry entry : results) {
            System.out.println(entry.toString());
        }


        testUpdateBrukar(brukarDao);

        // Create user to be deleted
        Brukar brukarToBeDeleted = brukarDao.addBrukar("JOHANNESEN", "DELETE@mail.com", "plizdontdelete");
        Brukar lastBrukar = testGetAllBrukars(brukarDao);
        System.out.println("Last user: " + lastBrukar + ", this will be deleted");
        testDeleteBrukar(brukarDao, lastBrukar.getId());

        em.close();

    }

    // TODO Create JUnit tests
    private static Brukar testGetAllBrukars(BrukarDao brukarDao) {
        List<Brukar> allBrukars = brukarDao.getAllBrukars();
        for (Brukar b : allBrukars) {
            System.out.println(b);
        }
        return allBrukars.get(allBrukars.size()-1);
    }

    private static void testUpdateBrukar(BrukarDao brukarDao) {
        Long brukarId = 101L;
        Map<String, String> map = new HashMap<>();
        String newName = "NEWNAME MC.NEWNAME";
        map.put("name", newName);
        brukarDao.updateBrukar(brukarId, map);
    }

    private static void testDeleteBrukar(BrukarDao brukarDao, Long id) {
        brukarDao.deleteBrukar(id);

    }

    public static Map<String, Object> configOverrideFromEnv() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", env.get(ENVIRONMENT_JDBC_URL));

        return configOverrides;
    }
}
