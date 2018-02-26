package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Bourreau.parser;
import static fr.warframe.devilbul.Bourreau.prefixTag;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.functionality.AvoidDefaultChannel.canWriteInDefaultChannel;
import static fr.warframe.devilbul.functionality.MinRole.canUseBot;
import static fr.warframe.devilbul.functionality.Presentation.affecteRole;
import static fr.warframe.devilbul.functionality.Presentation.isMadePresentation;
import static fr.warframe.devilbul.handle.HandleCommand.handleCommand;

public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if (!event.getAuthor().isBot()) {



                prefixTag = new JSONObject(new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "bot.json")))).getString("prefixTag");

                if (!event.getMessage().toString().contains("GUILD_MEMBER_JOIN") && !event.getMessage().getChannel().toString().startsWith("PC")) {
                    if (isMadePresentation(event.getMessage().toString(), event.getTextChannel().getId(), event.getAuthor().getId(), event.getGuild().getId()))
                        affecteRole(event.getMessage().getId(), event.getTextChannel().getId(), event.getAuthor().getId(), event.getGuild().getId());

                    if (canWriteInDefaultChannel(event.getGuild().getId(), event.getTextChannel().getId()))
                        if (canUseBot(event.getAuthor().getId(), event.getGuild().getId()))
                            if (!event.getMessage().getContentDisplay().startsWith(prefixTag + prefixTag))
                                if (event.getMessage().getContentDisplay().length() > 1)
                                    if (event.getMessage().getContentDisplay().startsWith(prefixTag)) {
                                        addReaction(event);
                                        handleCommand(parser.parse(event.getMessage().getContentDisplay().toLowerCase(), event));
                                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addReaction(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            boolean trouver = false;
            String emoteID;
            String serverID;

            emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("check").getString("idEmote");
            serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("check").getString("idServer");
            event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();

            for (Object emotes : configEmotesJson.getJSONObject("emotes").names())
                if (emotes.equals(event.getAuthor().getId())) {
                    trouver = true;
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject(event.getAuthor().getId()).getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject(event.getAuthor().getId()).getString("idServer");
                    event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();
                }

            if (!trouver) {
                emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
                serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
