package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static warframe.bourreau.InitID.accueilID;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.MessageOnEvent.MessageDeDeconnection;

public class ShutdownCommand extends Command {

    public static void Shutdown(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember())) {
            MessageDeDeconnection(event);

            if(!event.getTextChannel().getId().equals(accueilID))
                event.getTextChannel().sendMessage("Arrêt en cours !");

            event.getJDA().shutdown(true);
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^");
    }
}
