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
import static fr.warframe.devilbul.parser.StringParser.parseInsertSpace;
import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Goals {

    public static void goal(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String missionInfo = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar), warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), missionJson = new JSONObject(missionInfo);
            JSONArray goalsJson = warframeJson.getJSONArray("Goals");
            EmbedBuilder goal = new EmbedBuilder();
            long expire, time = Instant.now().toEpochMilli();
            String jour, heure, minute, seconde, fin, mission, progression;
            StringBuilder recompense;
            String[] node;
            int[] level;
            double pourcentage;

            if (goalsJson.length() > 0) {
                expire = Long.parseLong(goalsJson.getJSONObject(0).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
                jour = new SimpleDateFormat("dd").format(new Date(expire - time - 3600000));
                heure = new SimpleDateFormat("HH").format(new Date(expire - time - 3600000));
                minute = new SimpleDateFormat("mm").format(new Date(expire - time - 3600000));
                seconde = new SimpleDateFormat("ss").format(new Date(expire - time - 3600000));
                fin = "";
                recompense = new StringBuilder();
                mission = "";
                level = new int[2];

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

                if (findJsonKey(goalsJson.getJSONObject(0), "VictimNode")) {

                    if (goalsJson.getJSONObject(0).getString("ScoreVar").contains("Fomorian")) {
                        goal.setTitle("Balor Fomorian : " , "http://warframe.wikia.com/wiki/Fomorian");
                        goal.setThumbnail("https://vignette1.wikia.nocookie.net/warframe/images/1/13/DEGrineerFomorian.png");
                    }
                    else if (goalsJson.getJSONObject(0).getString("Desc").toLowerCase().contains("corpusrazorbackproject")) {
                        goal.setTitle("Razorback Armada : " , "http://warframe.wikia.com/wiki/Razorback");
                        goal.setThumbnail("https://vignette2.wikia.nocookie.net/warframe/images/2/24/ArmoredJackal.png");
                    }
                    else {
                        String[] str = goalsJson.getJSONObject(0).getString("Desc").toLowerCase().split("/");
                        goal.setTitle(str[str.length-1], null);
                    }

                    node = starCharJson.getJSONObject(goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getString("location")).getString("name").replace("[","$").split("[$]");
                    level[0] = goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getInt("minEnemyLevel");
                    level[1] = goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getInt("maxEnemyLevel");

                    mission += "type : " + missionJson.getJSONObject("MissionType").getJSONObject(goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getString("missionType")).getString("value") + "\n";
                    mission += "level : " + level[0] + " - " + level[1] + "\n";

                    if (goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getBoolean("archwingRequired"))
                        mission += "archwing requis\n";

                    if (languageJson.toString().toLowerCase().contains("razorback") || languageJson.toString().toLowerCase().contains("razor back"))
                        mission += languageJson.getJSONObject(goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getJSONArray("requiredItems").getString(0).toLowerCase()).getString("value") + " requis";
                    else {
                        String[] str = goalsJson.getJSONObject(0).getJSONObject("MissionInfo").getJSONArray("requiredItems").getString(0).toLowerCase().split("/");
                        mission += str[str.length-1] + " requis";
                    }

                    if (node[1].contains("PH")) node[1] = "Earth]";
                    goal.addField("Mission : " + node[0] + " (" + node[1].substring(0, node[1].length()-1) + ")\n", mission, false);

                    pourcentage = goalsJson.getJSONObject(0).getDouble("HealthPct") * 100;
                    progression = String.valueOf((int) (pourcentage * 100)/100.0) + "%";

                    goal.addField("Progression : ", progression, false);

                    node = starCharJson.getJSONObject(goalsJson.getJSONObject(0).getString("VictimNode")).getString("name").replace("[","$").split("[$]");

                    if (node[1].contains("PH")) node[1] = "Earth]";
                    goal.addField("Relais ciblé :", node[0] + " (" + node[1].substring(0, node[1].length()-1) + ")", false);
                }

                recompense.append(goalsJson.getJSONObject(0).getJSONObject("Reward").getInt("credits")).append(" cr");

                for (int i=0; i<goalsJson.getJSONObject(0).getJSONObject("Reward").getJSONArray("items").length(); i++) {
                    String[] str = goalsJson.getJSONObject(0).getJSONObject("Reward").getJSONArray("items").getString(i).split("/");
                    recompense.append("\n").append(parseInsertSpace(str[str.length - 1]));
                }

                goal.addField("Récompenses :", recompense.toString(), false);
                goal.setDescription("expire dans : " + fin);
                goal.setColor(new Color(250, 50, 50));
                goal.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);
            }
            else if (goalsJson.length() == 0) {
                goal.setTitle("Tactical Alert :", null);
                goal.setDescription("");
                goal.addField("Aucune alerte tactique en cours", "", false);
                goal.setColor(new Color(250, 50, 50));
                goal.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            }

            event.getTextChannel().sendMessage(goal.build()).complete();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
