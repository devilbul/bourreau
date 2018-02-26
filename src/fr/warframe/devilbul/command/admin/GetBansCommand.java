package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;

public class GetBansCommand extends SimpleCommand {

    @Command(name = "getbans")
    @Help(field = "**syntaxe** :      !getbans\n**effet :**   affiche les utilisateur banni\n", categorie = Categorie.Admin)
    public static void getBans(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.BAN_MEMBERS)) {
                    if (event.getGuild().getBanList().complete().size() > 0) {
                        StringBuilder bannis = new StringBuilder();

                        bannis.append("Liste des joueurs bannis :\n");

                        for (Guild.Ban ban : event.getGuild().getBanList().complete()) {
                            bannis.append(ban.getUser().getName()).append(" (").append(ban.getUser().getId());

                            if (ban.getReason() != null && !ban.getReason().isEmpty())
                                bannis.append(", ").append(ban.getReason()).append(")\n");
                            else
                                bannis.append(")\n");
                        }

                        bannis.append("\n!unban <id> pour deban");

                        event.getTextChannel().sendMessage(bannis.toString()).queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Aucun utilisateur banni").queue();
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
