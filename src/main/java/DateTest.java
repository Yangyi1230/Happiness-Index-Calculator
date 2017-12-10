import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Author: Spikerman < mail4spikerman@gmail.com >
 * Created Date: 11/30/17
 */
public class DateTest {
    public static void main(String[] args) throws ParseException {
        DateTest d = new DateTest();
        String s = "10/03/2015 12:00:00 AM";
        System.out.println(d.evaluate(s.substring(0, 10)));
    }

    public String evaluate(String datetime) throws ParseException {
        SimpleDateFormat f = new SimpleDateFormat("MM/DD/YYYY");
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        Date dates = f.parse(datetime);
        cal.setTime(dates);
        int d = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (d < 0) d = 0;
        return weekDays[d];
    }

}
