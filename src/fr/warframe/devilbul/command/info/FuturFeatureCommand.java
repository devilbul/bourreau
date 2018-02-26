package fr.warframe.devilbul.command.info;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class FuturFeatureCommand extends SimpleCommand {

    @Command(name = "up")
    @Help(field = "**syntaxe** :      !up\n**effet :**         donne un lien vers le wiki des futurs ajouts", categorie = Categorie.Information)
    public static void upcoming(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://warframe.wikia.com/wiki/Upcoming_Features").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
