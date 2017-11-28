package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static warframe.bourreau.InitID.raidsID;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.DateHeure.GiveDate;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThingRaid;

public class RaidCommand extends SimpleCommand {

    public static void AffichePresent(MessageReceivedEvent event) {
        try {
            if (event.getTextChannel().getId().equals(raidsID)) {
                String raid = new String(Files.readAllBytes(Paths.get("raid" + File.separator + "raid_du_" + GiveDate() + ".json")));
                JSONObject raidJson = new JSONObject(raid);

                if (raidJson.names().length() > 1) {
                    int nbPresent = raidJson.names().length();
                    MessageBuilder present = new MessageBuilder();

                    present.append("voici ceux qui ont confirmé leur présence :\n\n");

                    for (int i=0; i<nbPresent; i++) {
                        if (!raidJson.names().getString(i).equals("date"))
                            present.append(raidJson.getJSONObject(raidJson.names().getString(i)).getString("auteur")).append("\n");
                    }

                    event.getTextChannel().sendMessage(present.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("personne n'a encore confirmé sa présence.").queue();
            }
            else
                MessageNoThingRaid(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Cancel(MessageReceivedEvent event) {
        try {
            String adresseRaid = System.getProperty("user.dir") + File.separator + "raid" + File.separator + "raid_du_" + GiveDate() + ".json";
            if (new File(adresseRaid).exists()) {
                if (event.getTextChannel().getId().equals(raidsID)) {
                    String raid = new String(Files.readAllBytes(Paths.get("raid" + File.separator + "raid_du_" + GiveDate() + ".json")));
                    JSONObject raidJson = new JSONObject(raid);

                    if (raidJson.names().toString().contains(event.getAuthor().getId())) {
                        FileWriter file = new FileWriter(adresseRaid);

                        raidJson.remove(event.getAuthor().getId());

                        event.getTextChannel().sendMessage("présence déconfirmée.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
                        event.getMessage().delete().complete();

                        file.write(raidJson.toString());
                        file.flush();
                        file.close();
                    }
                    else
                        event.getTextChannel().sendMessage("présence non confirmée.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
                }
                else
                    MessageNoThingRaid(event);
            }
            else
                event.getTextChannel().sendMessage("aucun sondage de raid en cours.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Present(MessageReceivedEvent event) {
        try {
            String adresseRaid = System.getProperty("user.dir") + File.separator + "raid" + File.separator + "raid_du_" + GiveDate() + ".json";
            if (new File(adresseRaid).exists()) {
                if (event.getTextChannel().getId().equals(raidsID)) {
                    String raid = new String(Files.readAllBytes(Paths.get("raid" + File.separator + "raid_du_" + GiveDate() + ".json")));
                    JSONObject raidJson = new JSONObject(raid);

                    if (!raidJson.names().toString().contains(event.getAuthor().getId())) {
                        JSONObject participantJson = new JSONObject();
                        FileWriter file = new FileWriter(adresseRaid);

                        participantJson.put("auteur", event.getAuthor().getName());
                        participantJson.put("auteurID", event.getAuthor().getId());

                        raidJson.put(event.getAuthor().getId(), participantJson);

                        event.getTextChannel().sendMessage("présence confirmée.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
                        event.getMessage().delete().complete();

                        file.write(raidJson.toString());
                        file.flush();
                        file.close();
                    }
                    else
                        event.getTextChannel().sendMessage("présence déjà signalée.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
                }
                else
                    MessageNoThingRaid(event);
            }
            else
                event.getTextChannel().sendMessage("aucun sondage de raid en cours.").complete().delete().completeAfter(10, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
