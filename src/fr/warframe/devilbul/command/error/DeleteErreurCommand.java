package fr.warframe.devilbul.command.error;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class DeleteErreurCommand extends SimpleCommand {

    @SubCommand(name = "delete", commande = "erreur")
    public static void deleteErreur(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String erreurID = "erreur " + recupString(commande);
                String erreur = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "erreur" + File.separator + "Erreur.json")));
                JSONObject erreurJson = new JSONObject(erreur);

                if (findJsonKey(erreurJson, erreurID)) {
                    String adresseErreur = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "erreur" + File.separator + "Erreur.json";
                    FileWriter file = new FileWriter(adresseErreur);

                    erreurJson.remove(erreurID);

                    file.write(erreurJson.toString(3));
                    file.flush();
                    file.close();

                    event.getTextChannel().sendMessage("Erreur " + recupString(commande) + " a été supprimé.").queue();
                } else
                    event.getTextChannel().sendMessage("ID erreur incorrect.").queue();
            } else
                event.getTextChannel().sendMessage("Aucune erreur saisie.").queue();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
