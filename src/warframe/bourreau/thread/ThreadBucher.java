package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadBucher extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Bucher(cmd.event);
        Tempo.Temporisation(8000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadBucher(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}