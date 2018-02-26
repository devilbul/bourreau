package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findClan;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class AddClanComand extends SimpleCommand {

    @Command(name = "addclan", subCommand = false)
    @Help(field = "**syntaxe 1** :  !addclan <nom du clan> / <nom du leader>\n**condition :**   le clan <nom du clan> ne doit pas être dans liste alliance\n" +
            "                        \n**effet :**            ajoute le clan <nom du clan> a liste alliane\n\n" +
            "**syntaxe 2** :  !addclan <nom du clan> <leader1> / <leader2> / ... / <leaderN>\n**condition :**   le clan <nom du clan> ne doit pas être dans liste alliance\n" +
            "                        \n**effet :**            ajoute le clan <nom du clan> a liste alliance", categorie = Categorie.Admin)
    public static void addClan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ")) {
                    String[] newClan = recupString(event.getMessage().getContentDisplay()).replaceFirst("/", "@").split(" @ ");
                    String clan = newClan[0];
                    String[] leaders = newClan[1].replace("/", "@").split(" @ ");

                    String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
                    JSONObject allianceJson = new JSONObject(alliance);
                    JSONObject clanJson = allianceJson.getJSONObject("clans");
                    JSONObject infoJson = allianceJson.getJSONObject("infos");

                    if (!findClan(clanJson.names(), clan)) {
                        String adresseAlliance = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "info" + File.separator + "Alliance.json";
                        FileWriter file = new FileWriter(adresseAlliance);
                        JSONObject newClanJson = new JSONObject();
                        JSONObject newAllianceJson = new JSONObject();
                        JSONArray leaderJson = new JSONArray();

                        for (int i = 0; i < leaders.length; i++)
                            leaderJson.put(i, leaders[i]);

                        newClanJson.put("leaders", leaderJson);
                        newClanJson.put("logoUrl", "");
                        newClanJson.put("tailleClan", "?");
                        clanJson.put(clan, newClanJson);
                        infoJson.put("nbClan", clanJson.length());
                        newAllianceJson.put("infos", infoJson);
                        newAllianceJson.put("clans", clanJson);

                        event.getTextChannel().sendMessage("Clan ajouté.").queue();

                        file.write(newAllianceJson.toString(3));
                        file.flush();
                        file.close();
                    } else
                        event.getTextChannel().sendMessage("Le clan saisi est déjà dans l'alliance.").queue();
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
