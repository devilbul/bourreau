package fr.warframe.devilbul.command.info;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class IdeaCommand extends SimpleCommand {

    @Command(name = "idee", subCommand = false)
    @Help(field = "**syntaxe** :      !idee\n**effet :**         donne un lien vers un google doc, pour proposer une idée de commande", categorie = Categorie.Information)
    public static void idee(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://docs.google.com/document/d/1kb-sIRzCQlau5JL2q2WZRFlUy94JKWLF8p4xvfRXJFU/edit?usp=sharing").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
