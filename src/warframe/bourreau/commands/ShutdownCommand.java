package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static warframe.bourreau.InitID.accueilID;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.messsage.MessageOnEvent.MessageDeDeconnection;

public class ShutdownCommand extends Command {

    public static void Shutdown(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                MessageDeDeconnection(event);

                if (!event.getTextChannel().getId().equals(accueilID))
                    event.getTextChannel().sendMessage("Arrêt en cours !").queue();

                event.getJDA().shutdown();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
