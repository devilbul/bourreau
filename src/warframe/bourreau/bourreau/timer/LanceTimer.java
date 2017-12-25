package warframe.bourreau.timer;

import net.dv8tion.jda.core.JDA;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;

import static warframe.bourreau.Bourreau.VINGT_QUATRE_HEURES;

public class LanceTimer {

    public static void lanceRaidTimer(JDA jda) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date time = calendar.getTime();
        Timer timer = new Timer();
        //timer.schedule(new RaidTimer(jda), time, VINGT_QUATRE_HEURES);
    }

    public static void lanceRaidTimer2(JDA jda) {
        Timer timer = new Timer();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(Calendar.HOUR, 24);

        //timer.scheduleAtFixedRate(new RaidTimer(jda), gc.getTime(), VINGT_QUATRE_HEURES);
    }
}
