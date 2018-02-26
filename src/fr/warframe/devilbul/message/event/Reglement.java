package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reglement {

    public static void messageReglement(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
            String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            JSONObject configRolesJson = new JSONObject(configRoles);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String roleAdminID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("admin").getString("idRole");
            String roleModoID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("modo").getString("idRole");
            String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("alliance").getString("idEmote");
            String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("alliance").getString("idServer");
            String textChannelRaidsID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("raids").getString("idTextChannel");
            String textChannelBotSpamID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("botSpam").getString("idTextChannel");
            MessageBuilder reglement = new MessageBuilder();

            reglement.append("***=============================================================***\n");
            reglement.append("Salut et bienvenue sur le serveur Discord de l'Alliance **");
            reglement.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
            reglement.append("French Connection");
            reglement.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
            reglement.append(" **\n\n***=>***   __Quelques règles de bonne conduite :__\n\n `(1) Soyez respectueux.`\n");
            reglement.append(" `(2) Ne soyez pas hors-sujet et prennez en compte ce que les gens disent avant de débarquer avec un hors-sujet total.`\n");
            reglement.append(" `(3) Ne soyez pas haineux. On est ici pour passer un bon moment. Twitter est un meilleur endroit pour raconter comment vous détestez tout le monde.`\n");
            reglement.append(" `(4) Le piratage c'est pas très bien. Donc n'en parlez pas. ;)`\n\n");
            reglement.append("Bien sur, les autres **__règles de bienséance__** s'appliquent aussi, et tout ce qui est valable sur les forums ou en jeu l'est ici aussi, donc pas d'insultes, \n");
            reglement.append("devbashing, etc.\n\n\n");
            reglement.append("***=>***   __Quelques information pratique :__\n\n");
            reglement.append(" `(1) Si vous rencontrez un problème, vous pouvez mentionner les `");
            reglement.append(event.getJDA().getRoleById(roleAdminID));
            reglement.append("`et les `");
            reglement.append(event.getJDA().getRoleById(roleModoID));
            reglement.append("`, ou sinon vous pouvez aussi l'envoyer au bot, en message privé, avec la commande !claim.`\n `(2) Pour toutes les commandes de bot");
            reglement.append(", faite-les dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelBotSpamID));
            reglement.append("`.`\n `(3) Si vous ne souhaitez pas avoir de notification d'un salon textuel en particulier, fait un clic droit sur celui-ci, puis clicquez sur la checkbox.`\n\n");
            reglement.append("***=>***   __Pour ");
            reglement.append(event.getJDA().getSelfUser());
            reglement.append(" :__\n\n `(1) La plupart des commandes se font dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelBotSpamID));
            reglement.append("`.`\n `(2) Les commandes relative aux raids, se font dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelRaidsID));
            reglement.append("`.`\n `(3) Donc évitez tout spam de commande, dans les salons textuels non-dédié pour cela, sous peine de sanction.`\n");
            reglement.append("\n***=============================================================***");

            event.getTextChannel().sendMessage(reglement.build()).queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
