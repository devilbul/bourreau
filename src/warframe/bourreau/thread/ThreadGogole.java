package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadGogole extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Gogole(cmd.event);
        Tempo.Temporisation(12000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadGogole(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}