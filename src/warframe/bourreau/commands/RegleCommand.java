package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.MessageOnEvent.*;

public class RegleCommand extends Command {

    public static void Reglement(MessageReceivedEvent event) {
        try {
            MessageReglement(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void ReglementPrive(PrivateMessageReceivedEvent event) {
            MessageReglementPrive(event);
    }
}
