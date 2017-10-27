package warframe.bourreau.thread;

import warframe.bourreau.commands.AdminCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.music.PlaySound.playSoundMention;
import static warframe.bourreau.util.Find.FindAdmin;

public class ThreadAuBucher extends Thread {
    CommandParser.CommandContainer cmd;

    public void run() {
        if (FindAdmin(cmd.event, cmd.event.getMember())) {
            playSoundMention(cmd.event);
            Tempo.Temporisation(7000);
            AdminCommand.AuBucher(cmd.event);
            SonCommand.Leave(cmd.event);
        }
    }

    public ThreadAuBucher(CommandParser.CommandContainer cmd) {
        this.cmd = cmd;
    }
}