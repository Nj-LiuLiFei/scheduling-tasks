package cn.liulifei.schedulingtasks.util;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author LiuLiFei
 *
 */
public class LocalDateTimeUtils {
    private static final String DEFAULT_DATE_TIME_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>(){
        @Override
        protected DateFormat initialValue(){
            return new  SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        }
    };
    public static String getNowDateTime(){
        return df.get().format(new Date());
    }
}
