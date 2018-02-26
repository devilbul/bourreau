package fr.warframe.devilbul.functionality;

import fr.warframe.devilbul.Bourreau;
import fr.warframe.devilbul.utils.annotations.functionality.Functionality;
import net.dv8tion.jda.core.entities.Role;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MinRole {

    @Functionality(name = "min_role_to_use_bot", description = "permet de faire un role minimal pour utiliser le bot")
    public static boolean canUseBot(String idAuthor, String idServeur) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(idServeur).getJSONArray("fonctionnalites");
            JSONObject configRoleJson = new JSONObject(configRole).getJSONObject("roles").getJSONObject(idServeur).getJSONObject("roles").getJSONObject("tenno");

            if (configFunctionalityJson.toList().contains("min_role_to_use_bot")) {
                if (Bourreau.jda.getGuildById(idServeur).getMemberById(idAuthor).getRoles().size() > 1)
                    return true;

                if (!configRoleJson.getString("idRole").isEmpty()) {
                    String idMinRole = configRoleJson.getString("idRole");
                    Role minRole = Bourreau.jda.getRoleById(idMinRole);

                    return Bourreau.jda.getGuildById(idServeur).getMemberById(idAuthor).getRoles().contains(minRole);
                }
            }

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return true;
        }
    }
}
