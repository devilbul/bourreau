package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NoThing {

    public static void messageNoThing(MessageReceivedEvent event) {
        MessageBuilder message = new MessageBuilder();

        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes (sur un salon textuel ou en message privé).").queue();

        message.append("You know nothing, ");
        message.append(event.getAuthor());

        event.getTextChannel().sendMessage(message.build()).queue();
    }

    public static void messageNoThingRaid(MessageReceivedEvent event) {
        try {
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("raids").getString("idTextChannel");
            MessageBuilder message = new MessageBuilder();

            message.append("mauvais salon textuel, cette commande se fait dans le salon ");
            message.append(event.getJDA().getTextChannelById(textChannelID));
            message.append("\nYou know nothing, ");
            message.append(event.getAuthor());

            event.getTextChannel().sendMessage(message.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
