package warframe.bourreau.util;

public class Tempo {

    public static void Temporisation(int temps) {
        try {
            Thread.sleep(temps);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
