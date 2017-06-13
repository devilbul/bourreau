package warframe.bourreau.api;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import static warframe.bourreau.util.Find.FindJsonKey;
import static warframe.bourreau.util.urlReadJson.readAll;

public class warframeAPI {

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

            long time = warframeJson.getLong("Time") * 1000;

            if (updateJson == null && hotfixJson == null)
                event.getTextChannel().sendMessage("aucune update / hotfix trouvée").queue();

            if (updateJson != null) {
                updates.setTitle(updateJson.getJSONArray("Messages").getJSONObject(0).getString("Message"), updateJson.getString("Prop"));
                updates.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/9/9e/WarframeLogoU15.png/revision/latest?cb=20150129215506");
                updates.setDescription("Dernière mise à jour.");
                if (FindJsonKey(updateJson, "Prop")) updates.addField("Lien vers le post du forum warframe :", updateJson.getString("Prop"), true);
                if (FindJsonKey(updateJson, "ImageUrl")) updates.setImage(updateJson.getString("ImageUrl"));
                updates.setColor(new Color(17, 204, 17));
                updates.setFooter(new java.text.SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new java.util.Date(time)) + " | Source : forum.warframe.com", "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(updates.build()).complete();
            }

            if (hotfixJson != null) {
                hotfix.setTitle(hotfixJson.getJSONArray("Messages").getJSONObject(0).getString("Message"), hotfixJson.getString("Prop"));
                hotfix.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/9/9e/WarframeLogoU15.png/revision/latest?cb=20150129215506");
                hotfix.setDescription("Dernier hotfix.");
                if (FindJsonKey(hotfixJson, "Prop")) hotfix.addField("Lien vers le post du forum warframe :", hotfixJson.getString("Prop"), true);
                if (FindJsonKey(hotfixJson, "ImageUrl")) hotfix.setImage(hotfixJson.getString("ImageUrl"));
                hotfix.setColor(new Color(17, 204, 17));
                hotfix.setFooter(new java.text.SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new java.util.Date(time)) + " | Source : forum.warframe.com", "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(hotfix.build()).complete();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Baro(MessageReceivedEvent event) {

    }

    public static void Alerts(MessageReceivedEvent event) {

    }

    public static void Sortie(MessageReceivedEvent event) {

    }

    public static void PVPChallenge(MessageReceivedEvent event) {

    }

    public static void Events(MessageReceivedEvent event) {

    }

    public static void Invasion(MessageReceivedEvent event) {

    }

    public static void InvasionWithInterest(MessageReceivedEvent event) {

    }
}
