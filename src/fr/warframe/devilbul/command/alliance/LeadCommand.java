package fr.warframe.devilbul.command.alliance;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class LeadCommand extends SimpleCommand {

    @Command(name = "leads")
    @Help(field = "**syntaxe** :   !leads\n**effet :**            affiche tous les clans, avec leurs leaders", categorie = Categorie.Alliance)
    public static void listLeader(MessageReceivedEvent event) {
        try {
            StringBuilder leader = new StringBuilder();
            StringBuilder clan = new StringBuilder();
            String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            JSONArray leaders;
            EmbedBuilder lead = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            List<String> buffer = new ArrayList<>();
            int nbClan = clanJson.length();

            for (int i = 0; i < nbClan; i++)
                buffer.add(clanJson.names().getString(i));

            Collections.sort(buffer);

            for (int j = 0; j < nbClan; j++) {
                leaders = clanJson.getJSONObject(buffer.get(j)).getJSONArray("leaders");

                for (int k = 0; k < leaders.length(); k++) {
                    if (k != leaders.length() - 1)
                        leader.append(leaders.getString(k)).append(" / ");
                    else if (k == leaders.length() - 1)
                        leader.append(leaders.getString(k)).append("\n");
                }

                clan.append(buffer.get(j)).append(" :\n");
            }

            lead.setTitle("**Alliance :** " + nomAlliance, "http://wfraid.teamfr.net/");
            lead.setThumbnail(logoUrlAlliance);
            lead.setDescription(nbClan + " clans");
            lead.addField("Nom des clans : ", clan.toString(), true);
            lead.addField("Représentants : ", leader.toString(), true);
            lead.setColor(new Color(0, 0, 0));
            lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

            event.getTextChannel().sendMessage(lead.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
