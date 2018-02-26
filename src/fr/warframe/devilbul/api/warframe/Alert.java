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
import static fr.warframe.devilbul.utils.Find.findNamesJSONObkect;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Alert {

    public static void alert(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), starCharJson = new JSONObject(starChar), missionJson = new JSONObject(mission);
            JSONArray alertsJson = warframeJson.getJSONArray("Alerts");
            EmbedBuilder alert = new EmbedBuilder();
            long expire, time = Instant.now().toEpochMilli();
            String jour, heure, minute, seconde, fin, recompense;
            String[] node;
            int[] level;

            for (int i = 0; i < alertsJson.length(); i++) {
                fin = "";
                recompense = "";
                level = new int[2];
                node = starCharJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("location")).getString("name").replace("[", "$").split("[$]");

                expire = Long.parseLong(alertsJson.getJSONObject(i).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
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

                level[0] = alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getInt("minEnemyLevel");
                level[1] = alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getInt("maxEnemyLevel");

                recompense += "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getInt("credits") + " cr\n";

                if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names().length() > 1) {
                    if (findNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "items"))
                        recompense += "          - " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).toLowerCase()).getString("value") + " \n";
                    if (findNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "countedItems"))
                        recompense += "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getInt("ItemCount") +
                                " " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value") + "\n";
                }

                if (node[1].contains("PH")) node[1] = "Earth]";
                alert.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("missionType")).getString("value"),
                        "expire dans : " + fin + "\nlevel : " + level[0] + " - " + level[1] + "\nrécompenses :\n" + recompense, false);
            }

            alert.setTitle("Alert", "http://warframe.wikia.com/wiki/Alert");
            alert.setDescription("en cours");
            alert.setThumbnail("http://i.imgur.com/KQ7f9l7.png");
            alert.setColor(new Color(178, 23, 46));
            alert.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(alert.build()).queue();

        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    public static void alertWithInterest(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), starCharJson = new JSONObject(starChar), missionJson = new JSONObject(mission);
            JSONArray alertsJson = warframeJson.getJSONArray("Alerts");
            EmbedBuilder alert = new EmbedBuilder();
            long expire, time = Instant.now().toEpochMilli();
            String jour, heure, minute, seconde, fin, recompense = "";
            String[] node;
            int[] level;

            for (int i=0; i<alertsJson.length(); i++) {
                if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names().length() > 1) {
                    fin = "";
                    level = new int[2];
                    node = starCharJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("location")).getString("name").replace("[","$").split("[$]");

                    expire = Long.parseLong(alertsJson.getJSONObject(i).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
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

                    level[0] = alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getInt("minEnemyLevel");
                    level[1] = alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getInt("maxEnemyLevel");

                    if (findNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "items")) {
                        if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).contains("StoreItems") &&
                                !alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).contains("Fusion")) {
                            recompense = "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getInt("credits") + " cr\n";
                            recompense += "          - " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).toLowerCase()).getString("value") + " \n";

                            if (node[1].contains("PH")) node[1] = "Earth]";
                            alert.addField(node[0] + " (" + node[1].substring(0, node[1].length()-1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("missionType")).getString("value"),
                                    "expire dans : " + fin + "\nlevel : " + level[0] + " - " + level[1] + "\nrécompenses :\n" + recompense, false);
                        }
                    }
                    if (findNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "countedItems")) {
                        if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("Alertium") ||
                                alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("Catbrow")||
                                alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("Eventium")) {
                            recompense = "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getInt("credits") + " cr\n";
                            recompense += "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getInt("ItemCount") +
                                    " " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value") + "\n";

                            if (node[1].contains("PH")) node[1] = "Earth]";
                            alert.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("missionType")).getString("value"),
                                    "expire dans : " + fin + "\nlevel : " + level[0] + " - " + level[1] + "\nrécompenses :\n" + recompense, false);
                        }
                    }
                }
            }

            if (recompense.equals("")) alert.addField("aucune alerte avec une récompense intéressante", "", false);

            alert.setTitle("Alert", "http://warframe.wikia.com/wiki/Alert");
            alert.setDescription("en cours, ayant une récompenses intéressante\n");
            alert.setThumbnail("http://i.imgur.com/KQ7f9l7.png");
            alert.setColor(new Color(178, 23, 46));
            alert.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(alert.build()).queue();

        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
