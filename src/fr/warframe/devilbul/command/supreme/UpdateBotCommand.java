package fr.warframe.devilbul.command.supreme;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;

public class UpdateBotCommand extends SimpleCommand {

    @Command(name = "updatebot")
    @Help(field = "**syntaxe** :   !updatebot\n**effet :**           met à jour le bot", categorie = Categorie.Supreme)
    public static void reboot(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                if (System.getProperty("os.name").equals("Linux")) {
                    event.getTextChannel().sendMessage("mise à jour en cours.").queue();
                    Runtime.getRuntime().exec("/bin/bash /home/discordbot/bourreau-update.sh");
                }
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
