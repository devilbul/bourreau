package fr.warframe.devilbul.api.warframe;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
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
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class VoidFissure {

    public static void voidFissure(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar), warframeJson = new JSONObject(jsonText), missionJson = new JSONObject(mission);
            JSONArray fissureJson = warframeJson.getJSONArray("ActiveMissions");
            EmbedBuilder voidFissure = new EmbedBuilder();
            long expire, time = Instant.now().toEpochMilli();
            String heure, minute, seconde, fin;
            String commande = event.getMessage().getContentDisplay().toLowerCase();
            String[] node;

            if (commande.contains(" ")) {
                String tiers = recupString(commande);
                voidFissure.setTitle("Fissure du Néant : " + tiers, "http://warframe.wikia.com/wiki/Void_Fissure");

                for (int i = 0; i < fissureJson.length(); i++) {
                    if (missionJson.getJSONObject("VoidFissure").getJSONObject(fissureJson.getJSONObject(i).getString("Modifier")).getString("value").toLowerCase().equals(tiers)) {
                        fin = "";
                        expire = Long.parseLong(fissureJson.getJSONObject(i).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
                        heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                        minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                        seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));

                        if (heure.equals("00")) fin += "";
                        else if (heure.charAt(0) == '0') fin += heure.substring(1) + "h ";
                        else fin += heure + "h ";
                        if (minute.equals("00") && heure.equals("00")) fin += "";
                        else if (minute.charAt(0) == '0') fin += minute.substring(1) + "m ";
                        else fin += minute + "m ";
                        if (seconde.charAt(0) == '0') fin += seconde.substring(1) + "s";
                        else fin += seconde + "s";

                        node = starCharJson.getJSONObject(fissureJson.getJSONObject(i).getString("Node")).getString("name").split(" ");

                        if (node[1].contains("PH")) node[1] = "Earth]";
                        voidFissure.addField(missionJson.getJSONObject("VoidFissure").getJSONObject(fissureJson.getJSONObject(i).getString("Modifier")).getString("value") + " : " +
                                        node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(fissureJson.getJSONObject(i).getString("Node")).getString("mission_type")).getString("value"),
                                "expire dans : " + fin, false);
                    }
                }
            } else {
                voidFissure.setTitle("Fissure du Néant", "http://warframe.wikia.com/wiki/Void_Fissure");

                for (int i = 0; i < fissureJson.length(); i++) {
                    fin = "";
                    expire = Long.parseLong(fissureJson.getJSONObject(i).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
                    heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                    minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                    seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));

                    if (heure.equals("00")) fin += "";
                    else if (heure.charAt(0) == '0') fin += heure.substring(1) + "h ";
                    else fin += heure + "h ";
                    if (minute.equals("00") && heure.equals("00")) fin += "";
                    else if (minute.charAt(0) == '0') fin += minute.substring(1) + "m ";
                    else fin += minute + "m ";
                    if (seconde.charAt(0) == '0') fin += seconde.substring(1) + "s";
                    else fin += seconde + "s";

                    node = starCharJson.getJSONObject(fissureJson.getJSONObject(i).getString("Node")).getString("name").replace("[", "$").split("[$]");

                    if (node[1].contains("PH")) node[1] = "Earth]";
                    voidFissure.addField(missionJson.getJSONObject("VoidFissure").getJSONObject(fissureJson.getJSONObject(i).getString("Modifier")).getString("value") + " : " +
                                    node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(fissureJson.getJSONObject(i).getString("Node")).getString("mission_type")).getString("value"),
                            "expire dans : " + fin, false);
                }

                voidFissure.addField("pour voir les fissures d'un seul tiers :", "tapez la commande : **__!void <tiers>__**\n   <tiers> parmi : axi, neo, meso, lith", false);
            }

            voidFissure.setDescription("missions où une fissure du néant est apparue");
            voidFissure.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/9/9c/LuminousIconLarge.png");
            voidFissure.setColor(new Color(240, 180, 20));
            voidFissure.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(voidFissure.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
