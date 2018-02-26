package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.message.event.Welcome.messageDeBienvenue;
import static fr.warframe.devilbul.message.event.Welcome.messageDeBienvenueWithPresentation;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

