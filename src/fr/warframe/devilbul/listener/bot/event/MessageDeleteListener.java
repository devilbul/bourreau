package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MessageDeleteListener extends ListenerAdapter {

    @Override
    public void onMessageDelete(MessageDeleteEvent event) {
        super.onMessageDelete(event);

        try {
            String pathJson = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json")));
            JSONObject eidolonJson = new JSONObject(pathJson);

            if (event.getGuild().getId().equals(eidolonJson.getString("id_serveur")))
                if (event.getTextChannel().getId().equals(eidolonJson.getString("id_text_channel"))) {
                    if (event.getMessageId().equals(eidolonJson.getString("id_message_embed"))) {
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                        file.write(eidolonJson.put("id_message_embed", "").toString(3));
                        file.flush();
                        file.close();
                    }

                    if (event.getMessageId().equals(eidolonJson.getString("id_message_mention"))) {
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                        file.write(eidolonJson.put("id_message_mention", "").toString(3));
                        file.flush();
                        file.close();
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
