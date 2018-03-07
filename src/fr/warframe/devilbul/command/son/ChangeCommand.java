package fr.warframe.devilbul.command.son;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.thread.ThreadSon.*;

public class ChangeCommand extends SimpleCommand {

    @SubCommand(name="change", commande = "son")
    @Help(field = "**Coming son**", categorie = Categorie.Supreme)
    public static void changeStateThread(MessageReceivedEvent event) {
        try {
            if (canPlay()) {
                stopPlay();
                event.getTextChannel().sendMessage("Commandes son désactivées !").queue();
            }
            else {
                startPlay();
                event.getTextChannel().sendMessage("Commandes son activées !").queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
