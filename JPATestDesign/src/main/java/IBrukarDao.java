import java.util.List;
import java.util.Map;

public interface IBrukarDao {

    /**
     * Get all the users from the database
     * @return List of all users
     */
    public List<Brukar> getAllBrukars();

    /**
     * Update a user in the database. Returns the user object so it can be modified
     */
    public void updateBrukar(Long brukarId, Map<String, String> inputMap);

    /**
     * Delete a user from the database
     */
    public void deleteBrukar(Long bruklarId);

    /**
     * Add a user to the database
     */
    public Brukar addBrukar(String name, String email, String pwd);

}
