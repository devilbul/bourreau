package warframe.bourreau.listener;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;
import warframe.bourreau.Bourreau;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.commands.BasedCommand.Presentation;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.handle.HandleCommand.handleCommand;
import static warframe.bourreau.handle.HandleCommandPrivate.handleCommandPrivate;
import static warframe.bourreau.messsage.MessageOnEvent.MessageDeBienvenue;
import static warframe.bourreau.messsage.MessageOnEvent.MessagePremierConnection;
import static warframe.bourreau.util.Find.FindAdminRole;
import static warframe.bourreau.util.Find.FindUserToServers;

public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        try {
            String configCategory = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCategory.json")));
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            String configRole = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            String configVoiceChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
            JSONObject configCategoryJson = new JSONObject(configCategory);
            JSONObject configCommandJson = new JSONObject(configCommand);
            JSONObject configRoleJson = new JSONObject(configRole);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);
            FileWriter fileCategory = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCategory.json");
            FileWriter fileCommand = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCommand.json");
            FileWriter fileRole = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configRole.json");
            FileWriter fileTextChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configtextChannel.json");
            FileWriter fileVoiceChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configVoiceChannel.json");
            Role admin = FindAdminRole(event);

            configCategoryJson.getJSONObject("categories").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("categories", new JSONObject().put("clan", new JSONObject().put("idCategory", "").put("nameCategory", ""))));
            fileCategory.write(configCategoryJson.toString());
            fileCategory.flush();
            fileCategory.close();

            configCommandJson.getJSONObject("commandes").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("commandes", new JSONArray()).put("commandesPrivees", new JSONArray()).put("fonctionnalites", new JSONArray()));
            fileCommand.write(configCommandJson.toString());
            fileCommand.flush();
            fileCommand.close();

            configRoleJson.getJSONObject("roles").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("roles", new JSONObject().put("admin", new JSONObject().put("idRole", "").put("nameRole", "")).put("alliance", new JSONObject().put("idRole", "").put("nameRole", "")).put("tenno", new JSONObject().put("idRole", "").put("nameRole", "")).put("bucher", new JSONObject().put("idRole", "").put("nameRole", "")).put("modo", new JSONObject().put("idRole", "").put("nameRole", ""))));
            fileRole.write(configRoleJson.toString());
            fileRole.flush();
            fileRole.close();

            configTextChannelJson.getJSONObject("textChannels").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("textChannels", new JSONObject().put("admin", new JSONObject().put("idTextChannel", "").put("nameTextChannel", "")).put("accueil", new JSONObject().put("idTextChannel", "").put("nameTextChannel", "")).put("botSpam", new JSONObject().put("idTextChannel", "").put("nameTextChannel", "")).put("raids", new JSONObject().put("idTextChannel", "").put("nameTextChannel", ""))));
            fileTextChannel.write(configTextChannelJson.toString());
            fileTextChannel.flush();
            fileTextChannel.close();

            configVoiceChannelJson.getJSONObject("voiceChannels").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("voiceChannels", new JSONObject().put("bucher", new JSONObject().put("idTextChannel", "").put("nameTextChannel", ""))));
            fileVoiceChannel.write(configVoiceChannelJson.toString());
            fileVoiceChannel.flush();
            fileVoiceChannel.close();

            for (Member member : event.getGuild().getMembersWithRoles(admin))
                if (!member.getUser().equals(event.getGuild().getOwner().getUser()))
                    member.getUser().openPrivateChannel().complete().sendMessage(MessagePremierConnection()).queue();

            event.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage(MessagePremierConnection()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandJson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites");

            if (configCommandJson.toString().contains("presentation") && !event.getMember().getUser().isBot())
                MessageDeBienvenue(event);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        if (!event.getMember().getUser().isBot()) {
            MessageBuilder message = new MessageBuilder();
            message.append("Au revoir ");
            message.append(event.getMember().getUser());

            event.getGuild().getDefaultChannel().sendMessage(message.build()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandJson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites");

            if (!event.getAuthor().equals(event.getJDA().getSelfUser()) && !event.getAuthor().isBot() && !event.getMessage().getChannel().toString().startsWith("PC")) {
                if (event.getMember().getRoles().size() == 0 && configCommandJson.toString().contains("presentation"))
                    Presentation(event);
                if (event.getMessage().getContent().startsWith("!")) {
                    if (event.getMember().getRoles().size() > 0 || !configCommandJson.toString().contains("presentation")) {
                        if (event.getMessage().getContent().startsWith("!")) {
                            AddReaction(event);
                            handleCommand(Bourreau.parser.parse(event.getMessage().getContent().toLowerCase(), event));
                        }
                    }
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor() != event.getJDA().getSelfUser()) {
            if (FindUserToServers(event)) {
                if (event.getMessage().getContent().startsWith("!")) {
                    AddReactionPrivate(event);
                    handleCommandPrivate(Bourreau.parserPrivate.parsePrivate(event.getMessage().getContent().toLowerCase(), event));
                }
            }
        }
    }

    private void AddReaction(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
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
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private void AddReactionPrivate(PrivateMessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}