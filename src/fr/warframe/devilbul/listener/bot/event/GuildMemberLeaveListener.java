package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.functionality.AuRevoir.canSayBye;
import static fr.warframe.devilbul.utils.time.DateHeure.giveDate;
import static fr.warframe.devilbul.utils.time.DateHeure.giveHeure;

public class GuildMemberLeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        try {
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel).getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels");
            String accueilID = configTextChannelJson.getJSONObject("accueil").getString("idTextChannel");

            if (canSayBye(event)) {
                if (!event.getMember().getUser().isBot()) {
                    MessageBuilder message = new MessageBuilder();
                    message.append("Au revoir ");
                    message.append(event.getMember().getUser().getName());
                    message.append(" (");
                    message.append(event.getMember().getUser());
                    message.append(") !");

                    if (!accueilID.isEmpty())
                        event.getGuild().getTextChannelById(accueilID).sendMessage(message.build()).queue();
                    else
                        event.getGuild().getDefaultChannel().sendMessage(message.build()).queue();
                }
            }

            log(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(GuildMemberLeaveEvent event) {
        try {
            String log = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "logs" + File.separator + "guild_member_leave.json")));
            JSONArray logJson = new JSONArray(log);
            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "logs" + File.separator + "guild_member_leave.json");

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
            System.out.println(giveDate() + " | " + giveHeure() + " || Guild Member Leave Event");
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
