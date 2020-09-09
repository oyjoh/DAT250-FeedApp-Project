import javax.persistence.*;
import java.util.List;

@Entity
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poll_id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "poll")
    private List<Entry> entries;

    private String summary;
    private String created_time;
    private String updated_time;

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getCreated_time() { return created_time; }
    public void setCreated_time(String created_time) { this.created_time = created_time; }

    public String getUpdated_time() { return getUpdated_time(); }
    public void setUpdated_time(String updated_time) { this.updated_time = updated_time; }

}
