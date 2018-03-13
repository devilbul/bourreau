package fr.warframe.devilbul.command.info;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class SiteCommand extends SimpleCommand {

    @Command(name = "site")
    @Help(field = "**syntaxe** :      !site\n**effet :**         donne le lien du site de l'alliance", categorie = Categorie.Information)
    public static void site(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://wfraid.teamfr.net/").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
