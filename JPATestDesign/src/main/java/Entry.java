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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTime_submitted() {
        return time_submitted;
    }

    public void setTime_submitted(String time_submitted) {
        this.time_submitted = time_submitted;
    }
}
