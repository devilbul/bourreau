package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Bourreau.parserPrivate;
import static fr.warframe.devilbul.Bourreau.prefixTag;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.handle.HandlPrivateCommand.handlePrivateCommand;
import static fr.warframe.devilbul.utils.Find.findUserToServers;

public class PrivateMessageReceivedListener extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        try {
            if (event.getAuthor() != event.getJDA().getSelfUser() && !event.getMessage().getContentDisplay().equals("!")) {
                prefixTag = new JSONObject(new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "bot.json")))).getString("prefixTag");

                if (findUserToServers(event))
                    if (event.getMessage().getContentDisplay().startsWith(prefixTag)) {
                        addReactionPrivate(event);
                        handlePrivateCommand(parserPrivate.parsePrivate(event.getMessage().getContentDisplay().toLowerCase(), event));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addReactionPrivate(PrivateMessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            boolean trouver = false;
            String emoteID;
            String serverID;

            emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("check").getString("idEmote");
            serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("check").getString("idServer");
            event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();

            for (Object emotes : configEmotesJson.getJSONObject("emotes").names())
                if (emotes.equals(event.getAuthor().getId())) {
                    trouver = true;
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject(event.getAuthor().getId()).getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject(event.getAuthor().getId()).getString("idServer");
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();
                }
            if (!trouver) {
                emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
                serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");
                event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}