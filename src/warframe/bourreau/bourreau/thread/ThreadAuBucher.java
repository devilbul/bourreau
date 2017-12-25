package warframe.bourreau.thread;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import warframe.bourreau.commands.AdminCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.Init.managers;
import static warframe.bourreau.music.PlaySound.playSoundMention;
import static warframe.bourreau.util.Find.findAdmin;

public class ThreadAuBucher extends Thread {
    private MessageReceivedEvent event;
    private static boolean isPlayed = true;

    public void run() {
        if (findAdmin(event, event.getMember())) {
            setPlayedBucher(true);
            playSoundMention(event);
            Tempo.temporisation(7000);

            while (isPlayed) { System.out.print(""); }
            managers.get(event.getGuild().getId()).getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
            AdminCommand.auBucher(event);
            SonCommand.leave(event);
        }
    }

    public ThreadAuBucher(MessageReceivedEvent event) { this.event = event; }

    public static void setPlayedBucher(boolean played) { isPlayed = played; }
}