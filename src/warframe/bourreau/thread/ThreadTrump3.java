package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadTrump3 extends Thread {

    CommandParser.CommandContainer cmd;

     public void run() {
        SonCommand.TrumpComp3(cmd.event);
        Tempo.Temporisation(178000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadTrump3(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
}
