package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.Invasions.invasion;
import static fr.warframe.devilbul.api.warframe.Invasions.invasionWithInterest;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class InvasionsCommand extends SimpleCommand {

    @Command(name = "invasions")
    @Help(field = "**syntaxe 1** :    !invasions\n**effet :**         affiche toutes les invasions en cours" +
            "\n\n**syntaxe 2** :    !invasions interest\n**effet :**         affiche toutes les invasions en cours avec des récompenses intéressante", categorie = Categorie.Warframe)
    public static void invasions(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) invasionWithInterest(event);
            else invasion(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
