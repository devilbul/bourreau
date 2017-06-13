package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadTrump2 extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.TrumpComp2(cmd.event);
        Tempo.Temporisation(187000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadTrump2(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
}
