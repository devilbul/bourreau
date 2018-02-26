package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Deconnection {

    public static void messageDeDeconnection(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
            String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");
            MessageBuilder shutdown = new MessageBuilder();

            shutdown.append("Je vais aller faire un som, à plus en LAN.\n");
            shutdown.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));

            event.getJDA().getTextChannelById(textChannelID).sendMessage(shutdown.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
