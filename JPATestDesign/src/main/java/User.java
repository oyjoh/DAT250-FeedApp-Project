import javax.persistence.*;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String email;

    @OneToMany(mappedBy = "user")
    private List<Poll> polls;

    private String hash;
    private String salt;

    private String created_time;
    private String updated_time;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getHash() { return hash; }
    public void setHash(String hash) { this.hash = hash; }

    public String getSalt() { return salt; }
    public void setSalt(String salt) { this.salt = salt; }

    public String getCreated_time() { return created_time; }
    public void setCreated_time(String created_time) { this.created_time = created_time; }

    public String getUpdated_time() { return getUpdated_time(); }
    public void setUpdated_time(String updated_time) { this.updated_time = updated_time; }


}
