package fr.warframe.devilbul.api.warframe;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;

public class Updates {

    public static void updates(MessageReceivedEvent event) {
        String urlApi = "http://content.warframe.com/dynamic/worldState.php";

        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject warframeJson = new JSONObject(jsonText), updateJson = null, hotfixJson = null;
            JSONArray eventsJson = warframeJson.getJSONArray("Events");
            EmbedBuilder updates = new EmbedBuilder(), hotfix = new EmbedBuilder();

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
                if (findJsonKey(updateJson, "Prop"))
                    updates.addField("Lien vers le post du forum warframe :", updateJson.getString("Prop"), true);
                if (findJsonKey(updateJson, "ImageUrl")) updates.setImage(updateJson.getString("ImageUrl"));
                updates.setColor(new Color(70, 70, 255));
                updates.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : forum.warframe.com", logoUrlAlliance);

                event.getTextChannel().sendMessage(updates.build()).complete();
            }

            if (hotfixJson != null) {
                hotfix.setTitle(hotfixJson.getJSONArray("Messages").getJSONObject(0).getString("Message"), hotfixJson.getString("Prop"));
                hotfix.setThumbnail("http://vignette4.wikia.nocookie.net/warframe/images/9/9e/WarframeLogoU15.png/revision/latest?cb=20150129215506");
                hotfix.setDescription("Dernier hotfix.");
                if (findJsonKey(hotfixJson, "Prop"))
                    hotfix.addField("Lien vers le post du forum warframe :", hotfixJson.getString("Prop"), true);
                if (findJsonKey(hotfixJson, "ImageUrl")) hotfix.setImage(hotfixJson.getString("ImageUrl"));
                hotfix.setColor(new Color(70, 70, 255));
                hotfix.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : forum.warframe.com", logoUrlAlliance);

                event.getTextChannel().sendMessage(hotfix.build()).complete();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
