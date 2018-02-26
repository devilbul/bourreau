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
import static fr.warframe.devilbul.utils.Recup.recupID;

public class BanCommand extends SimpleCommand {

    @Command(name = "ban")
    @Help(field = "**syntaxe** :      !ban @<pseudo>\n**effet :**         ban du serveur l'utilisateur", categorie = Categorie.Admin)
    public static void ban(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                    if (event.getGuild().getSelfMember().canInteract(event.getMessage().getMentionedMembers().get(0))) {
                        int dayBan = 7;

                        if (event.getMessage().toString().contains("@")) {
                            if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser()))
                                event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                            else {
                                event.getGuild().getController().ban(recupID(event.getMessage().getMentionedUsers().toString()), dayBan).submit();
                                event.getTextChannel().sendMessage("client banni").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
                    } else
                        event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getAsMention() + " a autant ou plus de droit que le bot.").queue();
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
