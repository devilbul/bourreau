package warframe.bourreau.thread;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.Init.managers;
import static warframe.bourreau.Init.queueSon;
import static warframe.bourreau.music.PlaySound.playSound;
import static warframe.bourreau.util.Find.findUserVC;

public class ThreadSon extends Thread {
    private MessageReceivedEvent event;
    private String sound;
    private static boolean isPlayed = false;

    public void run() {
        setPlayed(true);
        playSound(event, sound);
        Tempo.temporisation(4);

        while (isPlayed) { System.out.print(""); }
        managers.get(event.getGuild().getId()).getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
        SonCommand.leave(event);

        if (!queueSon.isEmpty())
            queueSon.remove(0);

        if (!queueSon.isEmpty())
            if (findUserVC(queueSon.get(0).getEvent()) == null)
                queueSon.remove(0);

        if (!queueSon.isEmpty())
            new ThreadSon(queueSon.get(0).getEvent(), queueSon.get(0).getCommandeSon()).start();
        else
            setPlayed(false);
    }

    public ThreadSon(MessageReceivedEvent event, String sound) { this.event = event; this.sound = sound; }

    public static void setPlayed(boolean played) { isPlayed = played; }

    public static boolean isPlayed() { return isPlayed; }
}