import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "user")
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
    public void setPolls(List<Poll> polls) {
        this.polls = polls;
    }

    public String getName() { return name; }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHash() { return hash; }
    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSalt() { return salt; }
    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getCreated_time() { return created_time; }
    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() { return updated_time; }
    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    protected Brukar(){}

    private Brukar(String name, String email, String hash, String salt, String created_time, String updated_time) {
        this.name = name;
        this.email = email;
        this.hash = hash;
        this.salt = salt;
        this.updated_time = updated_time;
        this.created_time = created_time;
    }

    public static BrukarBuilder builder() {
        return new BrukarBuilder();
    }

    public static class BrukarBuilder {

        private String name;
        private String email;
        private String hash;
        private String salt;
        private String created_time;
        private String updated_time;

        public BrukarBuilder setName(final String name) {
            this.name = name;
            return this;
        }

        public BrukarBuilder setEmail(final String email) {
            this.email = email;
            return this;
        }

        public BrukarBuilder setHash(final String hash) {
            this.hash = hash;
            return this;
        }

        public BrukarBuilder setSalt(final String salt) {
            this.salt = salt;
            return this;
        }

        public BrukarBuilder setCreated_time(final String created_time) {
            this.created_time = created_time;
            return this;
        }

        public BrukarBuilder setUpdated_time(final String updated_time) {
            this.updated_time = updated_time;
            return this;
        }


        public Brukar build() {
            return new Brukar(name, email, hash, salt, created_time, updated_time);
        }

    }

}
