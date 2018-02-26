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
import static warframe.bourreau.util.Find.findUserVC;

public class TrollCommand extends SimpleCommand {

    public static void fdp(MessageReceivedEvent event) {

    }

    @Command(name="pute", subCommand=false)
    public static void pute(MessageReceivedEvent event) {
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

    public static void rekt(MessageReceivedEvent event) {

    }

    @Command(name="rip", subCommand=false)
    public static void rip(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("#JeSuisBourreau !").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="segpa", subCommand=false)
    public static void segpta(MessageReceivedEvent event) {
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

    @Command(name="tg", subCommand=false)
    public static void tg(MessageReceivedEvent event) {
        try {
            MessageBuilder message = new MessageBuilder();

            message.append("TG toi même, ");
            message.append(event.getAuthor());

            event.getTextChannel().sendMessage(message.build()).queue();

            if (findUserVC(event) != null && !event.getAuthor().equals(event.getGuild().getOwner().getUser()))
                event.getGuild().getController().setMute(event.getMember(), true).submit();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
