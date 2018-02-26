package fr.warframe.devilbul.listener.bot.event;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.functionality.AuRevoir.canSayBye;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
