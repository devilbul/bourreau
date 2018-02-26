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

public class RemoveClanCommand extends SimpleCommand {

    @Command(name = "removeclan")
    @Help(field = "**syntaxe** :      !removeclan <nom du clan>\n**effet :**            supprime le clan <nom du clan> de liste alliance", categorie = Categorie.Admin)
    public static void removeClan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ")) {
                    String clanLower = recupString(event.getMessage().getContentDisplay());
                    String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
                    JSONObject allianceJson = new JSONObject(alliance);
                    JSONObject clanJson = allianceJson.getJSONObject("clans");
                    JSONObject infoJson = allianceJson.getJSONObject("infos");

                    if (findClanLower(clanJson.names(), clanLower)) {
                        String adresseAlliance = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "info" + File.separator + "Alliance.json";
                        FileWriter file = new FileWriter(adresseAlliance);
                        JSONObject newAllianceJson = new JSONObject();

                        clanJson.remove(findClanKey(clanJson.names(), clanLower));

                        infoJson.put("nbClan", clanJson.length());

                        newAllianceJson.put("infos", infoJson);
                        newAllianceJson.put("clans", clanJson);

                        event.getTextChannel().sendMessage("Clan supprimé.").queue();

                        file.write(newAllianceJson.toString(3));
                        file.flush();
                        file.close();
                    } else
                        event.getTextChannel().sendMessage("Le clan saisi a soit été mal écrit, soit n'appartient pas à l'alliance.").queue();
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
