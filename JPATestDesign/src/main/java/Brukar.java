import jdk.jfr.Name;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Table(name = "user")
@NamedQueries({
        @NamedQuery(name="Brukar.findAll", query="SELECT b FROM Brukar b"),
        @NamedQuery(name="Brukar.findById", query = "SELECT b FROM Brukar b WHERE b.user_id = :brukar_id")
})
public class Brukar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String name;
    private String email;
    private String hash;
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

    public Long getId() { return user_id; }

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

    public String getCreated_time() { return created_time; }
    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() { return updated_time; }
    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String toString() {
        return "id: " + user_id + ", name = " + name;
    }

    protected Brukar(){}

    private Brukar(String name, String email, String hash, String created_time, String updated_time) {
        this.name = name;
        this.email = email;
        this.hash = hash;
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
        private String created_time;
        private String updated_time;

        public BrukarBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public BrukarBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public BrukarBuilder hash(final String hash) {
            this.hash = hash;
            return this;
        }

        public BrukarBuilder created_time(final String created_time) {
            this.created_time = created_time;
            return this;
        }

        public BrukarBuilder updated_time(final String updated_time) {
            this.updated_time = updated_time;
            return this;
        }


        public Brukar build() {
            return new Brukar(name, email, hash, created_time, updated_time);
        }

    }

}
