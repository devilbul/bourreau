package fr.warframe.devilbul.exception;

import fr.warframe.devilbul.Bourreau;
import net.dv8tion.jda.core.EmbedBuilder;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.utils.time.DateHeure.*;

public class ErreurGestion {

    public static void afficheErreur(String messageErreur, Exception e) {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Rapport d'erreur :", null);
        message.setDescription("commande : " + messageErreur);
        message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
        message.setColor(new Color(178, 10, 22));
        message.addField("erreur :", "" + e.getMessage(), false);
        message.addField("cause :", "" + String.valueOf(e.getCause()), false);
        message.addField("localize :", "" + e.getLocalizedMessage(), false);

        Bourreau.jda.getGuildById("298503533777387530").getTextChannelById("392019799397367818").sendMessage(message.build()).queue();
    }

    public static void saveErreur(String contextName, String authorName, String authorId, String message, Exception e) {
        try {
            String erreur = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "erreur" + File.separator + "Erreur.json")));
            String idErreur = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "erreur" + File.separator + "IdErreur.json")));
            String adresseErreur = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "erreur" + File.separator + "Erreur.json";
            String adresseIdErreur = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "erreur" + File.separator + "IdErreur.json";
            StringBuilder StackTrace = new StringBuilder();
            FileWriter file = new FileWriter(adresseErreur);
            FileWriter fileId = new FileWriter(adresseIdErreur);
            JSONObject erreurJson = new JSONObject(erreur);
            JSONObject idErreurJson = new JSONObject(idErreur);
            JSONObject newErreurJson = new JSONObject();
            JSONObject newIdErreurJson = new JSONObject();
            int ID = idErreurJson.getInt("ID") + 1;

            newIdErreurJson.put("ID", ID);

            for (int i = 0; i < e.getStackTrace().length; i++)
                StackTrace.append(e.getStackTrace()[i]).append("\n");

            newErreurJson.put("date", giveDate());
            newErreurJson.put("heure", giveHeure());
            newErreurJson.put("text channel", contextName);
            newErreurJson.put("auteur commande (name)", authorName);
            newErreurJson.put("auteur commande (id)", authorId);
            newErreurJson.put("commande", message);
            newErreurJson.put("message erreur", e.getMessage());
            newErreurJson.put("cause", e.getCause());
            newErreurJson.put("localize", e.getLocalizedMessage());
            newErreurJson.put("stack trace", StackTrace.toString());
            erreurJson.put("erreur " + String.valueOf(ID), newErreurJson);

            file.write(erreurJson.toString(3));
            fileId.write(newIdErreurJson.toString(3));
            file.flush();
            fileId.flush();
            file.close();
            fileId.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
