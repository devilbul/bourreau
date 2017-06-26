package warframe.bourreau.handle;

import warframe.bourreau.commands.Command;
import warframe.bourreau.commands.HelpCommand;
import warframe.bourreau.parser.CommandParserPrivate;

import static warframe.bourreau.Main.commands;

public class HandleCommandPrivate {

    public static void handleCommandPrivate(CommandParserPrivate.CommandContainerPrivate cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = Command.called();

            if (safe) {
                switch (cmd.invoke) {
                    case "help":
                        HelpCommand.HelpPrivate(cmd.event);
                        break;
                    default:
                        cmd.event.getAuthor().getPrivateChannel().sendMessage("Commande à faire sur un salon textuel du serveur " + cmd.event.getJDA().getGuilds().get(0).getName() +
                                "\nou commande inconnue !");
                        cmd.event.getAuthor().getPrivateChannel().close();
                        break;
                }
            }
        }
    }
}
