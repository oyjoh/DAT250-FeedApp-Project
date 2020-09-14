import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name="Entry.findAll", query="SELECT e FROM Entry e"),
        @NamedQuery(name="Entry.findByPoll",
                query="SELECT e FROM Entry e WHERE e.poll.poll_id = :poll"),
})
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry_id;

    private Value value;
    private Integer number;

    private String time_submitted;
    @ManyToOne(fetch = FetchType.EAGER)
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brukar brukar;


    public Poll getPoll() {
        return poll;
    }
    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public Brukar getBrukar() {
        return brukar;
    }
    public void setBrukar(Brukar brukar) {
        this.brukar = brukar;
    }

    public Value getValue() {
        return value;
    }
    public void setValue(Value value) {
        this.value = value;
    }

    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getTime_submitted() {
        return time_submitted;
    }
    public void setTime_submitted(String time_submitted) {
        this.time_submitted = time_submitted;
    }

    protected Entry(){}

    private Entry(Poll poll, Brukar brukar, Value value, Integer number, String time_submitted) {
        this.poll = poll;
        this.brukar = brukar;
        this.value = value;
        this.number = number;
        this.time_submitted = time_submitted;
    }

    public static EntryBuilder builder() {
        return new EntryBuilder();
    }

    @Override
    public String toString() {
        return "Entry{" + value.name()+ "," + number +
                '}';
    }


    public static class EntryBuilder {

        private Poll poll;

        private Brukar user;

        private Value value;
        private Integer number;

        private String time_submitted;

        public EntryBuilder setPoll(final Poll poll) {
            this.poll = poll;
            return this;
        }

        public EntryBuilder setBrukar(final Brukar user) {
            this.user = user;
            return this;
        }


        public EntryBuilder setValue(final Value value) {
            this.value = value;
            return this;
        }

        public EntryBuilder setNumber(final Integer number) {
            this.number = number;
            return this;
        }

        public EntryBuilder setTime_submitted(final String time_submitted) {
            this.time_submitted = time_submitted;
            return this;
        }

        public Entry build() {
            return new Entry(poll, user, value, number, time_submitted);
        }

    }

}