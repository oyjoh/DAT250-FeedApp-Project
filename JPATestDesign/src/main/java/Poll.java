import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "Poll.findAll", query = "SELECT p FROM Poll p"),
})
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long poll_id;

    private String summary;
    private String created_time;
    private String updated_time;
    private String joinKey;
    private Boolean isPublic;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brukar brukar;

    @OneToMany(mappedBy = "poll")
    private List<Entry> entries = new ArrayList<>();


    public Long getPoll_id() {
        return poll_id;
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    public String getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(String joinKey) {
        this.joinKey = joinKey;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    protected Poll() {
    }

    private Poll(Brukar brukar, String summary, String created_time, String updated_time, Boolean isPublic) {
        this.summary = summary;
        this.created_time = created_time;
        this.updated_time = updated_time;
        this.brukar = brukar;
        this.isPublic = isPublic;
    }

    public static PollBuilder builder() {
        return new PollBuilder();
    }

    public static class PollBuilder {

        private Brukar brukar;
        private String summary;
        private String created_time;
        private String updated_time;
        private Boolean isPublic;


        public PollBuilder brukar(final Brukar brukar) {
            this.brukar = brukar;
            return this;
        }

        public PollBuilder summary(final String summary) {
            this.summary = summary;
            return this;
        }

        public PollBuilder created_time(final String created_time) {
            this.created_time = created_time;
            return this;
        }

        public PollBuilder updated_time(final String updated_time) {
            this.updated_time = updated_time;
            return this;
        }

        public PollBuilder isPublic(final Boolean isPublic) {
            this.isPublic = isPublic;
            return this;
        }


        public Poll build() {
            return new Poll(brukar, summary, created_time, updated_time, isPublic);
        }

    }
}
