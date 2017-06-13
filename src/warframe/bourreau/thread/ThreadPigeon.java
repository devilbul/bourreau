package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadPigeon extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Pigeon(cmd.event);
        Tempo.Temporisation(12000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadPigeon(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}