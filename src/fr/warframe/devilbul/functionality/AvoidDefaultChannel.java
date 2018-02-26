package fr.warframe.devilbul.functionality;

import fr.warframe.devilbul.utils.annotations.functionality.Functionality;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AvoidDefaultChannel {

    @Functionality(name = "avoid_command_default_channel", description = "évite de polluer le channel par défault")
    public static boolean canWriteInDefaultChannel(String idServeur, String idTextChannel) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(idServeur).getJSONArray("fonctionnalites");
            JSONObject configTextChannelJson = new JSONObject(configTextChannel).getJSONObject("textChannels").getJSONObject(idServeur).getJSONObject("textChannels");

            return !configFunctionalityJson.toList().contains("avoid_command_default_channel") || !configTextChannelJson.getJSONObject("accueil").getString("idTextChannel").equals(idTextChannel);
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
