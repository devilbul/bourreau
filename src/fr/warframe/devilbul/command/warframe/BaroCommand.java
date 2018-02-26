package fr.warframe.devilbul.command.warframe;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.api.warframe.Baro.baro;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class BaroCommand extends SimpleCommand {

    @Command(name = "baro")
    @Help(field = "**syntaxe** :      !baro\n**effet :**         affiche le temps restant avant l'arrivé de Baro Ki'Teer, en plus du relais sur lequel il sera", categorie = Categorie.Warframe)
    public static void voidTraders(MessageReceivedEvent event) {
        try {
            baro(event);
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
