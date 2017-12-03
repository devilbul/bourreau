package warframe.bourreau.thread;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.manager;
import static warframe.bourreau.InitID.queueSon;
import static warframe.bourreau.Bourreau.DEFAULT_VOLUME;
import static warframe.bourreau.music.PlaySound.playSound;

public class ThreadSon extends Thread {
    private MessageReceivedEvent event;
    private String sound;
    private static boolean isPlayed = false;

    public void run() {
        setPlayed(true);
        playSound(event, sound, DEFAULT_VOLUME);
        Tempo.Temporisation(4);

        while (isPlayed) { System.out.print(""); }
        manager.getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
        SonCommand.Leave(event);
        queueSon.remove(0);

        if (!queueSon.isEmpty())
            new ThreadSon(event, queueSon.get(0)).start();
    }

    public ThreadSon(MessageReceivedEvent event, String sound) { this.event = event; this.sound = sound; }

    public static void setPlayed(boolean played) { isPlayed = played; }

    public static boolean isPlayed() { return isPlayed; }
}