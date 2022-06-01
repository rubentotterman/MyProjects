package util;

import java.sql.Timestamp;
import java.util.Date;

public class UniqueIdUtil {
    public static long getUniqueId() {
        Date date = new Date();
        return new Timestamp(date.getTime()).getTime();
    }
}
