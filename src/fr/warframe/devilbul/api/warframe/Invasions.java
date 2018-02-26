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
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Invasions {

    public static void invasion(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), starCharJson = new JSONObject(starChar);
            JSONArray invasionsJson = warframeJson.getJSONArray("Invasions");
            EmbedBuilder invasions = new EmbedBuilder();
            String[] factions, node, recompenses;
            StringBuilder recompense;
            String avancement;
            double pourcentage;
            int count, goal;

            for (int i=0; i<invasionsJson.length(); i++) {
                count = invasionsJson.getJSONObject(i).getInt("Count");
                count = (int) Math.sqrt(Math.pow(count, 2));
                goal = invasionsJson.getJSONObject(i).getInt("Goal");

                if (count < goal) {
                    recompense = new StringBuilder();
                    factions = new String[2];
                    factions[0] = invasionsJson.getJSONObject(i).getJSONObject("DefenderMissionInfo").getString("faction").toLowerCase().split(("_"))[1];
                    factions[1] = invasionsJson.getJSONObject(i).getJSONObject("AttackerMissionInfo").getString("faction").toLowerCase().split(("_"))[1];
                    node = starCharJson.getJSONObject(invasionsJson.getJSONObject(i).getString("Node")).getString("name").replace("[", "$").split("[$]");

                    if (factions[0].equals("infestation")) {
                        recompenses = new String[1];
                        pourcentage = (1 - count / (goal * 1.0)) * 100;
                        avancement = String.valueOf((int) (pourcentage * 100)/100.0) + "%";
                    } else {
                        recompenses = new String[2];
                        pourcentage = (count + goal) / (goal * 2.0) * 100;
                        avancement = String.valueOf((int) (pourcentage * 100) / 100.0) + "%";
                    }

                    if (recompenses.length == 1)
                        recompenses[0] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                    else if (recompenses.length == 2) {
                        recompenses[0] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                        recompenses[1] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("AttackerReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                    }

                    for (int j=0; j<recompenses.length; j++)
                        recompense.append("        - en combatant contre : ").append(factions[j]).append("\n           ").append(recompenses[j]).append("\n");

                    if (node[1].contains("PH")) node[1] = "Earth]";
                    invasions.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") : " + languageJson.getJSONObject(invasionsJson.getJSONObject(i).getString("LocTag").toLowerCase()).getString("value"),
                            "**" + factions[0] + " vs " + factions[1] + "**\navancement : " + avancement + "\nrécompense(s) :\n" + recompense, false);
                }
            }

            invasions.setTitle("Invasions", "http://warframe.wikia.com/wiki/Invasion");
            invasions.setDescription("en cours");
            invasions.setThumbnail("https://raw.githubusercontent.com/aliasfalse/genesis/master/src/resources/invasion.png");
            invasions.setColor(new Color(70, 70, 255));
            invasions.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(invasions.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    public static void invasionWithInterest(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream(), isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi), jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi), languageJson = new JSONObject(jsonTextLanguage), starCharJson = new JSONObject(starChar);
            JSONArray invasionsJson = warframeJson.getJSONArray("Invasions");
            EmbedBuilder invasions = new EmbedBuilder();
            String[] factions, node, recompenses;
            StringBuilder recompense = new StringBuilder();
            String avancement;
            double pourcentage;
            int count, goal;

            for (int i=0; i<invasionsJson.length(); i++) {
                count = invasionsJson.getJSONObject(i).getInt("Count");
                count = (int) Math.sqrt(Math.pow(count, 2));
                goal = invasionsJson.getJSONObject(i).getInt("Goal");

                if (invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("WeaponParts")
                        || invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("MiscItems")
                        || invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("Blueprint")) {
                    if (count < goal) {
                        recompense = new StringBuilder();
                        factions = new String[2];
                        factions[0] = invasionsJson.getJSONObject(i).getJSONObject("DefenderMissionInfo").getString("faction").toLowerCase().split(("_"))[1];
                        factions[1] = invasionsJson.getJSONObject(i).getJSONObject("AttackerMissionInfo").getString("faction").toLowerCase().split(("_"))[1];
                        node = starCharJson.getJSONObject(invasionsJson.getJSONObject(i).getString("Node")).getString("name").replace("[", "$").split("[$]");

                        if (factions[0].equals("infestation")) {
                            recompenses = new String[1];
                            pourcentage = (1 - count / (goal * 1.0)) * 100;
                            avancement = String.valueOf((int) (pourcentage * 100) / 100.0) + "%";
                        } else {
                            recompenses = new String[2];
                            pourcentage = (count + goal) / (goal * 2.0) * 100;
                            avancement = String.valueOf((int) (pourcentage * 100) / 100.0) + "%";
                        }

                        if (recompenses.length == 1)
                            recompenses[0] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                        else if (recompenses.length == 2) {
                            recompenses[0] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                            recompenses[1] = languageJson.getJSONObject(invasionsJson.getJSONObject(i).getJSONObject("AttackerReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value");
                        }

                        for (int j=0; j<recompenses.length; j++)
                            recompense.append("        - en combatant contre : ").append(factions[j]).append("\n           ").append(recompenses[j]).append("\n");

                        if (node[1].contains("PH")) node[1] = "Earth]";
                        invasions.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") : " + languageJson.getJSONObject(invasionsJson.getJSONObject(i).getString("LocTag").toLowerCase()).getString("value"),
                                "**" + factions[0] + " vs " + factions[1] + "**\navancement : " + avancement + "\nrécompense(s) :\n" + recompense, false);
                    }
                }
            }

            if (recompense.toString().equals("")) invasions.addField("aucune invasions avec une récompense intéressante", "", false);

            invasions.setTitle("Invasions", "http://warframe.wikia.com/wiki/Invasion");
            invasions.setDescription("en cours, ayant une récompenses intéressante");
            invasions.setThumbnail("https://raw.githubusercontent.com/aliasfalse/genesis/master/src/resources/invasion.png");
            invasions.setColor(new Color(70, 70, 255));
            invasions.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

            event.getTextChannel().sendMessage(invasions.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
