package fr.warframe.devilbul.thread;

import fr.warframe.devilbul.utils.Tempo;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.Init.managers;
import static fr.warframe.devilbul.music.PlaySound.playSoundMention;
import static fr.warframe.devilbul.utils.Find.findAdmin;

public class ThreadAuBucher extends Thread {
    /*private MessageReceivedEvent event;
    private static boolean isPlayed = true;

    public void run() {
        if (findAdmin(event, event.getMember())) {
            setPlayedBucher(true);
            playSoundMention(event, "bucher.mp4");
            Tempo.temporisation(7000);

            while (isPlayed) { System.out.print(""); }
            managers.get(event.getGuild().getId()).getPlayer(event.getGuild()).getAudioPlayer().stopTrack();
            AdminCommand.auBucher(event);
            SonCommand.leave(event);
        }
    }

    public ThreadAuBucher(MessageReceivedEvent event) { this.event = event; }

    public static void setPlayedBucher(boolean played) { isPlayed = played; }*/
}