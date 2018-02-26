package fr.warframe.devilbul.command.modo;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findModo;
import static fr.warframe.devilbul.utils.Recup.recupID;

public class UnDeafenCommand extends SimpleCommand {

    @Command(name = "undeafen")
    @Help(field = "**syntaxe** :      !undeafen @<pseudo>\n**condition :**   l'utilisateur <pseudo> est assourdi soit par un admin/modo, ou soit par commnande **!deafen**\n" +
            "                        \n**effet :**            redonne le droit d'entendre à l'utilisateur", categorie = Categorie.Modo)
    public static void unDeafen(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.VOICE_DEAF_OTHERS)) {
                    String id = recupID(event.getMessage().getMentionedUsers().toString());

                    if (event.getMessage().toString().contains("@")) {
                        if (!event.getGuild().getMemberById(id).isOwner()) {

                            event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), false).submit();
                            event.getTextChannel().sendMessage("client désourdi").queue();
                        } else
                            event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                    } else
                        event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
                } else
                    event.getTextChannel().sendMessage("Le bot n'a pas ce droit.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
