package by.ita.chernook.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final ZoneId defaultZoneId = ZoneId.systemDefault();

    public static String formatZonedDateTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return "";
        }
        return zonedDateTime.withZoneSameInstant(defaultZoneId).format(formatter);
    }
}