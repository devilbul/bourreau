package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.message.event.FirstConnection.messagePremierConnection;
import static fr.warframe.devilbul.utils.Find.findAdminRole;
import static fr.warframe.devilbul.utils.time.DateHeure.giveDate;
import static fr.warframe.devilbul.utils.time.DateHeure.giveHeure;

public class GuildJoinListener extends ListenerAdapter {

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        try {
            String configCategory = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCategory.json")));
            String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
            String configFunctionality = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            String configVoiceChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
            JSONObject configCategoryJson = new JSONObject(configCategory);
            JSONObject configCommandJson = new JSONObject(configCommand);
            JSONObject configFunctionalityJson = new JSONObject(configFunctionality);
            JSONObject configRoleJson = new JSONObject(configRole);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);
            FileWriter fileCategory = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configCategory.json");
            FileWriter fileCommand = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configCommand.json");
            FileWriter fileFunctionality = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configFunctionality.json");
            FileWriter fileRole = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configRole.json");
            FileWriter fileTextChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configTextChannel.json");
            FileWriter fileVoiceChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configVoiceChannel.json");
            Role admin = findAdminRole(event);

            configCategoryJson.getJSONObject("categories").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("categories", new JSONObject().put("clan", new JSONObject().put("idCategory", "").put("nameCategory", ""))));
            fileCategory.write(configCategoryJson.toString(3));
            fileCategory.flush();
            fileCategory.close();

            configCommandJson.getJSONObject("commandes").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("commandes", new JSONArray().put("help")));
            fileCommand.write(configCommandJson.toString(3));
            fileCommand.flush();
            fileCommand.close();

            configFunctionalityJson.getJSONObject("fonctionnalites").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("fonctionnalites", new JSONArray()));
            fileFunctionality.write(configFunctionalityJson.toString(3));
            fileFunctionality.flush();
            fileFunctionality.close();

            configRoleJson.getJSONObject("roles").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("roles", new JSONObject().put("admin", new JSONObject().put("idRole", "").put("nameRole", "")).put("alliance", new JSONObject().put("idRole", "").put("nameRole", "")).put("tenno", new JSONObject().put("idRole", "").put("nameRole", "")).put("bucher", new JSONObject().put("idRole", "").put("nameRole", "")).put("modo", new JSONObject().put("idRole", "").put("nameRole", ""))));
            fileRole.write(configRoleJson.toString(3));
            fileRole.flush();
            fileRole.close();

            configTextChannelJson.getJSONObject("textChannels").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("textChannels", new JSONObject().put("admin", new JSONObject().put("idTextChannel", "").put("nameTextChannel", "")).put("accueil", new JSONObject().put("idTextChannel", event.getGuild().getDefaultChannel().getId()).put("nameTextChannel", event.getGuild().getDefaultChannel().getName())).put("botSpam", new JSONObject().put("idTextChannel", "").put("nameTextChannel", "")).put("raids", new JSONObject().put("idTextChannel", "").put("nameTextChannel", ""))));
            fileTextChannel.write(configTextChannelJson.toString(3));
            fileTextChannel.flush();
            fileTextChannel.close();

            configVoiceChannelJson.getJSONObject("voiceChannels").put(event.getGuild().getId(), new JSONObject().put("nameServer", event.getGuild().getName()).put("voiceChannels", new JSONObject().put("bucher", new JSONObject().put("idVoiceChannel", "").put("nameVoiceChannel", ""))));
            fileVoiceChannel.write(configVoiceChannelJson.toString(3));
            fileVoiceChannel.flush();
            fileVoiceChannel.close();

            if (event.getGuild().getMembersWithRoles(admin).size() > 0)
                for (Member member : event.getGuild().getMembersWithRoles(admin))
                    if (!member.getUser().equals(event.getGuild().getOwner().getUser()))
                        member.getUser().openPrivateChannel().complete().sendMessage(messagePremierConnection()).queue();

            event.getGuild().getOwner().getUser().openPrivateChannel().complete().sendMessage(messagePremierConnection()).queue();

            log(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(GuildJoinEvent event) {
        try {
            String log = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "logs" + File.separator + "guild_join.json")));
            JSONArray logJson = new JSONArray(log);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs" + File.separator + "guild_join.json");

            logJson.put(new JSONObject()
                    .put("serveur", new JSONObject()
                            .put("name", event.getGuild().getName())
                            .put("id", event.getGuild().getId()))
                    .put("date", giveDate() + " | " + giveHeure())
            );

            System.out.println("---------------------------------------------------------------");
            System.out.println(giveDate() + " | " + giveHeure() + " || Guild Join Event");
            System.out.println("serveur : " + event.getGuild().getName() + " (" + event.getGuild().getId() + ")");
            System.out.println("---------------------------------------------------------------");

            file.write(logJson.toString(3));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}