import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TestPerson {
    private static final String PERSISTENCE_UNIT_NAME = "poll";
    private static final String ENVIRONMENT_JDBC_URL = "jdbc_urldb";
    private static EntityManagerFactory factory;
    EntityManager em;
    PersonDao personDao;
    PollDao pollDao;
    EntryDao entryDao;

    @Before
    public void setUp() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, configOverrideFromEnv());
        em = factory.createEntityManager();
        personDao = new PersonDao(em);
        pollDao = new PollDao(em);
        entryDao = new EntryDao(em);
    }

    @Test
    public void testAddUserAddsOneUser() {
        int startLength = personDao.getAllPersons().size();
        Person person = personDao.addPerson("Test", "test@mail", "testpwd");
        assertEquals(startLength + 1, personDao.getAllPersons().size());
    }

    @Test
    public void testUpdatePerson() {
        Person person = personDao.addPerson("beforname", "befor@email.com", "beforepwd");
        System.out.println(person.getName());
        PersonUpdateRequest bur = PersonUpdateRequest.builder()
                .name("aftername")
                .email("after@email.com")
                .pwd("afterpwd")
                .build();
        personDao.updatePerson(person.getId(), bur);
        System.out.println(personDao.getPersonById(person.getId()).getName());
        assertEquals(personDao.getPersonById(person.getId()).getName(), "aftername");
    }

    @Test
    public void testDeletePerson() {
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        personDao.deletePerson(person.getId());
        assertNull(personDao.getPersonById(person.getId()));
    }

    //POLL -- TESTS
    @Test
    public void testAddPoll(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        int lengthBefore = pollDao.getAllPolls().size();
        Poll poll = pollDao.addPoll("this is summary", person, true);
        assertEquals(lengthBefore + 1, pollDao.getAllPolls().size());
    }

    @Test
    public void testUpdatePoll(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("beforeSummary", person, Boolean.TRUE);
        PollUpdateRequest pur = PollUpdateRequest.builder()
                .isPublic(Boolean.FALSE)
                .summary("afterSummary")
                .build();
        pollDao.updatePoll(poll.getPoll_id(), pur);
        assertEquals(pollDao.getPollById(poll.getPoll_id()).getSummary(), "afterSummary");
    }

    @Test
    public void testDeletePoll(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("beforeSummary", person, Boolean.TRUE);
        pollDao.deletePoll(poll.getPoll_id());
        assertNull(pollDao.getPollById(poll.getPoll_id()));
    }

    //Entry -- TESTS
    @Test
    public void testAddEntry(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("this is summary", person, true);
        int lengthBefore = entryDao.getAllEntries().size();
        Entry entry = entryDao.addEntry(Value.NO, 1, person, poll);
        assertEquals(lengthBefore + 1, entryDao.getAllEntries().size());
    }

    @Test
    public void testUpdateEntry(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("beforeSummary", person, Boolean.TRUE);
        Entry entry = entryDao.addEntry(Value.NO, 1, person, poll);
        EntryUpdateRequest eur = EntryUpdateRequest.builder()
                .number(2)
                .value(Value.YES)
                .build();
        entryDao.updateEntry(entry.getEntry_id(), eur);
        assertEquals(entryDao.getEntryById(entry.getEntry_id()).getNumber(), Integer.valueOf(2));
    }

    @Test
    public void testDeleteEntry(){
        Person person = personDao.addPerson("exists", "exist@email.com", "existpwd");
        Poll poll = pollDao.addPoll("beforeSummary", person, Boolean.TRUE);
        Entry entry = entryDao.addEntry(Value.NO, 1, person, poll);
        entryDao.deleteEntry(entry.getEntry_id());
        assertNull(entryDao.getEntryById(entry.getEntry_id()));
    }

    public static Map<String, Object> configOverrideFromEnv() {
        Map<String, String> env = System.getenv();
        Map<String, Object> configOverrides = new HashMap<>();
        configOverrides.put("javax.persistence.jdbc.url", env.get(ENVIRONMENT_JDBC_URL));

        return configOverrides;
    }
}
