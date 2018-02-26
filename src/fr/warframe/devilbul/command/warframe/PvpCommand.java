package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.Pvp.pvpChallenge;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class PvpCommand extends SimpleCommand {

    @Command(name = "pvp")
    @Help(field = "**syntaxe** :      !pvp\n**effet :**         affiche les défis conclave quotidien", categorie = Categorie.Warframe)
    public static void pvpChallenges(MessageReceivedEvent event) {
        try {
            pvpChallenge(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
