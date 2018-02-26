package warframe.bourreau.handle;

import warframe.bourreau.commands.SimpleCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;
import warframe.bourreau.util.WaitingSound;

import static warframe.bourreau.Init.queueSon;
import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.thread.ThreadSon.isPlayed;

class HandleSonCommand {

    static void handleSonCommand(CommandParser.CommandContainer cmd){

        if (commands.containsKey(cmd.invoke)) {
            boolean safe = SimpleCommand.called();

            if (!isPlayed())
                    queueSon.add(new WaitingSound(cmd.event, "first"));
                    Tempo.temporisation(4);

            if (safe) {
                switch (cmd.invoke) {
                    case "acdc":
                        SonCommand.adcd(cmd.event);
                        break;
                    case "ah":
                        
                        SonCommand.ah(cmd.event);
                        break;
                    case "bucher":
                        SonCommand.bucher(cmd.event);
                        break;
                    case "gg":
                        SonCommand.gg(cmd.event);
                        break;
                    case "gogole":
                        SonCommand.gogole(cmd.event);
                        break;
                    case "nah":
                        SonCommand.nah(cmd.event);
                        break;
                    case "leave":
                        SonCommand.leave(cmd.event);
                        break;
                    case "pigeon":
                        SonCommand.pigeon(cmd.event);
                        break;
                    case "son":
                        SonCommand.son(cmd.event);
                        break;
                    case "souffrir":
                        SonCommand.souffrir(cmd.event);
                        break;
                    case "trump":
                        SonCommand.trump(cmd.event);
                        break;
                    case "trumpcomp":
                        SonCommand.trumpcomp(cmd.event);
                        break;
                    case "trumpcomp2":
                        SonCommand.trumpcomp2(cmd.event);
                        break;
                    case "trumpcomp3":
                        SonCommand.trumpcomp3(cmd.event);
                        break;
                    default:
                        cmd.event.getTextChannel().sendMessage("NOPE").queue();
                        break;
                }
            }
        }
    }
}
