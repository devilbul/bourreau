package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;


public class ThreadSouffrir extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Souffrir(cmd.event);
        Tempo.Temporisation(7000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadSouffrir(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}