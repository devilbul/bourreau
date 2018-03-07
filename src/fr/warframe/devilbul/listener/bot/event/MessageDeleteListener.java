package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.events.message.MessageDeleteEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.utils.time.DateHeure.giveDate;
import static fr.warframe.devilbul.utils.time.DateHeure.giveHeure;

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

                    if (event.getMessageId().equals(eidolonJson.getString("id_message_notif"))) {
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                        file.write(eidolonJson.put("id_message_notif", "").toString(3));
                        file.flush();
                        file.close();
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void log(MessageDeleteEvent event) {
        try {
            String log = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "logs" + File.separator + "message_delete.json")));
            JSONArray logJson = new JSONArray(log);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs" + File.separator + "message_delete.json");

            logJson.put(new JSONObject()
                    .put("message_id", event.getMessageId())
                    .put("serveur", new JSONObject()
                            .put("name", event.getGuild().getName())
                            .put("id", event.getGuild().getId()))
                    .put("date", giveDate() + " | " + giveHeure())
            );

            System.out.println("---------------------------------------------------------------");
            System.out.println(giveDate() + " | " + giveHeure() + " || Message Delete Event");
            System.out.println("serveur : " + event.getGuild().getName() + " (" + event.getGuild().getId() + ")");
            System.out.println("message_id : " + event.getMessageId());
            System.out.println("---------------------------------------------------------------");

            file.write(logJson.toString(3));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
