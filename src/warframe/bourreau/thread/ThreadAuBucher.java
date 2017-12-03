package warframe.bourreau.thread;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import warframe.bourreau.commands.AdminCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.manager;
import static warframe.bourreau.music.PlaySound.playSoundMention;
import static warframe.bourreau.util.Find.FindAdmin;

public class ThreadAuBucher extends Thread {
    private MessageReceivedEvent event;
    private static boolean isPlayed = true;

    public void run() {
        if (FindAdmin(event, event.getMember())) {
            setPlayedBucher(true);
            playSoundMention(event);
            Tempo.Temporisation(7000);

            while (isPlayed) { System.out.print(""); }
            manager.getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
            AdminCommand.AuBucher(event);
            SonCommand.Leave(event);
        }
    }

    public ThreadAuBucher(MessageReceivedEvent event) { this.event = event; }

    public static void setPlayedBucher(boolean played) { isPlayed = played; }
}