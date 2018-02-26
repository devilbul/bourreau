package fr.warframe.devilbul.command.supreme;

import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.Deconnection.messageDeDeconnection;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;

public class ShutdownCommand {

    @Command(name = "shutdown")
    @Help(field = "**syntaxe** :   !shutdown\n**effet :**            arrête le bot", categorie = Categorie.Supreme)
    public static void shutdown(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
                JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");

                messageDeDeconnection(event);

                if (!event.getTextChannel().getId().equals(textChannelID))
                    event.getTextChannel().sendMessage("Arrêt en cours !").queue();

                event.getJDA().shutdown();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
