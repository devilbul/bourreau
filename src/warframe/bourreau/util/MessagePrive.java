package warframe.bourreau.util;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

import static warframe.bourreau.InitID.adminID;

public class MessagePrive {

    private static void EnvoiMessage(Member destinataire, Message message) {
        destinataire.getUser().openPrivateChannel().complete();
        destinataire.getUser().getPrivateChannel().sendMessage(message).queue();
        destinataire.getUser().getPrivateChannel().close();
    }

    public static void MessageReclamationAdmin(MessageReceivedEvent event) {
        Role admin = event.getGuild().getRoleById(adminID);
        List<Member> listAdmin = event.getGuild().getMembersWithRoles(admin);
        MessageBuilder reclamation = new MessageBuilder();

        reclamation.append(event.getAuthor());
        reclamation.append(" a fait une réclamation.\n\nmessage :   ").append(event.getMessage().getContent());

        for (Member aListAdmin : listAdmin) {
            if (!aListAdmin.getUser().equals(event.getJDA().getSelfUser()))
                EnvoiMessage(aListAdmin, reclamation.build());
        }
    }

    public static void MessageRecrutement(MessageReceivedEvent event) {
        Role admin = event.getGuild().getRoleById(adminID);
        List<Member> listAdmin = event.getGuild().getMembersWithRoles(admin);
        MessageBuilder reclamation = new MessageBuilder();

        reclamation.append(event.getAuthor());
        reclamation.append(" souhaite rejoindre l'Alliance.\n\nmessage :   ").append(event.getMessage().getContent());

        for (Member aListAdmin : listAdmin) {
            if (!aListAdmin.getUser().equals(event.getJDA().getSelfUser()))
                EnvoiMessage(aListAdmin, reclamation.build());
        }
    }
}
