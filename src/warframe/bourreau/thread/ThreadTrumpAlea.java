package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadTrumpAlea extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        int[] listDuree = {6, 14, 7, 9, 6, 7, 6, 16, 6, 10, 6, 6, 5, 6, 7, 5, 10, 4, 16, 7, 14, 14, 14, 13, 19, 12, 9, 9, 4, 8, 7, 28, 16, 1, 17, 3, 10, 10, 10};
        int choix;
        choix = SonCommand.Trump(cmd.event);
        Tempo.Temporisation((listDuree[choix-1] + 5) * 1000 + 4000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadTrumpAlea(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
}
