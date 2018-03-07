package fr.warframe.devilbul.listener.bot.event;

import fr.warframe.devilbul.Bourreau;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
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

public class GuildLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
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
            MessageBuilder message = new MessageBuilder();

            message.append("Bourreau n'est plus sur le serveur : **");
            message.append(event.getGuild().getName());
            message.append("** (");
            message.append(event.getGuild().getId());
            message.append(") !");

            configCategoryJson.getJSONObject("categories").remove(event.getGuild().getId());
            fileCategory.write(configCategoryJson.toString(3));
            fileCategory.flush();
            fileCategory.close();

            configCommandJson.getJSONObject("commandes").remove(event.getGuild().getId());
            fileCommand.write(configCommandJson.toString(3));
            fileCommand.flush();
            fileCommand.close();

            configFunctionalityJson.getJSONObject("fonctionnalites").remove(event.getGuild().getId());
            fileFunctionality.write(configFunctionalityJson.toString(3));
            fileFunctionality.flush();
            fileFunctionality.close();

            configRoleJson.getJSONObject("roles").remove(event.getGuild().getId());
            fileRole.write(configRoleJson.toString(3));
            fileRole.flush();
            fileRole.close();

            configTextChannelJson.getJSONObject("textChannels").remove(event.getGuild().getId());
            fileTextChannel.write(configTextChannelJson.toString(3));
            fileTextChannel.flush();
            fileTextChannel.close();

            configVoiceChannelJson.getJSONObject("voiceChannels").remove(event.getGuild().getId());
            fileVoiceChannel.write(configVoiceChannelJson.toString(3));
            fileVoiceChannel.flush();
            fileVoiceChannel.close();

            Bourreau.jda.getGuildById("298503533777387530").getMemberById("180419578554220545").getUser().openPrivateChannel().complete().sendMessage(message.build()).queue();

            log(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(GuildLeaveEvent event) {
        try {
            String log = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "logs" + File.separator + "guild_leave.json")));
            JSONArray logJson = new JSONArray(log);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs" + File.separator + "guild_leave.json");

            logJson.put(new JSONObject()
                    .put("serveur", new JSONObject()
                            .put("name", event.getGuild().getName())
                            .put("id", event.getGuild().getId()))
                    .put("date", giveDate() + " | " + giveHeure())
            );

            System.out.println("---------------------------------------------------------------");
            System.out.println(giveDate() + " | " + giveHeure() + " || Guild Leave Event");
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
