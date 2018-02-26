package fr.warframe.devilbul.command.info;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class ProgresCommand extends SimpleCommand {

    @Command(name = "progres")
    @Help(field = "**syntaxe** :      !progres\n**effet :**         donne un lien vers le tableau Trello, montrant ma progression", categorie = Categorie.Information)
    public static void progression(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Voici ma progression : \nhttps://trello.com/b/JEEkreCv").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
