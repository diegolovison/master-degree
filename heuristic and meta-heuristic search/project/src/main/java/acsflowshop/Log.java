package acsflowshop;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Diego_Lovison on 6/3/2014.
 */
public class Log {

    private static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void info(Object message) {

        System.out.println(format.format(new Date()) +" " + message);
    }
}
