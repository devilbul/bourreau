package warframe.bourreau.api;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.parser.StringParser.ParseInsertSpace;
import static warframe.bourreau.util.Find.FindJsonKey;
import static warframe.bourreau.util.Find.FindNamesJSONObkect;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThing;
import static warframe.bourreau.util.Recup.recupString;
import static warframe.bourreau.util.urlReadJson.readAll;

public class warframeAPI {

    public static void Alert(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject missionJson = new JSONObject(mission);
            JSONArray alertsJson = warframeJson.getJSONArray("Alerts");
            EmbedBuilder alert = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long expire;
            String jour;
            String heure;
            String minute;
            String seconde;
            String fin;
            String recompense;
            String[] node;
            int[] level;

            for (int i=0; i<alertsJson.length(); i++) {
                fin = "";
                recompense = "";
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

                recompense += "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getInt("credits") + " cr\n";

                if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names().length() > 1) {
                    if (FindNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "items"))
                        recompense += "          - " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).toLowerCase()).getString("value") + " \n";
                    if (FindNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "countedItems"))
                        recompense += "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getInt("ItemCount") +
                                " " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").toLowerCase()).getString("value") + "\n";
                }

                if (node[1].contains("PH")) node[1] = "Earth]";
                alert.addField(node[0] + " (" + node[1].substring(0, node[1].length()-1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("missionType")).getString("value"),
                        "expire dans : " + fin + "\nlevel : " + level[0] + " - " + level[1] + "\nrécompenses :\n" + recompense, false);
            }

            alert.setTitle("Alert", "http://warframe.wikia.com/wiki/Alert");
            alert.setDescription("en cours");
            alert.setThumbnail("http://i.imgur.com/KQ7f9l7.png");
            alert.setColor(new Color(178, 23, 46));
            alert.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(alert.build()).queue();

        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void AlertWithInterest(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject missionJson = new JSONObject(mission);
            JSONArray alertsJson = warframeJson.getJSONArray("Alerts");
            EmbedBuilder alert = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long expire;
            String jour;
            String heure;
            String minute;
            String seconde;
            String fin;
            String recompense = "";
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

                    if (FindNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "items")) {
                        if (alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).contains("StoreItems") &&
                                !alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).contains("Fusion")) {
                            recompense = "          - " + alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getInt("credits") + " cr\n";
                            recompense += "          - " + languageJson.getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").getJSONArray("items").getString(0).toLowerCase()).getString("value") + " \n";

                            if (node[1].contains("PH")) node[1] = "Earth]";
                            alert.addField(node[0] + " (" + node[1].substring(0, node[1].length()-1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getString("missionType")).getString("value"),
                                    "expire dans : " + fin + "\nlevel : " + level[0] + " - " + level[1] + "\nrécompenses :\n" + recompense, false);
                        }
                    }
                    if (FindNamesJSONObkect(alertsJson.getJSONObject(i).getJSONObject("MissionInfo").getJSONObject("missionReward").names(), "countedItems")) {
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
            alert.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(alert.build()).queue();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Baro(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject baroJson = warframeJson.getJSONArray("VoidTraders").getJSONObject(0);
            EmbedBuilder baro = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long active = Long.parseLong(baroJson.getJSONObject("Activation").getJSONObject("$date").getString("$numberLong"));
            long expire = Long.parseLong(baroJson.getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong"));
            String jour;
            String heure;
            String minute;
            String seconde;
            String debut = "";
            String fin = "";
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
            }
            else if (Long.valueOf(baroJson.getJSONObject("Activation").getJSONObject("$date").getString("$numberLong")) - time < 1) {
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

                for (int i=0; i<baroJson.getJSONArray("Manifest").length(); i++)
                    baro.addField(languageJson.getJSONObject(baroJson.getJSONArray("Manifest").getJSONObject(i).getString("ItemType").toLowerCase()).getString("value"),
                            baroJson.getJSONArray("Manifest").getJSONObject(i).getInt("RegularPrice") + " cr - " + baroJson.getJSONArray("Manifest").getJSONObject(i).getInt("PrimePrice") + " ducats", false);

                baro.addField("Temps avant son départ du relai : " + node[0] + " " + node[1], fin, false);
            }

            baro.setTitle("Baro Ki'Teer", "https://www.warframe.wikia.com/wiki/Sortie");
            baro.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/d/d8/MarchandDuN%C3%A9ant.png/revision/latest?cb=20150630171447&path-prefix=fr");
            baro.setColor(new Color(36, 153, 153));
            baro.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(baro.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Darvo(MessageReceivedEvent event){
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject warframeJson = new JSONObject(jsonText);

            /***/

        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Events(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject warframeJson = new JSONObject(jsonText);

            /***/

        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Goal(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String missionInfo = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject missionJson = new JSONObject(missionInfo);
            JSONArray goalsJson = warframeJson.getJSONArray("Goals");
            EmbedBuilder goal = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long expire;
            String jour;
            String heure;
            String minute;
            String seconde;
            String fin;
            String recompense;
            String mission;
            String progression;
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
                recompense = "";
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

                if (FindJsonKey(goalsJson.getJSONObject(0), "VictimNode")) {

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

                recompense += goalsJson.getJSONObject(0).getJSONObject("Reward").getInt("credits") + " cr";

                for (int i=0; i<goalsJson.getJSONObject(0).getJSONObject("Reward").getJSONArray("items").length(); i++) {
                    String[] str = goalsJson.getJSONObject(0).getJSONObject("Reward").getJSONArray("items").getString(i).split("/");
                    recompense += "\n" + ParseInsertSpace(str[str.length - 1]);
                }

                goal.addField("Récompenses :", recompense, false);
                goal.setDescription("expire dans : " + fin);
                goal.setColor(new Color(250, 50, 50));
                goal.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");
            }
            else if (goalsJson.length() == 0) {
                goal.setTitle("Tactical Alert :", null);
                goal.setDescription("");
                goal.addField("Aucune alerte tactique en cours", "", false);
                goal.setColor(new Color(250, 50, 50));
                goal.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            }

            event.getTextChannel().sendMessage(goal.build()).complete();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Invasion(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject starCharJson = new JSONObject(starChar);
            JSONArray invasionsJson = warframeJson.getJSONArray("Invasions");
            EmbedBuilder invasions = new EmbedBuilder();
            String[] factions;
            String[] node;
            String[] recompenses;
            String recompense;
            String avancement;
            double pourcentage;
            int count;
            int goal;

            for (int i=0; i<invasionsJson.length(); i++) {
                count = invasionsJson.getJSONObject(i).getInt("Count");
                count = (int) Math.sqrt(Math.pow(count, 2));
                goal = invasionsJson.getJSONObject(i).getInt("Goal");

                if (count < goal) {
                    recompense = "";
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
                        recompense += "        - en combatant contre : " + factions[j] + "\n           " + recompenses[j] + "\n";

                    if (node[1].contains("PH")) node[1] = "Earth]";
                    invasions.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") : " + languageJson.getJSONObject(invasionsJson.getJSONObject(i).getString("LocTag").toLowerCase()).getString("value"),
                            "**" + factions[0] + " vs " + factions[1] + "**\navancement : " + avancement + "\nrécompense(s) :\n" + recompense, false);
                }
            }

            invasions.setTitle("Invasions", "http://warframe.wikia.com/wiki/Invasion");
            invasions.setDescription("en cours");
            invasions.setThumbnail("https://raw.githubusercontent.com/aliasfalse/genesis/master/src/resources/invasion.png");
            invasions.setColor(new Color(70, 70, 255));
            invasions.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(invasions.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void InvasionWithInterest(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";
        String urlLanugage = "https://raw.githubusercontent.com/WFCD/warframe-worldstate-data/master/data/languages.json";

        try {
            InputStream isApi = new URL(urlApi).openStream();
            InputStream isLanguage = new URL(urlLanugage).openStream();
            BufferedReader rdApi = new BufferedReader(new InputStreamReader(isApi, Charset.forName("UTF-8")));
            BufferedReader rdLanguage = new BufferedReader(new InputStreamReader(isLanguage, Charset.forName("UTF-8")));
            String jsonTextApi = readAll(rdApi);
            String jsonTextLanguage = readAll(rdLanguage);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            JSONObject warframeJson = new JSONObject(jsonTextApi);
            JSONObject languageJson = new JSONObject(jsonTextLanguage);
            JSONObject starCharJson = new JSONObject(starChar);
            JSONArray invasionsJson = warframeJson.getJSONArray("Invasions");
            EmbedBuilder invasions = new EmbedBuilder();
            String[] factions;
            String[] node;
            String[] recompenses;
            String recompense = "";
            String avancement;
            double pourcentage;
            int count;
            int goal;

            for (int i=0; i<invasionsJson.length(); i++) {
                count = invasionsJson.getJSONObject(i).getInt("Count");
                count = (int) Math.sqrt(Math.pow(count, 2));
                goal = invasionsJson.getJSONObject(i).getInt("Goal");

                if (invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("WeaponParts")
                        || invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("MiscItems")
                        || invasionsJson.getJSONObject(i).getJSONObject("DefenderReward").getJSONArray("countedItems").getJSONObject(0).getString("ItemType").contains("Blueprint")) {
                    if (count < goal) {
                        recompense = "";
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
                            recompense += "        - en combatant contre : " + factions[j] + "\n           " + recompenses[j] + "\n";

                        if (node[1].contains("PH")) node[1] = "Earth]";
                        invasions.addField(node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") : " + languageJson.getJSONObject(invasionsJson.getJSONObject(i).getString("LocTag").toLowerCase()).getString("value"),
                                "**" + factions[0] + " vs " + factions[1] + "**\navancement : " + avancement + "\nrécompense(s) :\n" + recompense, false);
                    }
                }
            }

            if (recompense.equals("")) invasions.addField("aucune invasions avec une récompense intéressante", "", false);

            invasions.setTitle("Invasions", "http://warframe.wikia.com/wiki/Invasion");
            invasions.setDescription("en cours, ayant une récompenses intéressante");
            invasions.setThumbnail("https://raw.githubusercontent.com/aliasfalse/genesis/master/src/resources/invasion.png");
            invasions.setColor(new Color(70, 70, 255));
            invasions.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(invasions.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void PVPChallenge(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String commande = event.getMessage().getContent().toLowerCase();
            String pvpInfo = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "DefiConclave.json")));
            JSONObject warframeJson = new JSONObject(jsonText);
            JSONObject pvpInfoJson = new JSONObject(pvpInfo);
            JSONArray pvpJson = warframeJson.getJSONArray("PVPChallengeInstances");
            EmbedBuilder pvp = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            int key = -1;
            long expire;
            String jour;
            String heure;
            String minute;
            String seconde;
            String fin = "";
            String defi = null;

            if (commande.contains(" ")) {
                String action = recupString(commande);

                if (action.equals("hebdo")) {
                    defi = "";

                    for (int i=0; i<pvpJson.length(); i++) {
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
                }
                else
                    MessageNoThing(event);
            }
            else {
                defi = "défi quotidien";

                for (int i=0; i<pvpJson.length(); i++) {
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

                for (int i=0; i<pvpJson.length(); i++) {
                    if (pvpJson.getJSONObject(i).getString("Category").contains("TypeCategory_DAILY")) {
                        if (FindJsonKey(pvpInfoJson.getJSONObject("defi"), pvpJson.getJSONObject(i).getString("challengeTypeRefID")))
                            pvp.addField(pvpInfoJson.getJSONObject("mode").getString(pvpJson.getJSONObject(i).getString("PVPMode")), pvpInfoJson.getJSONObject("defi").getString(pvpJson.getJSONObject(i).getString("challengeTypeRefID")), false);
                        else
                            pvp.addField(pvpInfoJson.getJSONObject("mode").getString(pvpJson.getJSONObject(i).getString("PVPMode")), "défi à ajouter : " + pvpJson.getJSONObject(i).getString("challengeTypeRefID"), false);
                    }
                }
            }

            pvp.setTitle("Conclave : " + defi, "http://warframe.wikia.com/wiki/Conclave");
            pvp.setThumbnail("https://vignette2.wikia.nocookie.net/warframe/images/7/7f/Teshin.png/revision/latest?cb=20150818043033");
            pvp.setColor(new Color(10, 10, 10));
            pvp.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(pvp.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Sortie(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String sortieInfo = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Sortie.json")));
            String mission = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject sortieInfoJson = new JSONObject(sortieInfo);
            JSONObject warframeJson = new JSONObject(jsonText);
            JSONObject missionJson = new JSONObject(mission);
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

            for (int i=0; i<sortieJson.getJSONArray("Variants").length(); i++) {
                node = starCharJson.getJSONObject(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("node")).getString("name").replace("[","$").split("[$]");

                if (node[1].contains("PH")) node[1] = "Earth]";
                sortie.addField(node[0] + " (" + node[1].substring(0, node[1].length()-1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("missionType")).getString("value"),
                        sortieInfoJson.getJSONObject("modifierTypes").getString(sortieJson.getJSONArray("Variants").getJSONObject(i).getString("modifierType")), false);
            }

            sortie.setTitle("Sortie vs : " + sortieInfoJson.getJSONObject("bosses").getJSONObject(sortieJson.getString("Boss")).getString("name"), "https://www.warframe.wikia.com/wiki/Sortie");
            sortie.setThumbnail(sortieInfoJson.getJSONObject("bosses").getJSONObject(sortieJson.getString("Boss")).getString("image"));
            sortie.setDescription("expire dans : " + fin);
            sortie.setColor(new Color(250, 50, 50));
            sortie.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(sortie.build()).complete();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Syndicat(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject warframeJson = new JSONObject(jsonText);
            JSONObject missionJson = new JSONObject(mission);
            JSONArray syndicatJson = warframeJson.getJSONArray("SyndicateMissions");
            EmbedBuilder syndicat = new EmbedBuilder();
            String commande = event.getMessage().getContent().toLowerCase();

            if (commande.contains(" ")) {
                String syndic = recupString(commande);
                String missions = "";
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

                for (int i=0; i<syndicatJson.length(); i++){
                    if (syndicatJson.getJSONObject(i).getString("Tag").toLowerCase().contains(syndic) || syndicatJson.getJSONObject(i).getString("Tag").toLowerCase().equals(syndic + "Syndicate"))
                        missionsJson = syndicatJson.getJSONObject(i).getJSONArray("Nodes");
                }

                switch (syndic) {
                    case "arbiters":
                        syndicat.setTitle("Syndicats : Arbitres d'Hexis", "http://warframe.wikia.com/wiki/Arbiters_of_Hexis");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/1/1c/Arbiters_of_Hexis.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(55, 64, 69)); //#374045
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "newloka":
                        syndicat.setTitle("Syndicats : Nouveau Loka", "http://warframe.wikia.com/wiki/New_Loka");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette4.wikia.nocookie.net/warframe/images/6/65/New_Loka.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(42, 60, 46)); //#2A3C2E
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "cephalonsuda":
                        syndicat.setTitle("Syndicats : Céphalon Suda", "http://warframe.wikia.com/wiki/Cephalon_Suda");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette1.wikia.nocookie.net/warframe/images/a/a6/Cephalon_Suda.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(61, 55, 93)); //#3D375D
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "perrin":
                        syndicat.setTitle("Syndicats : La Séquence Perrin", "http://warframe.wikia.com/wiki/The_Perrin_Sequence");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/f/f2/The_Perrin_Sequence.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(61, 73, 99)); //#3D4963
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "redveil":
                        syndicat.setTitle("Syndicats : Voile Rouge", "http://warframe.wikia.com/wiki/Red_Veil");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette4.wikia.nocookie.net/warframe/images/3/33/Red_Veil.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(61, 24, 57)); //#3D1839
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    case "steelmeridian":
                        syndicat.setTitle("Syndicats : Méridien d'acier", "http://warframe.wikia.com/wiki/Steel_Meridian");
                        syndicat.setDescription("missions syndicales, expire dans : " + fin);
                        syndicat.setThumbnail("https://vignette1.wikia.nocookie.net/warframe/images/7/70/Steel_Meridian.png");
                        assert missionsJson != null;
                        for (int i = 0; i<missionsJson.length(); i++) {
                            node = starCharJson.getJSONObject(missionsJson.getString(i)).getString("name").replace("[","$").split("[$]");
                            if (node[1].contains("PH")) node[1] = "Earth]";
                            missions += node[0] + " (" + node[1].substring(0, node[1].length() - 1) + ") - " + missionJson.getJSONObject("MissionType").getJSONObject(starCharJson.getJSONObject(missionsJson.getString(i)).getString("mission_type")).getString("value") + "\n";
                        }
                        syndicat.addField("liste des missions :", missions, false);
                        syndicat.setColor(new Color(44, 63, 70)); //#2C3F46
                        syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : arframe.com", "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(syndicat.build()).queue();
                        break;
                    default:
                        event.getTextChannel().sendMessage(syndic + " : n'est pas un syndicat").queue();
                        break;
                }
            }
            else {
                syndicat.setTitle("Syndicats :", "http://warframe.wikia.com/wiki/Syndicates");
                syndicat.setDescription("missions syndicales");
                syndicat.setThumbnail("https://github.com/WFCD/genesis/blob/master/src/resources/syndicate.png?raw=true");
                syndicat.addField("choissiez le syndicat, parmi :", "\nSteelMeridian\nRedVeil\nPerrin\nCephalonSuda\nNewLoka\nArbiters", true);
                syndicat.addField("les suivants :", "\nMéridien d'acier\nVoile Rouge\nLa Séquence Perrin\nCéphalon Suda\nNouveau Loka\nArbitres d'Hexis", true);
                syndicat.addField("avec la commande : **__!syndicat <choix du syndicat>__**", "exemple : !syndicat NewLoka", false);
                syndicat.setColor(new Color(70, 70, 255));
                syndicat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(syndicat.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Updates(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject warframeJson = new JSONObject(jsonText);
            JSONArray eventsJson = warframeJson.getJSONArray("Events");
            JSONObject updateJson = null;
            JSONObject hotfixJson = null;
            EmbedBuilder updates = new EmbedBuilder();
            EmbedBuilder hotfix = new EmbedBuilder();

            for (int i = 0; i < eventsJson.length(); i++) {
                if (eventsJson.getJSONObject(i).getJSONArray("Messages").getJSONObject(0).getString("Message").contains("Update"))
                    updateJson = eventsJson.getJSONObject(i);

                if (eventsJson.getJSONObject(i).getJSONArray("Messages").getJSONObject(0).getString("Message").contains("Hotfix"))
                    hotfixJson = eventsJson.getJSONObject(i);
            }

            if (updateJson == null && hotfixJson == null)
                event.getTextChannel().sendMessage("aucune update / hotfix trouvée").queue();

            if (updateJson != null) {
                updates.setTitle(updateJson.getJSONArray("Messages").getJSONObject(0).getString("Message"), updateJson.getString("Prop"));
                updates.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/9/9e/WarframeLogoU15.png/revision/latest?cb=20150129215506");
                updates.setDescription("Dernière mise à jour.");
                if (FindJsonKey(updateJson, "Prop")) updates.addField("Lien vers le post du forum warframe :", updateJson.getString("Prop"), true);
                if (FindJsonKey(updateJson, "ImageUrl")) updates.setImage(updateJson.getString("ImageUrl"));
                updates.setColor(new Color(70, 70, 255));
                updates.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : forum.warframe.com", "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(updates.build()).complete();
            }

            if (hotfixJson != null) {
                hotfix.setTitle(hotfixJson.getJSONArray("Messages").getJSONObject(0).getString("Message"), hotfixJson.getString("Prop"));
                hotfix.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/9/9e/WarframeLogoU15.png/revision/latest?cb=20150129215506");
                hotfix.setDescription("Dernier hotfix.");
                if (FindJsonKey(hotfixJson, "Prop")) hotfix.addField("Lien vers le post du forum warframe :", hotfixJson.getString("Prop"), true);
                if (FindJsonKey(hotfixJson, "ImageUrl")) hotfix.setImage(hotfixJson.getString("ImageUrl"));
                hotfix.setColor(new Color(70, 70, 255));
                hotfix.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : forum.warframe.com", "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(hotfix.build()).complete();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void VoidFissure(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            String starChar = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "StarChar.json")));
            String mission = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Mission.json")));
            JSONObject starCharJson = new JSONObject(starChar);
            JSONObject warframeJson = new JSONObject(jsonText);
            JSONObject missionJson = new JSONObject(mission);
            JSONArray fissureJson = warframeJson.getJSONArray("ActiveMissions");
            EmbedBuilder voidFissure = new EmbedBuilder();
            long time = Instant.now().toEpochMilli();
            long expire;
            String heure;
            String minute;
            String seconde;
            String fin;
            String commande = event.getMessage().getContent().toLowerCase();
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
            }
            else {
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

                    node = starCharJson.getJSONObject(fissureJson.getJSONObject(i).getString("Node")).getString("name").replace("[","$").split("[$]");

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
            voidFissure.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : warframe.com", "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(voidFissure.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
