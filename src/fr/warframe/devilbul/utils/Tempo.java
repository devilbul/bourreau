package fr.warframe.devilbul.utils;

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
