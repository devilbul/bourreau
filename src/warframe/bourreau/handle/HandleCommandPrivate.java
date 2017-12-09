package warframe.bourreau.handle;

import net.dv8tion.jda.core.MessageBuilder;
import warframe.bourreau.commands.*;
import warframe.bourreau.parser.CommandParserPrivate;

import static warframe.bourreau.Bourreau.commands;

public class HandleCommandPrivate {

    public static void handleCommandPrivate(CommandParserPrivate.CommandContainerPrivate cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = SimpleCommand.called();

            if (safe) {
                switch (cmd.invoke) {
                    /*case "candidate":
                        CandidatCommand.Candidate(cmd.event);
                        break;
                    case "claim":
                        ClaimCommand.Claim(cmd.event);
                        break;*/
                    case "help":
                        HelpCommand.HelpPrivate(cmd.event);
                        break;
                    case "about":
                        BasedCommand.AboutBotPrivate(cmd.event);
                        break;
                    default:
                        cmd.event.getAuthor().openPrivateChannel().complete().sendMessage("Commande à faire sur un salon textuel du serveur " + cmd.event.getJDA().getGuilds().get(0).getName() + "\nou commande inconnue !").complete();
                        break;
                }
            }
        } else {
            MessageBuilder message = new MessageBuilder();

            cmd.event.getAuthor().openPrivateChannel().complete().sendMessage("Commande inconnue. !help pour lister les commandes (sur un salon textuel ou en message privé).").queue();

            message.append("You know nothing, ");
            message.append(cmd.event.getAuthor());

            cmd.event.getAuthor().openPrivateChannel().complete().sendMessage(message.build()).queue();
        }
    }
}
