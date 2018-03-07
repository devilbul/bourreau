package fr.warframe.devilbul.functionality;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;

import static fr.warframe.devilbul.utils.Find.getUserCensure;
import static fr.warframe.devilbul.utils.Find.isUserCensure;

public class AntiSpam {

    public static boolean isCensured(MessageReceivedEvent event) {
        try {
            String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
            JSONArray antiSpamJson = new JSONArray(antiSpam);

            if (isUserCensure(event.getAuthor().getId())) {
                int index = getUserCensure(event.getAuthor().getId());

                System.out.println(antiSpamJson.getJSONObject(index).getLong("next_music_command"));
                System.out.println(Instant.now().toEpochMilli());
                System.out.println(antiSpamJson.getJSONObject(index).getLong("next_music_command") - Instant.now().toEpochMilli());

                if (antiSpamJson.getJSONObject(index).getLong("next_music_command") > 0
                        && antiSpamJson.getJSONObject(index).getLong("next_music_command") - Instant.now().toEpochMilli() > 0)
                    return true;
                else {
                    FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "anti_spam.json");

                    antiSpamJson.getJSONObject(index).put("next_music_command", Instant.now().toEpochMilli() + antiSpamJson.getJSONObject(index).getInt("time"));

                    file.write(antiSpamJson.toString(3));
                    file.flush();
                    file.close();
                    return false;
                }
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
