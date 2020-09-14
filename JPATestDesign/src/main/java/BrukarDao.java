import com.lambdaworks.crypto.SCryptUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.SecureRandom;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class BrukarDao implements IBrukarDao {
    private final Random RANDOM = new SecureRandom();
    private EntityManager entityManager;

    public BrukarDao(EntityManager entityManager) {
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
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Brukar brukar = getBrukarById(brukarId);

        // Iterate over the input to see what we want to update
        for (Map.Entry<String, String> e : inputMap.entrySet()) {
            switch (e.getKey()) {
                case "name":
                    brukar.setName(e.getValue());
                    break;
                case "email":
                    brukar.setEmail(e.getValue());
                    break;
                case "hash":
                    brukar.setHash(getSHA256Hash(e.getValue()));
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

    public void updateBrukar(Long brukarId, BrukarUpdateRequest bur) {
        String updatedTimestamp = TimeStamp.getTimeStamp();
        Brukar brukar = getBrukarById(brukarId);

        // Iterate over the input to see what we want to update
        if (bur.getName() != null) brukar.setName(bur.getName());
        if (bur.getEmail() != null) brukar.setEmail(bur.getEmail());
        if (bur.getPwd() != null) brukar.setHash(getSHA256Hash(bur.getPwd()));
        brukar.setUpdated_time(updatedTimestamp);

        // Update the database
        entityManager.getTransaction().begin();
        //entityManager.merge(brukar);
        entityManager.persist(brukar);
        entityManager.getTransaction().commit();

    }

    @Override
    public void deleteBrukar(Long brukarId) {
        Brukar brukar = getBrukarById(brukarId);
        entityManager.getTransaction().begin();
        entityManager.remove(brukar);
        entityManager.getTransaction().commit();
    }

    public Brukar addBrukar(String name, String email, String pwd) {
        // Create TimeStamp
        // Create hash
        String hash = getSHA256Hash(pwd);
        String timestamp = TimeStamp.getTimeStamp();

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

    public List<Poll> getAllPollsByUser(Long brukarId) {
        return getBrukarById(brukarId).getPolls();
    }

    public Brukar getBrukarById(Long brukarId) {
        return entityManager.find(Brukar.class, brukarId);
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
