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
import static fr.warframe.devilbul.message.event.NoThing.messageNoThing;
import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Pvp {

    public static void pvpChallenge(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd), commande = event.getMessage().getContentDisplay().toLowerCase();
            String pvpInfo = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "DefiConclave.json")));
            JSONObject warframeJson = new JSONObject(jsonText), pvpInfoJson = new JSONObject(pvpInfo);
            JSONArray pvpJson = warframeJson.getJSONArray("PVPChallengeInstances");
            EmbedBuilder pvp = new EmbedBuilder();
            long expire, time = Instant.now().toEpochMilli();
            int key = -1;
            String jour, heure, minute, seconde, fin = "", defi = null;

            if (commande.contains(" ")) {
                String action = recupString(commande);

                if (action.equals("hebdo")) {
                    defi = "";

                    for (int i = 0; i < pvpJson.length(); i++) {
                        if (pvpJson.getJSONObject(i).getString("challengeTypeRefID").contains("PVPTimedChallengeOtherChallengeCompleteANY"))
                            key = i;
                    }

                    expire = Long.parseLong(pvpJson.getJSONObject(key).getJSONObject("endDate").getJSONObject("$date").getString("$numberLong"));
                    jour = new SimpleDateFormat("dd").format(new Date(expire - time - 3600000));
                    heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                    minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                    seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));

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

                    pvp.setDescription("Défi conclave hebdomadaire");
                    pvp.addField("les défis se réinitialisent dans :", fin, true);
                } else
                    messageNoThing(event);
            } else {
                defi = "défi quotidien";

                for (int i = 0; i < pvpJson.length(); i++) {
                    if (pvpJson.getJSONObject(i).getString("Category").contains("PVPChallengeTypeCategory_DAILY"))
                        key = i;
                }

                expire = Long.parseLong(pvpJson.getJSONObject(key).getJSONObject("endDate").getJSONObject("$date").getString("$numberLong"));
                jour = new SimpleDateFormat("dd").format(new Date(expire - time - 3600000));
                heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));

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

                pvp.setDescription("Défi conclave quotidien, expire dans : " + fin);

                for (int i = 0; i < pvpJson.length(); i++) {
                    if (pvpJson.getJSONObject(i).getString("Category").contains("TypeCategory_DAILY")) {
                        if (findJsonKey(pvpInfoJson.getJSONObject("defi"), pvpJson.getJSONObject(i).getString("challengeTypeRefID")))
                            pvp.addField(pvpInfoJson.getJSONObject("mode").getString(pvpJson.getJSONObject(i).getString("PVPMode")), pvpInfoJson.getJSONObject("defi").getString(pvpJson.getJSONObject(i).getString("challengeTypeRefID")), false);
                        else
                            pvp.addField(pvpInfoJson.getJSONObject("mode").getString(pvpJson.getJSONObject(i).getString("PVPMode")), "défi à ajouter : " + pvpJson.getJSONObject(i).getString("challengeTypeRefID"), false);
                    }
                }
            }

            pvp.setTitle("Conclave : " + defi, "http://warframe.wikia.com/wiki/Conclave");
            pvp.setThumbnail("https://vignette2.wikia.nocookie.net/warframe/images/7/7f/Teshin.png/revision/latest?cb=20150818043033");
            pvp.setColor(new Color(10, 10, 10));
            pvp.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(pvp.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
