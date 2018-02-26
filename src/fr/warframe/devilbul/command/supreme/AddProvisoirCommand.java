package fr.warframe.devilbul.command.supreme;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;

public class AddProvisoirCommand extends SimpleCommand {

    @Command(name = "addsupreme")
    @Help(field = "**syntaxe** :    !supreme\n**effet :**         ajouter à la liste admin provisoir", categorie = Categorie.Supreme)
    public static void addToProvisoir(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                String adminProvisoir = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "adminProvisoir.json")));
                JSONArray adminProvisoirJson = new JSONArray(adminProvisoir);

                if (event.getMessage().toString().contains("@")) {
                    FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "adminProvisoir.json");

                    adminProvisoirJson.put(new JSONObject().put("id", event.getMessage().getMentionedUsers().get(0).getId()).put("name", event.getMessage().getMentionedUsers().get(0).getName()));
                    file.write(adminProvisoirJson.toString(3));
                    file.flush();
                    file.close();

                    event.getTextChannel().sendMessage("Ajouté.").queue();
                } else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
