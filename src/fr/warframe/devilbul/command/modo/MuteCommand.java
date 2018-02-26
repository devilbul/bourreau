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

public class MuteCommand extends SimpleCommand {

    @Command(name = "mute")
    @Help(field = "**syntaxe** :      !mute @<pseudo>\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal\n" +
            "                        \n**effet :**            retire le droit de parler à l'utilisateur", categorie = Categorie.Modo)
    public static void mute(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.VOICE_MUTE_OTHERS)) {
                    String id = recupID(event.getMessage().getMentionedUsers().toString());

                    if (event.getMessage().toString().contains("@")) {
                        if (!event.getGuild().getMemberById(id).isOwner()) {
                            if (findUserVC(event) != null) {
                                event.getGuild().getController().setMute(event.getGuild().getMemberById(id), true).submit();
                                event.getTextChannel().sendMessage("client muté").queue();
                            } else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                                event.getTextChannel().sendMessage("Impossible !").queue();
                            } else
                                event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été muté.").queue();
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
