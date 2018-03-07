package fr.warframe.devilbul.command.son;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.thread.ThreadSon.setPlayed;

public class SkipCommand extends SimpleCommand {

    @SubCommand(name="skip", commande = "son")
    @Help(field = "**Coming son**", categorie = Categorie.Son)
    public static void skip(MessageReceivedEvent event) {
        try {
            setPlayed(false);
            event.getChannel().sendMessage("Skipped the current song.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
