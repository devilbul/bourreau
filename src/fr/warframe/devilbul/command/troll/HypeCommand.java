package fr.warframe.devilbul.command.troll;

import fr.warframe.devilbul.Bourreau;
import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class HypeCommand extends SimpleCommand {

    @Command(name = "hype")
    @Help(field = "Test pour voir ce que ça fait !", categorie = Categorie.Troll)
    public static void hype(MessageReceivedEvent event) {
        try {
            MessageBuilder hype = new MessageBuilder();

            switch (new Random().nextInt(10)) {
                case 3:
                case 6:
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getMemberById("166951224019517440").getAsMention());
                    hype.append("\n");
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417790661710839809"));
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417790661710839809"));
                case 0:
                case 1:
                case 2:
                case 4:
                case 5:
                case 7:
                case 8:
                case 9:
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417790661710839809"));
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417790661710839809"));
                    hype.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417790661710839809"));
                    break;
            }

            event.getTextChannel().sendMessage(hype.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
