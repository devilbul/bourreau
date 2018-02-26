package fr.warframe.devilbul.command.supreme;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class DeleteProvisoirCommand extends SimpleCommand {

    @Command(name = "deletesupreme")
    @Help(field = "**syntaxe** :    !supreme\n**effet :**         ajouter à la liste admin provisoir", categorie = Categorie.Supreme)
    public static void deleteToProvisoir(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                String adminProvisoir = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "adminProvisoir.json")));
                JSONArray adminProvisoirJson = new JSONArray(adminProvisoir);

                if (event.getMessage().getContentDisplay().contains(" ")) {
                    int index = -1;

                    try {
                        index = Integer.valueOf(recupString(event.getMessage().getContentDisplay()));
                    } catch (Exception e) {
                        event.getTextChannel().sendMessage("Invalide").queue();
                        return;
                    }

                    if (index > -1) {
                        if (index < adminProvisoirJson.length()) {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "adminProvisoir.json");

                            adminProvisoirJson.remove(index);

                            file.write(adminProvisoirJson.toString(3));
                            file.flush();
                            file.close();

                            event.getTextChannel().sendMessage("Supprimé.").queue();
                        } else
                            event.getTextChannel().sendMessage("index incorrect").queue();
                    }
                }

            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
