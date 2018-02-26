package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.VoidFissure.voidFissure;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class VoidCommand extends SimpleCommand {

    @Command(name="void")
    @Help(field = "**syntaxe 1** :    !void\n**effet :**         affiche toutes les missions fissure en cours" +
            "\n\n**syntaxe 2** :    !void <tiers>\n**effet :**         affiche toutes les missions fissures du tiers <tiers> en cours", categorie = Categorie.Warframe)
    public static void voiD(MessageReceivedEvent event) {
        try {
            voidFissure(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
