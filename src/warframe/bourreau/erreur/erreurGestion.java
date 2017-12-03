package warframe.bourreau.erreur;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.util.DateHeure.*;

public class erreurGestion {

    public static void afficheErreur(MessageReceivedEvent event, Exception e) {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Rapport d'erreur :", null);
        message.setDescription("commande : " + event.getMessage().getContent());
        message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
        message.setColor(new Color(178, 10, 22));
        message.addField("erreur :", e.getMessage(), false);
        message.addField("cause :", String.valueOf(e.getCause()), false);
        message.addField("localize :", e.getLocalizedMessage(), false);

        event.getGuild().getTextChannelsByName("admin_perso", true).get(0).sendMessage(message.build()).queue();
        event.getTextChannel().sendMessage("Une erreur est survenue.").queue();
    }

    public static void saveErreur(MessageReceivedEvent event, Exception e) {
        try {
            String erreur = new String(Files.readAllBytes(Paths.get("res" + File.separator + "erreur" + File.separator + "Erreur.json")));
            String idErreur = new String(Files.readAllBytes(Paths.get("res" + File.separator + "erreur" + File.separator + "IdErreur.json")));
            String adresseErreur = System.getProperty("user.dir") + File.separator + "res" + File.separator + "erreur" + File.separator + "Erreur.json";
            String adresseIdErreur = System.getProperty("user.dir") + File.separator + "res" + File.separator + "erreur" + File.separator + "IdErreur.json";
            String StackTrace = "";
            FileWriter file = new FileWriter(adresseErreur);
            FileWriter fileId = new FileWriter(adresseIdErreur);
            JSONObject erreurJson = new JSONObject(erreur);
            JSONObject idErreurJson = new JSONObject(idErreur);
            JSONObject newErreurJson = new JSONObject();
            JSONObject newIdErreurJson = new JSONObject();
            int ID = idErreurJson.getInt("ID") + 1;

            newIdErreurJson.put("ID", ID);

            for (int i=0; i<e.getStackTrace().length; i++)
                StackTrace += e.getStackTrace()[i] + "\n";

            newErreurJson.put("date", GiveDate());
            newErreurJson.put("heure", GiveHeure());
            newErreurJson.put("text channel", event.getMessage().getTextChannel().getName());
            newErreurJson.put("auteur commande (name)", event.getAuthor().getName());
            newErreurJson.put("auteur commande (id)", event.getAuthor().getId());
            newErreurJson.put("commande", event.getMessage().getContent());
            newErreurJson.put("message erreur", e.getMessage());
            newErreurJson.put("cause", e.getCause());
            newErreurJson.put("localize", e.getLocalizedMessage());
            newErreurJson.put("stack trace", StackTrace);

            erreurJson.put("erreur " + String.valueOf(ID), newErreurJson);

            file.write(erreurJson.toString());
            fileId.write(newIdErreurJson.toString());
            file.flush();
            fileId.flush();
            file.close();
            fileId.close();

            event.getGuild().getTextChannelsByName("admin_perso", true).get(0).sendMessage("rapport ajouté à la bdd").queue();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
