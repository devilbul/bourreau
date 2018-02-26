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
import static fr.warframe.devilbul.utils.Recup.recupString;

public class UnBanCommand extends SimpleCommand {

    @Command(name = "unban")
    @Help(field = "**syntaxe** :      !unban @<pseudo>\n**condition :**   l'utilisateur <pseudo> doit être banni\n" +
            "                        \n**effet :**            deban du serveur l'utilisateur", categorie = Categorie.Admin)
    public static void unBan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                    String commande = event.getMessage().getContentDisplay().toLowerCase();

                    System.out.println(commande);
                    System.out.println(event.getMessage().getContentDisplay().toLowerCase());

                    String id = recupString(event.getMessage().getContentDisplay().toLowerCase());

                    System.out.println(id);

                    if (commande.contains(" ")) {
                        event.getGuild().getController().unban(id).submit();
                        event.getTextChannel().sendMessage("client débanni").queue();
                    } else
                        event.getTextChannel().sendMessage("Ce client n'est pas banni ou n'existe pas.").queue();
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
