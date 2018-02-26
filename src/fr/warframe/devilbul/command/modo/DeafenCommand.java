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
import static fr.warframe.devilbul.utils.Find.findUserVC;
import static fr.warframe.devilbul.utils.Recup.recupID;

public class DeafenCommand extends SimpleCommand {

    @Command(name = "deafen")
    @Help(field = "**syntaxe** :      !deafen @<pseudo>\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal\n" +
            "                        \n**effet :**            retire le droit d'entendre à l'utilisateur", categorie = Categorie.Modo)
    public static void deafen(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.VOICE_DEAF_OTHERS)) {
                    String id = recupID(event.getMessage().getMentionedUsers().toString());

                    if (event.getMessage().toString().contains("@")) {
                        if (!event.getGuild().getMemberById(id).isOwner()) {
                            if (findUserVC(event) != null) {
                                event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), true).submit();
                                event.getTextChannel().sendMessage("client assourdi").queue();
                            } else
                                event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été assourdi.").queue();
                        } else
                            event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                    } else
                        event.getTextChannel().sendMessage("Pas de personne mentionnée.").queue();
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
