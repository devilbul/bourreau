package warframe.bourreau.thread;

import warframe.bourreau.commands.AdminCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.manager;
import static warframe.bourreau.music.PlaySound.playSoundMention;
import static warframe.bourreau.util.Find.FindAdmin;

public class ThreadAuBucher extends Thread {
    CommandParser.CommandContainer cmd;
    private static boolean isPlayed = true;

    public void run() {
        if (FindAdmin(cmd.event, cmd.event.getMember())) {
            setPlayedBucher(true);
            playSoundMention(cmd.event);
            Tempo.Temporisation(7000);
            while (isPlayed) { System.out.print(""); }
            manager.getPlayer(cmd.event.getGuild()).getAudioPlayer().stopTrack();
            AdminCommand.AuBucher(cmd.event);
            SonCommand.Leave(cmd.event);
        }
    }

    public ThreadAuBucher(CommandParser.CommandContainer cmd) {
        this.cmd = cmd;
    }

    public static void setPlayedBucher(boolean played) { isPlayed = played; }
}