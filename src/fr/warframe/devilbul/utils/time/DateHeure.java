package fr.warframe.devilbul.utils.time;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateHeure {

    public static String giveDate() {
        String date;
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");

        cal1.setTimeInMillis(timeInMillis);
        date = dateFormat.format(cal1.getTime());

        return date;
    }

    public static String giveHeure() {
        String heure;
        long timeInMillis = System.currentTimeMillis();
        Calendar cal1 = Calendar.getInstance();
        SimpleDateFormat heureFormat = new SimpleDateFormat("HH:mm:ss ");

        cal1.setTimeInMillis(timeInMillis);
        heure = heureFormat.format(cal1.getTime());

        return heure;
    }
}
