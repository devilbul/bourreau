package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import warframe.bourreau.util.Command;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.Bourreau.getJda;
import static warframe.bourreau.util.Find.FindRolePrive;
import static warframe.bourreau.messsage.MessagePrive.MessageRecrutement;

public class CandidatCommand extends SimpleCommand {

    @Command(name="candidate")
    public static void Candidate(PrivateMessageReceivedEvent event) {
        if (!FindRolePrive(event, getJda().getGuildById(serveurID).getRoleById(membreAllianceID))) {
            MessageRecrutement(event);
            event.getAuthor().openPrivateChannel().complete().sendMessage("les leaders de clans ont été prévenue de votre candidature !").queue();
        }
        else
            event.getAuthor().openPrivateChannel().complete().sendMessage("tu es déjà membre de l'alliance.\n\n\n\nconno").queue();
    }
}