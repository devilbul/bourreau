package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import warframe.bourreau.util.Command;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.messsage.MessageOnEvent.*;

public class RegleCommand extends SimpleCommand {

    @Command(name="regle", subCommand=false)
    public static void reglement(MessageReceivedEvent event) {
        try {
            messageReglement(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
