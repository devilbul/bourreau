package warframe.bourreau.parser;

public class TimeParser {

    public static long parseTimeToSec(String time) {
        long sec;

        long hour = Long.valueOf(time.split(":")[0]);
        long minute = Long.valueOf(time.split(":")[1]);
        long seconde = Long.valueOf(time.split(":")[2]);

        sec = seconde + minute * 60 + hour * 3600;

        return sec;
    }

    public static String parseSecToTime(long sec) {
        String time = "";
        int hh;
        int mm;

        hh = (int) (sec / 3600);
        sec -= hh * 3600;
        if (hh >= 10) time += hh + ":";
        else time += "0" + hh + ":";

        mm = (int) (sec / 60);
        sec -= mm * 60;
        if (mm >= 10) time += mm + ":";
        else time += "0" + mm + ":";

        if (sec >= 10) time += sec;
        else time += "0" + sec;

        if (time.startsWith("00:"))
            time = time.substring(3);

        return time;
    }
}
