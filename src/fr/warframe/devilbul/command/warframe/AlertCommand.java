package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.Alert.alert;
import static fr.warframe.devilbul.api.warframe.Alert.alertWithInterest;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class AlertCommand extends SimpleCommand {

    @Command(name = "alerts")
    @Help(field = "**syntaxe 1** :    !lalerts\n**effet :**         affiche toutes les alertes en cours" +
            "\n\n**syntaxe 2** :    !alerts interest\n**effet :**         affiche toutes les alertes en cours avec des récompenses intéressante", categorie = Categorie.Warframe)
    public static void alerts(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) alertWithInterest(event);
            else alert(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
