package fr.warframe.devilbul.command.son;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import fr.warframe.devilbul.utils.music.WaitingSound;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.Init.queueSon;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class ListCommand extends SimpleCommand {

    @SubCommand(name = "list", commande = "son")
    @Help(field = "**Coming son**", categorie = Categorie.Son)
    public static void list(MessageReceivedEvent event) {
        try {
            MessageBuilder queue = new MessageBuilder();

            queue.append("Playlist :");

            if (queueSon.size() > 0)
                for (WaitingSound aQueueSon : queueSon)
                    queue.append("\n   • ").append(aQueueSon.getCommandeSon());
            else
                queue.append("\n   pas de son en attente.");

            event.getTextChannel().sendMessage(queue.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
