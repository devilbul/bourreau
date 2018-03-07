package fr.warframe.devilbul.thread;

import fr.warframe.devilbul.utils.Tempo;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Init.audioManagers;
import static fr.warframe.devilbul.Init.managers;
import static fr.warframe.devilbul.Init.queueSon;
import static fr.warframe.devilbul.music.PlaySound.playSound;

public class ThreadSon implements Runnable {

    private static boolean isPlayed = false;
    private static boolean canPlay = true;
    private static boolean canDisconnect = false;
    private static MessageReceivedEvent event;

    public void run() {
        while (true) {
            if (canPlay) {
                Tempo.temporisation(4);

                if (queueSon.size() > 0) {
                    isPlayed = true;
                    event = queueSon.get(0).getEvent();

                    playSound(event, queueSon.get(0).getCommandeSon());
                    while (isPlayed) { System.out.print(""); }
                    managers.get(event.getGuild().getId()).getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
                    Tempo.temporisation(4);
                    if (queueSon.size() > 0) queueSon.remove(0);
                    canDisconnect = true;
                }

                if (queueSon.size() > 0)
                    canDisconnect = false;

                if (canDisconnect) {
                    isPlayed = false;
                    audioManagers.get(event.getGuild().getId()).closeAudioConnection();
                }

                Tempo.temporisation(10000);
            } else {
                Tempo.temporisation(1000);
                System.out.println("");
            }
        }
    }

    public ThreadSon() {
    }

    public static void setPlayed(boolean played) {
        isPlayed = played;
    }

    public static boolean isPlayed() {
        return isPlayed;
    }

    public static void stopPlay() {
        try {
            String pathJson = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "music.json")));
            JSONObject musicJson = new JSONObject(pathJson);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "music.json");

            file.write(musicJson.put("can_play", canPlay = false).toString(3));
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startPlay() {
        try {
            String pathJson = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "music.json")));
            JSONObject musicJson = new JSONObject(pathJson);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "music.json");

            file.write(musicJson.put("can_play", canPlay = true).toString(3));
            file.flush();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean canPlay() {
        return canPlay;
    }
}