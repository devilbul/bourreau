package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadNah extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Nah(cmd.event);
        Tempo.Temporisation(4000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadNah(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}