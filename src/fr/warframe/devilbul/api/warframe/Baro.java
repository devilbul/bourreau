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

public class Baro {

    public static void baro(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), starCharJson = new JSONObject(starChar);
            JSONObject baroJson = warframeJson.getJSONArray("VoidTraders").getJSONObject(0);
            EmbedBuilder baro = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long active = Long.parseLong(baroJson.getJSONObject("Activation").getJSONObject("$date").getString("$numberLong"));
            long expire = Long.parseLong(baroJson.getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
            String jour, heure, minute, seconde, debut = "", fin = "";
            String[] node;

            node = starCharJson.getJSONObject(baroJson.getString("Node")).getString("name").split(" ");

            if (Long.valueOf(baroJson.getJSONObject("Activation").getJSONObject("$date").getString("$numberLong")) - time > 0) {
                jour = new SimpleDateFormat("dd").format(new Date(active - time - 3600000));
                heure = new SimpleDateFormat("HH").format(new Date(active - time - 3600000));
                minute = new SimpleDateFormat("mm").format(new Date(active - time - 3600000));
                seconde = new SimpleDateFormat("ss").format(new Date(active - time - 3600000));

                if (!jour.equals("01")) {
                    jour = String.valueOf(Long.valueOf(jour) - 1);
                    if (jour.charAt(0) == '0') debut += jour.substring(1) + "d ";
                    else debut += jour + "d ";
                }
                if (heure.charAt(0) == '0') debut += heure.substring(1) + "h ";
                else debut += heure + "h ";
                if (minute.charAt(0) == '0') debut += minute.substring(1) + "m ";
                else debut += minute + "m ";
                if (seconde.charAt(0) == '0') debut += seconde.substring(1) + "s";
                else debut += seconde + "s";

                baro.setDescription("Void Trader");
                baro.addField("Le marchand arrive dans " + debut, "sur le relai de " + baroJson.getString("Node").split("H")[0] + " : " + node[0] + " " + node[1], false);
            } else if (Long.valueOf(baroJson.getJSONObject("Activation").getJSONObject("$date").getString("$numberLong")) - time < 1) {
                jour = new SimpleDateFormat("dd").format(new Date(expire - time - 3600000));
                heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));

                if (!jour.equals("01")) {
                    jour = String.valueOf(Long.valueOf(jour) - 1);
                    if (jour.charAt(0) == '0') fin += jour.substring(1) + "d ";
                    else fin += jour + "d ";
                }
                if (heure.charAt(0) == '0') fin += heure.substring(1) + "h ";
                else fin += heure + "h ";
                if (minute.charAt(0) == '0') fin += minute.substring(1) + "m ";
                else fin += minute + "m ";
                if (seconde.charAt(0) == '0') fin += seconde.substring(1) + "s";
                else fin += seconde + "s";

                baro.setDescription("actuellemeent sur : " + node[0] + " " + node[1]);

                for (int i = 0; i < baroJson.getJSONArray("Manifest").length(); i++)
                    baro.addField(languageJson.getJSONObject(baroJson.getJSONArray("Manifest").getJSONObject(i).getString("ItemType").toLowerCase()).getString("value"),
                            baroJson.getJSONArray("Manifest").getJSONObject(i).getInt("RegularPrice") + " cr - " + baroJson.getJSONArray("Manifest").getJSONObject(i).getInt("PrimePrice") + " ducats", false);

                baro.addField("Temps avant son départ du relai : " + node[0] + " " + node[1], fin, false);
            }

            baro.setTitle("Baro Ki'Teer", "https://www.warframe.wikia.com/wiki/Sortie");
            baro.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/d/d8/MarchandDuN%C3%A9ant.png/revision/latest?cb=20150630171447&path-prefix=fr");
            baro.setColor(new Color(36, 153, 153));
            baro.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(baro.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
