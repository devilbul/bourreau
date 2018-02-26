package fr.warframe.devilbul.command.error;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ListErreurCommand extends SimpleCommand {

    @SubCommand(name="list",commande="erreur")
    public static void listErreur(MessageReceivedEvent event) {
        try {
            String erreur = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "erreur" + File.separator + "Erreur.json")));
            JSONObject erreurJson = new JSONObject(erreur);
            EmbedBuilder message = new EmbedBuilder();
            String erreurName;

            message.setTitle("Liste erreur :", null);
            message.setDescription("erreurs  survenues");
            message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
            message.setColor(new Color(178, 10, 22));

            if (erreurJson.length() == 0)
                event.getTextChannel().sendMessage("Aucune erreur enregistrée").queue();
            else {
                for (int i = 0; i < erreurJson.names().length(); i++) {
                    erreurName = erreurJson.names().getString(i);

                    message.addField(erreurName, erreurJson.getJSONObject(erreurName).getString("date") + " " + erreurJson.getJSONObject(erreurName).getString("heure") + "\n" +
                            erreurJson.getJSONObject(erreurName).getString("commande") + "\n", false);
                }

                event.getTextChannel().sendMessage(message.build()).queue();
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
