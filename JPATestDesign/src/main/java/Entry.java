import javax.persistence.*;

@Entity
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entry_id;

    private Value value;
    private int number;

    private String time_submitted;
    @ManyToOne(fetch = FetchType.EAGER)
    private Poll poll;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brukar brukar;


    public Poll getPoll() {
        return poll;
    }

    public Entry setPoll(Poll poll) {
        this.poll = poll;
        return this;
    }

    public Brukar getBrukar() {
        return brukar;
    }

    public Entry setBrukar(Brukar brukar) {
        this.brukar = brukar;
        return this;
    }

    public Value getValue() {
        return value;
    }

    public Entry setValue(Value value) {
        this.value = value;
        return this;
    }

    public int getNumber() {
        return number;
    }

    public Entry setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getTime_submitted() {
        return time_submitted;
    }

    public Entry setTime_submitted(String time_submitted) {
        this.time_submitted = time_submitted;
        return this;
    }
}
