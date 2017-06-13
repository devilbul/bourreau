package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

public class ThreadAh extends Thread {

    CommandParser.CommandContainer cmd;

    public void run() {
        SonCommand.Ah(cmd.event);
        Tempo.Temporisation(7000);
        SonCommand.Stop(cmd.event);
    }

    public ThreadAh(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
}
