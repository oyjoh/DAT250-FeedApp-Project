import com.lambdaworks.crypto.SCryptUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BrukarDao implements IBrukarDao{
    private final Random RANDOM = new SecureRandom();
    private EntityManager entityManager;

    public BrukarDao (EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Brukar> getAllBrukars() {
        Query query = entityManager.createNamedQuery("Brukar.findAll", Brukar.class);
        return query.getResultList();
    }

    @Override
    public void updateBrukar(Long brukarId, Map<String, String> inputMap) {
        // Create UpdatedTimeStamp
        Date date = new Date();
        long updateTime = date.getTime();
        String updatedTimestamp = new Timestamp(updateTime).toString();
        Brukar brukar = getBrukarByID(brukarId);

        // Iterate over the input to see what we want to update
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            switch (e.getKey()) {
                case "name" : brukar.setName(e.getValue());
                break;
                case "email" : brukar.setEmail(e.getValue());
                break;
                case "hash" : brukar.setHash(getSHA256Hash(e.getValue()));
                break;
            }
        }
        brukar.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        //entityManager.merge(brukar);
        entityManager.persist(brukar);
        entityManager.getTransaction().commit();

    }

    @Override
    public void deleteBrukar(Long brukarId) {
        Brukar brukar = getBrukarByID(brukarId);
        entityManager.getTransaction().begin();
        entityManager.remove(brukar);
        entityManager.getTransaction().commit();
    }

    public Brukar addBrukar(String name, String email, String pwd) {
        // Create TimeStamp
        Date date = new Date();
        long time = date.getTime();
        String timestamp = new Timestamp(time).toString();
        // Create hash
        String hash = getSHA256Hash(pwd);

        Brukar brukar = Brukar.builder()
                .setName(name)
                .setEmail(email)
                .setHash(hash)
                .setCreated_time(timestamp)
                .setUpdated_time(timestamp)
                .build();

        // Add the new user to the database
        entityManager.getTransaction().begin();
        entityManager.persist(brukar);
        entityManager.getTransaction().commit();

        return brukar;
    }

    private Brukar getBrukarByID(Long brukarId) {
        Brukar brukar = entityManager.find(Brukar.class, brukarId);
        /* TODO remove this?
        // Find the user by using a named query
        Query query = entityManager.createNamedQuery("Brukar.findById", Brukar.class);
        query.setParameter("brukar_id", brukarId);
        // TODO rather use query.getResultList()?
        return (Brukar) query.getSingleResult();

        */
        return brukar;
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
