package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindUserVC;

public class TrollCommand extends SimpleCommand {

    public static void FDP(MessageReceivedEvent event) {

    }

    @Command(name="pute")
    public static void Pute(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            MessageBuilder pute = new MessageBuilder();
            int choix = ThreadLocalRandom.current().nextInt(0, 3);
            String emoteID;
            String serverID;

            switch (choix) {
                case 0:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("291736390721339414").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("291736390721339414").getString("idServer");
                    pute.append("'Yual': :3\n");
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                case 1:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("147022628085825536").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("147022628085825536").getString("idServer");
                    pute.append("lukinu_u': :3\n");
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                case 2:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("231732702376624129").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("231732702376624129").getString("idServer");
                    pute.append("'tenshipute': :3\n");
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    pute.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                default:
                    break;
            }

            event.getTextChannel().sendMessage(pute.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Rekt(MessageReceivedEvent event) {

    }

    @Command(name="rip")
    public static void RIP(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("#JeSuisBourreau !").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="segpa")
    public static void Segpta(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            MessageBuilder segpa = new MessageBuilder();
            int choix = ThreadLocalRandom.current().nextInt(0, 3);
            String emoteID;
            String serverID;

            switch (choix) {
                case 0:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("155820408195514369").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("155820408195514369").getString("idServer");
                    segpa.append("'SkiLLoF' : Segpa\n");
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                case 1:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("155639329094369280").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("155639329094369280").getString("idServer");
                    segpa.append("'Moumous' : Segpa\n");
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                case 2:
                    emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("206483246685487104").getString("idEmote");
                    serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("206483246685487104").getString("idServer");
                    segpa.append("'Clarkkeint' : Segpa\n");
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    segpa.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
                    break;
                default:
                    break;
            }

            event.getTextChannel().sendMessage(segpa.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="tg")
    public static void Tg(MessageReceivedEvent event) {
        try {
            MessageBuilder message = new MessageBuilder();

            message.append("TG toi même, ");
            message.append(event.getAuthor());

            event.getTextChannel().sendMessage(message.build()).queue();

            if (FindUserVC(event) != null && !event.getAuthor().equals(event.getGuild().getOwner().getUser()))
                event.getGuild().getController().setMute(event.getMember(), true).submit();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
