import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class TestBrukar {
    private static final String PERSISTENCE_UNIT_NAME = "poll";
    private static final String ENVIRONMENT_JDBC_URL = "jdbc_urldb";
    private static EntityManagerFactory factory;
    EntityManager em ;
    BrukarDao brukarDao;

    @Before
    public void setUp(){
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrideFromEnv());
        em = factory.createEntityManager();
        brukarDao = new BrukarDao(em);
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
        Map<String, String> updateMap = new HashMap<>();
        updateMap.put("name", "aftername");
        updateMap.put("email", "after@email.com");
        updateMap.put("hash", "afterpwd");
        brukarDao.updateBrukar(brukar.getId(), updateMap);
        System.out.println(brukarDao.getBrukarById(brukar.getId()).getName());
        assertEquals(brukarDao.getBrukarById(brukar.getId()).getName(), "aftername");
    }

    @Test
    public void testDeleteBrukar() {
        Brukar brukar = brukarDao.addBrukar("exists", "exist@email.com", "existpwd");
        brukarDao.deleteBrukar(brukar.getId());
        assertNull(brukarDao.getBrukarById(brukar.getId()));
    }


    public static Map<String, Object> configOverrideFromEnv() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", env.get(ENVIRONMENT_JDBC_URL));

        return configOverrides;
    }
}