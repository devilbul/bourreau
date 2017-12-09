package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.messsage.MessageOnEvent.MessageDeDeconnection;

public class ShutdownCommand extends SimpleCommand {

    @Command(name="shutdown")
    public static void Shutdown(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
                JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");

                MessageDeDeconnection(event);

                if (!event.getTextChannel().getId().equals(textChannelID))
                    event.getTextChannel().sendMessage("Arrêt en cours !").queue();

                event.getJDA().shutdown();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
