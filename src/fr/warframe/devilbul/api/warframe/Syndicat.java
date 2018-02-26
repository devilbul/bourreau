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

public class Syndicat {

    public static void syndicat(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar), warframeJson = new JSONObject(jsonText), missionJson = new JSONObject(mission);
            JSONArray syndicatJson = warframeJson.getJSONArray("SyndicateMissions");
            EmbedBuilder syndicat = new EmbedBuilder();
            String commande = event.getMessage().getContentDisplay().toLowerCase();

            if (commande.contains(" ")) {
                String syndic = recupString(commande);
                StringBuilder missions = new StringBuilder();
                JSONArray missionsJson = null;
                long time = Instant.now().toEpochMilli();
                long expire = Long.parseLong(syndicatJson.getJSONObject(0).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
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

                for (int i = 0; i < syndicatJson.length(); i++) {
                    if (syndicatJson.getJSONObject(i).getString("Tag").toLowerCase().contains(syndic) || syndicatJson.getJSONObject(i).getString("Tag").toLowerCase().equals(syndic + "Syndicate"))
                        missionsJson = syndicatJson.getJSONObject(i).getJSONArray("Nodes");
                }

                switch (syndic) {
                    case "arbiters":
                        syndicat.setTitle("Syndicats : Arbitres d'Hexis", "http://warframe.wikia.com/wiki/Arbiters_of_Hexis");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/1/1c/Arbiters_of_Hexis.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(55, 64, 69)); //#374045
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "newloka":
                        syndicat.setTitle("Syndicats : Nouveau Loka", "http://warframe.wikia.com/wiki/New_Loka");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette4.wikia.nocookie.net/warframe/images/6/65/New_Loka.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(42, 60, 46)); //#2A3C2E
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "cephalonsuda":
                        syndicat.setTitle("Syndicats : Céphalon Suda", "http://warframe.wikia.com/wiki/Cephalon_Suda");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette1.wikia.nocookie.net/warframe/images/a/a6/Cephalon_Suda.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(61, 55, 93)); //#3D375D
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "perrin":
                        syndicat.setTitle("Syndicats : La Séquence Perrin", "http://warframe.wikia.com/wiki/The_Perrin_Sequence");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/f/f2/The_Perrin_Sequence.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(61, 73, 99)); //#3D4963
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "redveil":
                        syndicat.setTitle("Syndicats : Voile Rouge", "http://warframe.wikia.com/wiki/Red_Veil");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette4.wikia.nocookie.net/warframe/images/3/33/Red_Veil.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(61, 24, 57)); //#3D1839
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "steelmeridian":
                        syndicat.setTitle("Syndicats : Méridien d'acier", "http://warframe.wikia.com/wiki/Steel_Meridian");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette1.wikia.nocookie.net/warframe/images/7/70/Steel_Meridian.png");
                        assert missionsJson != null;
                        for (int i = 0; i < missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[", "$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions.append(node[0]).append(" (").append(node[1].substring(0, node[1].length() - 1)).append(") - ").append(missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value")).append("\n");
                        }
                        syndicat.addField("liste des missions :", missions.toString(), false);
                        syndicat.setColor(new Color(44, 63, 70)); //#2C3F46
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : arframe.com", logoUrlAlliance);

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    default:
                        event.getTextChannel().sendMessage(syndic + " : n'est pas un syndicat").queue();
                        break;
                }
            } else {
                syndicat.setTitle("Syndicats :", "http://warframe.wikia.com/wiki/Syndicates");
                syndicat.setDescription("missions syndicales");
                syndicat.setThumbnail("https://github.com/WFCD/genesis/blob/master/src/resources/syndicate.png?raw=true");
                syndicat.addField("choissiez le syndicat, parmi :", "\nSteelMeridian\nRedVeil\nPerrin\nCephalonSuda\nNewLoka\nArbiters", true);
                syndicat.addField("les suivants :", "\nMéridien d'acier\nVoile Rouge\nLa Séquence Perrin\nCéphalon Suda\nNouveau Loka\nArbitres d'Hexis", true);
                syndicat.addField("avec la commande : **__!syndicat <choix du syndicat>__**", "exemple : !syndicat NewLoka", false);
                syndicat.setColor(new Color(70, 70, 255));
                syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", logoUrlAlliance);

                event.getTextChannel().sendMessage(syndicat.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
