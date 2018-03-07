package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.message.event.Welcome.messageDeBienvenue;
import static fr.warframe.devilbul.message.event.Welcome.messageDeBienvenueWithPresentation;
import static fr.warframe.devilbul.utils.time.DateHeure.giveDate;
import static fr.warframe.devilbul.utils.time.DateHeure.giveHeure;

public class GuildMemberJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites");

            if (!event.getMember().getUser().isBot()) {
                if (configFunctionalityJson.toList().contains("presentation"))
                    messageDeBienvenueWithPresentation(event);
            }

            log(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(GuildMemberJoinEvent event) {
        try {
            String log = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "logs" + File.separator + "guild_member_join.json")));
            JSONArray logJson = new JSONArray(log);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs" + File.separator + "guild_member_join.json");

            logJson.put(new JSONObject()
                    .put("user", new JSONObject()
                            .put("username", event.getUser().getName())
                            .put("id", event.getUser().getId()))
                    .put("serveur", new JSONObject()
                            .put("name", event.getGuild().getName())
                            .put("id", event.getGuild().getId()))
                    .put("date", giveDate() + " | " + giveHeure())
            );

            System.out.println("---------------------------------------------------------------");
            System.out.println(giveDate() + " | " + giveHeure() + " || Guild Member Join Event");
            System.out.println("user : " + event.getUser().getName() + " (" + event.getUser().getId() + ")");
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

