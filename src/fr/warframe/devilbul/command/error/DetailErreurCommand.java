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

import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class DetailErreurCommand extends SimpleCommand {

    @SubCommand(name = "detail", commande = "erreur")
    public static void detailErreur(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String erreurID = "erreur " + recupString(commande);
                String erreur = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "erreur" + File.separator + "Erreur.json")));
                JSONObject erreurJson = new JSONObject(erreur);

                if (findJsonKey(erreurJson, erreurID)) {
                    EmbedBuilder message = new EmbedBuilder();

                    message.setTitle("Detail erreur :", null);
                    message.setDescription("commande : " + erreurJson.getJSONObject(erreurID).getString("commande"));
                    message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
                    message.setColor(new Color(178, 10, 22));

                    message.addField("date :", erreurJson.getJSONObject(erreurID).getString("date") + " " + erreurJson.getJSONObject(erreurID).getString("heure"), false);
                    message.addField("auteur", erreurJson.getJSONObject(erreurID).getString("auteur commande (name)"), false);
                    message.addField("text channel", erreurJson.getJSONObject(erreurID).getString("text channel"), false);

                    if (findValueObjectList(erreurJson.getJSONObject(erreurID).names().toList(), "message erreur"))
                        message.addField("message erreur", erreurJson.getJSONObject(erreurID).getString("message erreur"), false);

                    event.getTextChannel().sendMessage(message.build()).queue();
                } else
                    event.getTextChannel().sendMessage("ID erreur incorrect.").queue();
            } else
                event.getTextChannel().sendMessage("Aucune erreur saisie.").queue();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
