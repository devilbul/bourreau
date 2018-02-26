package fr.warframe.devilbul.message.event;

import fr.warframe.devilbul.Bourreau;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Init.serveurEmoteID;

public class Welcome {

    public static void messageDeBienvenueWithPresentation(GuildMemberJoinEvent event) {
        try {
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");
            MessageBuilder messageMP = new MessageBuilder();

            messageMP.append("Salut à toi, ");
            messageMP.append(event.getMember());
            messageMP.append("\n\nJe te souhaite la bienvenue sur le discord de l'Alliance **");
            messageMP.append(event.getGuild().getName());
            messageMP.append("**\n\nPrésente toi rapidement dans le salon ");
            messageMP.append(event.getGuild().getTextChannelById(textChannelID));
            messageMP.append(".\n\nSi tu joues à Warframe, et que souhaites rejoindre notre alliance, fais ta demande en message privé au bot");
            messageMP.append(", \navec la commande **__!canditate__** <message>, un admin ou un chef de clan va te répondre.");
            messageMP.append("\n\nIl faut respecter certaines règles qui se situent dans la commande **__!regle__** ");
            messageMP.append(", dont je t'invite fortement à lire.");
            messageMP.append("\n\nPour toute question, rapport de problème, réclamation, veuillez l'envoyer au bot, en message privé, avec la commande **__!claim__** <message>");
            messageMP.append(".\nPas de spam dans ce salon, merci de faire attention.");
            messageMP.append("\n\nAu plaisir de te revoir sur le discord.");
            messageMP.append("\n\nCordialement, ");
            messageMP.append(event.getJDA().getSelfUser());
            messageMP.append("\n\nPS : pour tous problèmes lié au bot, veuillez contacter ");
            messageMP.append(Bourreau.jda.getGuildById(serveurEmoteID).getMemberById("180419578554220545"));

            event.getMember().getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void messageDeBienvenue(GuildMemberJoinEvent event) {
        MessageBuilder messageMP = new MessageBuilder();

        messageMP.append("Salut à toi, ");
        messageMP.append(event.getMember());
        messageMP.append("\n\nJe te souhaite la bienvenue sur le discord **");
        messageMP.append(event.getGuild().getName());
        messageMP.append("**\n\nPersonne à contacter en cas de problème :\n==> proprio des lieux : ");
        messageMP.append(event.getGuild().getOwner());
        messageMP.append("\n=> admins : ");

        for (Member member : event.getGuild().getMembers())
            if (member.hasPermission(Permission.ADMINISTRATOR) && !member.equals(event.getGuild().getOwner()))
                messageMP.append(member).append("\n                       ");

        messageMP.append("\nCordialement, ");
        messageMP.append(event.getJDA().getSelfUser());
        messageMP.append("\n\nPS : pour tous problèmes lié au bot, veuillez contacter ");
        messageMP.append(Bourreau.jda.getGuildById(serveurEmoteID).getMemberById("180419578554220545"));

        event.getMember().getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
    }
}
