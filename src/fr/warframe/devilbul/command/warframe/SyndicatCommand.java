package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.Syndicat.syndicat;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class SyndicatCommand extends SimpleCommand {

    @Command(name = "syndicat")
    @Help(field = "**syntaxe 1** :   !syndicat\n**effet :**         affiche les missions syndicales actuelles\n\n" +
            "**syntaxe 2** :   !syndicat <syndicat>\n**parmi :**       SteelMeridian\n                    RedVeil\n" +
            "                    Perrin\n                    CephalonSuda\n                    NewLoka\n                    Arbiters", categorie = Categorie.Warframe)
    public static void syndicats(MessageReceivedEvent event) {
        try {
            syndicat(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
