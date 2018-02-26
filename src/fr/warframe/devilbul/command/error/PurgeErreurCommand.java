package fr.warframe.devilbul.command.error;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PurgeErreurCommand extends SimpleCommand {

    @SubCommand(name = "purge", commande = "erreur")
    public static void purgeErreur(MessageReceivedEvent event) {
        try {
            JSONObject erreurJson = new JSONObject();
            String adresseErreur = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "erreur" + File.separator + "Erreur.json";
            FileWriter file = new FileWriter(adresseErreur);

            file.write(erreurJson.toString(3));
            file.flush();
            file.close();

            event.getTextChannel().sendMessage("Toutes les erreurs ont été supprimées.").queue();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
