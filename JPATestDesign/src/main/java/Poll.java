import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poll_id;

    private String summary;
    private String created_time;
    private String updated_time;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brukar brukar;

    @OneToMany(mappedBy = "poll")
    private List<Entry> entries = new ArrayList<>();


    public Brukar getBrukar() {
        return brukar;
    }

    public Poll setBrukar(Brukar brukar) {
        this.brukar = brukar;
        return this;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public Poll setEntries(List<Entry> entries) {
        this.entries = entries;
        return this;
    }

    public String getSummary() { return summary; }
    public Poll setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getCreated_time() { return created_time; }
    public Poll setCreated_time(String created_time) {
        this.created_time = created_time;
        return this;
    }

    public String getUpdated_time() { return getUpdated_time(); }
    public Poll setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
        return this;
    }

}
