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

    public void setBrukar(Brukar brukar) {
        this.brukar = brukar;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public String getSummary() { return summary; }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreated_time() { return created_time; }
    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() { return updated_time; }
    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    protected Poll(){}

    private Poll(Brukar brukar, String summary, String created_time, String updated_time) {
        this.summary = summary;
        this.created_time = created_time;
        this.updated_time = updated_time;
        this.brukar = brukar;
    }

    public static PollBuilder builder() {
        return new PollBuilder();
    }

    public static class PollBuilder {

        private Brukar brukar;
        private String summary;
        private String created_time;
        private String updated_time;

        private List<Entry> entries = new ArrayList<>();


        public PollBuilder setBrukar(final Brukar brukar) {
            this.brukar = brukar;
            return this;
        }

        public PollBuilder setSummary(final String summary) {
            this.summary = summary;
            return this;
        }

        public PollBuilder setCreated_time(final String created_time) {
            this.created_time = created_time;
            return this;
        }

        public PollBuilder setUpdated_time(final String updated_time) {
            this.updated_time = updated_time;
            return this;
        }


        public Poll build() {
            return new Poll(brukar, summary, created_time, updated_time);
        }

    }
}
