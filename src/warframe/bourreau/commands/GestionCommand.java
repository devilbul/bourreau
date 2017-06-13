package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Find.FindClan;
import static warframe.bourreau.util.Recup.recupString;

public class GestionCommand extends Command {

    public static void AddClan(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContent();

                if (commande.contains(" ")) {
                    String[] newClan = recupString(event.getMessage().getContent()).replaceFirst("/", "@").split(" @ ");

                    if (newClan.length >= 2) {
                        String leader = newClan[1];
                        String clan = newClan[0];
                        String alliance = new String(Files.readAllBytes(Paths.get("info" + File.separator + "alliance.json")));
                        JSONObject allianceJson = new JSONObject(alliance);
                        JSONObject clanJson = allianceJson.getJSONObject("clan");

                        if (!FindClan(clanJson.names(), clan)) {
                            String adresseAlliance = System.getProperty("user.dir") + File.separator + "info" + File.separator + "alliance.json";
                            FileWriter file = new FileWriter(adresseAlliance);
                            JSONObject newClanJson = new JSONObject();
                            JSONObject newAllianceJson = new JSONObject();

                            newClanJson.put("nom", clan);
                            newClanJson.put("leader", leader);

                            clanJson.put(clan, newClanJson);

                            event.getTextChannel().sendMessage("clan ajouté.").queue();

                            newAllianceJson.put("nomAlliance", "French Connection");
                            newAllianceJson.put("nbClan", clanJson.length());
                            newAllianceJson.put("clan", clanJson);

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
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void RemoveClan(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContent();

                if (commande.contains(" ")) {
                    String clan = recupString(event.getMessage().getContent());
                    String alliance = new String(Files.readAllBytes(Paths.get("info" + File.separator + "alliance.json")));
                    JSONObject allianceJson = new JSONObject(alliance);
                    JSONObject clanJson = allianceJson.getJSONObject("clan");

                    if (FindClan(clanJson.names(), clan) ){
                        String adresseAlliance = System.getProperty("user.dir") + File.separator + "info" + File.separator + "alliance.json";
                        FileWriter file = new FileWriter(adresseAlliance);
                        JSONObject newAllianceJson = new JSONObject();

                        clanJson.remove(clan);

                        newAllianceJson.put("nomAlliance", "French Connection");
                        newAllianceJson.put("nbClan", clanJson.length());
                        newAllianceJson.put("clan", clanJson);

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
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
