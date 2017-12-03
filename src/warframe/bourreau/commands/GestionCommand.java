package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class GestionCommand extends SimpleCommand {

    @Command(name="addclan")
    public static void AddClan(MessageReceivedEvent event) {
                try {
                    if (FindAdmin(event, event.getMember())) {
                        String commande = event.getMessage().getContent();

                        if (commande.contains(" ")) {
                            String[] newClan = recupString(event.getMessage().getContent()).replaceFirst("/", "@").split(" @ ");
                            String clan = newClan[0];
                            String[] leaders = newClan[1].replace("/", "@").split(" @ ");

                            if (newClan.length >= 2) {
                                String alliance = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
                                JSONObject allianceJson = new JSONObject(alliance);
                                JSONObject clanJson = allianceJson.getJSONObject("clans");
                                JSONObject infoJson = allianceJson.getJSONObject("infos");

                                if (!FindClan(clanJson.names(), clan)) {
                                    String adresseAlliance = System.getProperty("user.dir") + File.separator + "res" + File.separator + "info" + File.separator + "Alliance.json";
                                    FileWriter file = new FileWriter(adresseAlliance);
                                    JSONObject newClanJson = new JSONObject();
                                    JSONObject newAllianceJson = new JSONObject();
                                    JSONArray leaderJson = new JSONArray();

                                    for (int i=0; i<leaders.length; i++)
                                        leaderJson.put(i, leaders[i]);

                                    newClanJson.put("leaders", leaderJson);
                                    newClanJson.put("logoUrl", "");
                                    clanJson.put(clan, newClanJson);
                                    infoJson.put("nomAlliance", "French Connection");
                                    infoJson.put("nbClan", clanJson.length());
                                    newAllianceJson.put("infos", infoJson);
                                    newAllianceJson.put("clans", clanJson);

                                    event.getTextChannel().sendMessage("clan ajouté.").queue();

                                    file.write(newAllianceJson.toString());
                                    file.flush();
                                    file.close();
                                }
                                else
                                    event.getTextChannel().sendMessage("le clan saisi est déjà dans l'alliance.").queue();
                            }
                            else if (newClan.length ==1)
                                event.getTextChannel().sendMessage("aucun leader saisi.").queue();
                            else
                                event.getTextChannel().sendMessage("erreur de syntaxe, syntaxe :                        !addclan <nom du clan> <nom du leader>" +
                                        "\nsi plusieurs leader, la syntaxe change :     !addclan <nom du clan> <leader1/leader2/.../leaderN>").queue();
                        }
                        else
                            event.getTextChannel().sendMessage("aucun clan saisi.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                }
                catch (Exception e) {
                    afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="removeclan")
    public static void RemoveClan(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContent();

                if (commande.contains(" ")) {
                    String clanLower = recupString(event.getMessage().getContent());
                    String alliance = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
                    JSONObject allianceJson = new JSONObject(alliance);
                    JSONObject clanJson = allianceJson.getJSONObject("clans");
                    JSONObject infoJson = allianceJson.getJSONObject("infos");

                    if (FindClanLower(clanJson.names(), clanLower) ){
                        String adresseAlliance = System.getProperty("user.dir") + File.separator + "res" + File.separator + "info" + File.separator + "Alliance.json";
                        FileWriter file = new FileWriter(adresseAlliance);
                        JSONObject newAllianceJson = new JSONObject();

                        clanJson.remove(FindClanKey(clanJson.names(),clanLower));

                        infoJson.put("nomAlliance", "French Connection");
                        infoJson.put("nbClan", clanJson.length());

                        newAllianceJson.put("infos", infoJson);
                        newAllianceJson.put("clans", clanJson);

                        event.getTextChannel().sendMessage("clan supprimé.").queue();

                        file.write(newAllianceJson.toString());
                        file.flush();
                        file.close();
                    }
                    else
                        event.getTextChannel().sendMessage("le clan saisi a soit été mal écrit, soit n'appartient pas à l'alliance.").queue();
                }
                else
                    event.getTextChannel().sendMessage("aucun clan saisi.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="addurl")
    public static void AddLogoUrl(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContent();

                if (commande.contains(" ")) {
                    String[] newClan = recupString(event.getMessage().getContent()).replaceFirst("/", "@").split(" @ ");
                    String clanSelect = newClan[0].toLowerCase();
                    String url = newClan[1];

                    if (newClan.length == 2) {
                        String alliance = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
                        JSONObject allianceJson = new JSONObject(alliance);
                        JSONObject clanJson = allianceJson.getJSONObject("clans");
                        JSONObject infosJson = allianceJson.getJSONObject("infos");

                        if (FindClanLower(clanJson.names(), clanSelect)) {
                            String adresseAlliance = System.getProperty("user.dir") + File.separator + "res" + File.separator + "info" + File.separator + "Alliance.json";
                            FileWriter file = new FileWriter(adresseAlliance);
                            JSONObject newClanJson = new JSONObject();
                            JSONObject newAllianceJson = new JSONObject();

                            newClanJson.put("leaders", clanJson.getJSONObject(FindClanKey(clanJson.names(),clanSelect)).getJSONArray("leaders"));
                            newClanJson.put("logoUrl", url);
                            clanJson.put(FindClanKey(clanJson.names(),clanSelect), newClanJson);

                            newAllianceJson.put("clans", clanJson);
                            newAllianceJson.put("infos", infosJson);

                            event.getTextChannel().sendMessage("url ajouté.").queue();

                            file.write(newAllianceJson.toString());
                            file.flush();
                            file.close();
                        }
                        else
                            event.getTextChannel().sendMessage("le clan saisi n'est dans l'alliance, ou est mal orthographié.").queue();
                    }
                    else if (newClan.length == 1)
                        event.getTextChannel().sendMessage("aucun url saisi.").queue();
                    else
                        event.getTextChannel().sendMessage("erreur de syntaxe, syntaxe :                        !addclan <nom du clan> <url>").queue();
                }
                else
                    event.getTextChannel().sendMessage("aucun clan saisi.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
