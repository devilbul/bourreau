package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadTrump extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.TrumpComp(cmd.event);
        Tempo.Temporisation(177000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadTrump(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
}
