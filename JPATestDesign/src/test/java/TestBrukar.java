import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestBrukar {
    private static final String PERSISTENCE_UNIT_NAME = "poll";
    private static final String ENVIRONMENT_JDBC_URL = "jdbc_urldb";
    private static EntityManagerFactory factory;
    EntityManager em;
    BrukarDao brukarDao;
    PollDao pollDao;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrideFromEnv());
        em = factory.createEntityManager();
        brukarDao = new BrukarDao(em);
        pollDao = new PollDao(em);
    }

    @Test
    public void testAddUserAddsOneUser() {
        int startLength = brukarDao.getAllBrukars().size();
        Brukar brukar = brukarDao.addBrukar("Test", "test@mail", "testpwd");
        assertEquals(startLength + 1, brukarDao.getAllBrukars().size());
    }

    @Test
    public void testUpdateBrukar() {
        Brukar brukar = brukarDao.addBrukar("beforname", "befor@email.com", "beforepwd");
        System.out.println(brukar.getName());
        BrukarUpdateRequest bur = BrukarUpdateRequest.builder()
                .name("aftername")
                .email("after@email.com")
                .pwd("afterpwd")
                .build();
        brukarDao.updateBrukar(brukar.getId(), bur);
        System.out.println(brukarDao.getBrukarById(brukar.getId()).getName());
        assertEquals(brukarDao.getBrukarById(brukar.getId()).getName(), "aftername");
    }

    @Test
    public void testDeleteBrukar() {
        Brukar brukar = brukarDao.addBrukar("exists", "exist@email.com", "existpwd");
        brukarDao.deleteBrukar(brukar.getId());
        assertNull(brukarDao.getBrukarById(brukar.getId()));
    }

    //POLL -- TESTS
    @Test
    public void testAddPoll(){
        Brukar brukar = brukarDao.addBrukar("exists", "exist@email.com", "existpwd");
        int lengthBefore = pollDao.getAllPolls().size();
        Poll poll = pollDao.addPoll("this is summary", brukar, true);
        assertEquals(lengthBefore + 1, pollDao.getAllPolls().size());
    }

    @Test
    public void updatePoll(){
        Brukar brukar = brukarDao.addBrukar("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("beforeSummary", brukar, true);
        //TODO pollDao.updatePoll(poll.getPoll_id(), );
    }


    public static Map<String, Object> configOverrideFromEnv() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", env.get(ENVIRONMENT_JDBC_URL));

        return configOverrides;
    }
}
