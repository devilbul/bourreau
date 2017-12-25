package warframe.bourreau.util;

public class Tempo {

    public static void temporisation(int temps) {
        try {
            Thread.sleep(temps);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
