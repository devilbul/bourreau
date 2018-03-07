package fr.warframe.devilbul.command.son;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

import static fr.warframe.devilbul.Init.queueSon;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.thread.ThreadSon.setPlayed;

public class StopCommand extends SimpleCommand {

    @SubCommand(name = "stop", commande = "son")
    @Help(field = "**Coming son**", categorie = Categorie.Son)
    public static void stop(MessageReceivedEvent event) {
        try {
            queueSon = new ArrayList<>();
            setPlayed(false);
            event.getChannel().sendMessage("Stop.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
