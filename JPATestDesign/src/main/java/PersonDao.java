import com.lambdaworks.crypto.SCryptUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class PersonDao {
    private EntityManager entityManager;

    public PersonDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public List<Person> getAllPersons() {
        Query query = entityManager.createNamedQuery("Person.findAll", Person.class);
        return query.getResultList();
    }

    public void updatePerson(Long personId, Map<String, String> inputMap) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Person person = getPersonById(personId);

        // Iterate over the input to see what we want to update
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            switch (e.getKey()) {
                case "name":
                    person.setName(e.getValue());
                    break;
                case "email":
                    person.setEmail(e.getValue());
                    break;
                case "hash":
                    person.setHash(getSHA256Hash(e.getValue()));
                    break;
            }
        }
        person.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        //entityManager.merge(person);
        entityManager.persist(person);
        entityManager.getTransaction().commit();

    }

    public void updatePerson(Long personId, PersonUpdateRequest bur) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Person person = getPersonById(personId);

        // Iterate over the input to see what we want to update
        if (bur.getName() != null) person.setName(bur.getName());
        if (bur.getEmail() != null) person.setEmail(bur.getEmail());
        if (bur.getPwd() != null) person.setHash(getSHA256Hash(bur.getPwd()));
        person.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        //entityManager.merge(person);
        entityManager.persist(person);
        entityManager.getTransaction().commit();

    }
    
    public void deletePerson(Long personId) {
        Person person = getPersonById(personId);
        entityManager.getTransaction().begin();
        entityManager.remove(person);
        entityManager.getTransaction().commit();
    }

    public Person addPerson(String name, String email, String pwd) {
        // Create TimeStamp
        // Create hash
        String hash = getSHA256Hash(pwd);
        String timestamp = TimeStamp.getTimeStamp();

        Person person = Person.builder()
                .name(name)
                .email(email)
                .hash(hash)
                .created_time(timestamp)
                .updated_time(timestamp)
                .build();

        // Add the new user to the database
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();

        return person;
    }

    public List<Poll> getAllPollsByUser(Long personId) {
        return getPersonById(personId).getPolls();
    }

    public Person getPersonById(Long personId) {
        return entityManager.find(Person.class, personId);
    }

    /**
     * @param pwd password to be hashed
     * @return hash using SCrypt
     */
    private String getSHA256Hash(String pwd) {
        int N = 16384, r = 8, p = 1;
        return SCryptUtil.scrypt(pwd, N, r, p);
    }
}
