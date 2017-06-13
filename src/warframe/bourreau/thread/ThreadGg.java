package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;


public class ThreadGg extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Gg(cmd.event);
        Tempo.Temporisation(6000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadGg(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}