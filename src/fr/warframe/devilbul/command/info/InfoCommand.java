package fr.warframe.devilbul.command.info;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class InfoCommand extends SimpleCommand {

    @Command(name = "info")
    @Help(field = "**syntaxe** :      !info\n**effet :**         donne un lien vers site, qui affiche\n                    Alerts\n" +
            "                    News\n                    Invasions\n                    Darvo Daily Deals\n                    Void Traders\n" +
            "                    Sorties(WIP)\n                    Void Fissures(WIP)\n                    Market Items\n                    Earth Day/Night Cycle", categorie = Categorie.Information)
    public static void info(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://deathsnacks.com/wf").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
