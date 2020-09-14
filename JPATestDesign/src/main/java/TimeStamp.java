import java.sql.Timestamp;
import java.util.Date;

public class TimeStamp {

    public static String getTimeStamp() {
        Date date = new Date();
        long time = date.getTime();
        return new Timestamp(time).toString();
    }
}
