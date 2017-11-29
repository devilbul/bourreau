package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import warframe.bourreau.util.Command;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.messsage.MessageOnEvent.*;

public class RegleCommand extends SimpleCommand {

    @Command(name="regle")
    public static void Reglement(MessageReceivedEvent event) {
        try {
            MessageReglement(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="regle")
    public static void ReglementPrive(PrivateMessageReceivedEvent event) {
            MessageReglementPrive(event);
    }
}
