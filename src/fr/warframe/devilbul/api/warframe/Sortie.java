package fr.warframe.devilbul.api.warframe;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Sortie {

    public static void sortie(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String sortieInfo = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Sortie.json")));
            String mission = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar), sortieInfoJson = new JSONObject(sortieInfo), warframeJson = new JSONObject(jsonText), missionJson = new JSONObject(mission);
            JSONObject sortieJson = warframeJson.getJSONArray("Sorties").getJSONObject(0);
            EmbedBuilder sortie = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long expire = Long.parseLong(sortieJson.getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
            String jour = new SimpleDateFormat("dd").format(new Date(expire - time - 3600000));
            String heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
            String minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
            String seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));
            String fin = "";
            String[] node;

            if (!jour.equals("01")) {
                if (jour.charAt(0) == '0') fin += jour.substring(1) + "d ";
                else fin += jour + "d ";
            }
            if (heure.equals("00")) fin += "";
            else if (heure.charAt(0) == '0') fin += heure.substring(1) + "h ";
            else fin += heure + "h ";
            if (minute.equals("00") && heure.equals("00")) fin += "";
            else if (minute.charAt(0) == '0') fin += minute.substring(1) + "m ";
            else fin += minute + "m ";
            if (seconde.charAt(0) == '0') fin += seconde.substring(1) + "s";
            else fin += seconde + "s";

            for (int i = 0; i < sortieJson.getJSONArray("Variants").length(); i++) {
                node = starCharJson.getJSONObject(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("node")).getString("name").replace("[", "$").split("[$]");

                if (node[1].contains("PH")) node[1] = "Earth]";
                sortie.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("missionType")).getString("value"),
                        sortieInfoJson.getJSONObject("modifierTypes").getString(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("modifierType")), false);
            }

            sortie.setTitle("Sortie vs : " + sortieInfoJson.getJSONObject("bosses").getJSONObject(sortieJson.getString("Boss")).getString("name"), "https://www.warframe.wikia.com/wiki/Sortie");
            sortie.setThumbnail(sortieInfoJson.getJSONObject("bosses").getJSONObject(sortieJson.getString("Boss")).getString("image"));
            sortie.setDescription("expire dans : " + fin);
            sortie.setColor(new Color(250, 50, 50));
            sortie.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(sortie.build()).complete();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
