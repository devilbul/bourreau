package fr.warframe.devilbul.command.supreme;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;

public class ListProvisoirCommand extends SimpleCommand {

    @Command(name = "listsupreme")
    @Help(field = "**syntaxe** :    !listsupreme\n**effet :**         affiche la liste admin provisoir", categorie = Categorie.Supreme)
    public static void listToProvisoir(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                String adminProvisoir = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "adminProvisoir.json")));
                JSONArray adminProvisoirJson = new JSONArray(adminProvisoir);
                StringBuilder message = new StringBuilder();

                for (int i = 0; i < adminProvisoirJson.length(); i++)
                    message.append("**").append(i).append("** : ").append(adminProvisoirJson.getJSONObject(i).getString("name")).append(" (").append(adminProvisoirJson.getJSONObject(i).getString("id")).append(")\n");

                event.getTextChannel().sendMessage(message.toString()).queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
