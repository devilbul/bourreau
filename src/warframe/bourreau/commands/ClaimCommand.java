package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import static warframe.bourreau.messsage.MessagePrive.MessageReclamationAdmin;

public class ClaimCommand extends Command {

    public static void Claim(PrivateMessageReceivedEvent event) {
        MessageReclamationAdmin(event);
        event.getAuthor().openPrivateChannel().complete().sendMessage("les administrateurs ont été prévenue de votre réclamation !").queue();
    }
}

