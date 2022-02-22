package pl.raportsa.memelibrary.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");

    public static String formatDate(Date date) {
        return sdfDate.format(date);
    }
}
