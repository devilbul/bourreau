package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findClanKey;
import static fr.warframe.devilbul.utils.Find.findClanLower;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class AddLogoUrlCommand extends SimpleCommand {

    @Command(name = "addurl")
    @Help(field = "**syntaxe** :      !addurl <nom du clan> / <logo url>\n**effet :**            ajoute le logo du clan dans liste alliance", categorie = Categorie.Admin)
    public static void addLogoUrl(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ")) {
                    String[] newClan = recupString(event.getMessage().getContentDisplay()).replaceFirst("/", "@").split(" @ ");
                    String clanSelect = newClan[0].toLowerCase();
                    String url = newClan[1];

                    if (newClan.length == 2) {
                        String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
                        JSONObject allianceJson = new JSONObject(alliance);
                        JSONObject clanJson = allianceJson.getJSONObject("clans");
                        JSONObject infosJson = allianceJson.getJSONObject("infos");

                        if (findClanLower(clanJson.names(), clanSelect)) {
                            String adresseAlliance = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "info" + File.separator + "Alliance.json";
                            FileWriter file = new FileWriter(adresseAlliance);
                            JSONObject newClanJson = clanJson.getJSONObject(findClanKey(clanJson.names(), clanSelect));
                            JSONObject newAllianceJson = new JSONObject();

                            newClanJson.put("logoUrl", url);
                            clanJson.put(findClanKey(clanJson.names(), clanSelect), newClanJson);

                            newAllianceJson.put("clans", clanJson);
                            newAllianceJson.put("infos", infosJson);

                            event.getTextChannel().sendMessage("url ajouté.").queue();

                            file.write(newAllianceJson.toString(3));
                            file.flush();
                            file.close();
                        } else
                            event.getTextChannel().sendMessage("Le clan saisi n'est dans l'alliance, ou est mal orthographié.").queue();
                    } else
                        event.getTextChannel().sendMessage("Erreur de syntaxe, syntaxe :                        !addclan <nom du clan> <url>").queue();
                } else
                    event.getTextChannel().sendMessage("Aucun clan saisi.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
