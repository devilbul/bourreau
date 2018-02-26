package fr.warframe.devilbul.functionality;

import fr.warframe.devilbul.utils.annotations.functionality.Functionality;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AuRevoir {

    @Functionality(name = "au_revoir", description = "permet d'afficher un message lorsqu'un utilisateur qui le serveur")
    public static boolean canSayBye(GuildMemberLeaveEvent event) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites");

            return configFunctionalityJson.toList().contains("au_revoir");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
