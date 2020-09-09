import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Brukar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String email;
    private String hash;
    private String salt;
    private String created_time;
    private String updated_time;

    @OneToMany(mappedBy = "brukar")
    private List<Poll> polls = new ArrayList<>();


    public List<Poll> getPolls() {
        return polls;
    }

    public Brukar setPolls(List<Poll> polls) {
        this.polls = polls;
        return this;
    }


    public String getName() { return name; }
    public Brukar setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() { return email; }
    public Brukar setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getHash() { return hash; }
    public Brukar setHash(String hash) {
        this.hash = hash;
        return this;
    }

    public String getSalt() { return salt; }
    public Brukar setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getCreated_time() { return created_time; }
    public Brukar setCreated_time(String created_time) {
        this.created_time = created_time;
        return this;
    }

    public String getUpdated_time() { return getUpdated_time(); }
    public Brukar setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
        return this;
    }


}
