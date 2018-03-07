package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Recup.recupID;

public class KickCommand extends SimpleCommand {

    @Command(name = "kick")
    @Help(field = "**syntaxe** :      !kick @<pseudo>\n**effet :**         expulse du serveur l'utilisateur", categorie = Categorie.Admin)
    public static void kick(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.KICK_MEMBERS)) {
                    if (event.getMessage().toString().contains("@")) {
                        if (event.getGuild().getSelfMember().canInteract(event.getMessage().getMentionedMembers().get(0))) {
                            if (findAdminSupreme(event.getMessage().getMentionedUsers().get(0).getId())) {
                                event.getTextChannel().sendMessage("Impossible !").queue();
                                return;
                            }

                            if (!event.getMessage().getMentionedMembers().get(0).isOwner()) {
                                event.getGuild().getController().kick(recupID(event.getMessage().getMentionedUsers().toString())).submit();
                                event.getTextChannel().sendMessage("client kické").queue();
                            } else
                                event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                        } else
                            event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getAsMention() + " a autant ou plus de droit que le bot.").queue();
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
