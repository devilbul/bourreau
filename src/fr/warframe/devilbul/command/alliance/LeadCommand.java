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
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findClanKey;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.enumeration.ClanWarframe.*;

public class LeadCommand extends SimpleCommand {

    @Command(name="leads")
    @Help(field = "**syntaxe 2** :   !leads\n**effet :**            affiche tous les clans, avec leurs leaders", categorie = Categorie.Alliance)
    public static void listLeader(MessageReceivedEvent event) {
        try {
            StringBuilder leader = new StringBuilder();
            StringBuilder clan = new StringBuilder();
            String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder lead = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            for (int i=0; i<nbClan; i++) {
                JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                for (int j=0; j<leaders.length(); j++) {
                    if (j !=leaders.length()-1)
                        leader.append(leaders.getString(j)).append(" / ");
                    else if (j == leaders.length()-1)
                        leader.append(leaders.getString(j)).append("\n");
                }

                clan.append(clanJson.names().get(i)).append(" :\n");
            }

            lead.setTitle("**Alliance :** " + nomAlliance, "http://wfraid.teamfr.net/");
            lead.setThumbnail(logoUrlAlliance);
            lead.setDescription(nbClan + " clans");
            lead.addField("Clans : ", clan.toString(), true);
            lead.addField("Warlords : ", leader.toString(), true);
            lead.setColor(new Color(0, 0, 0));
            lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

            event.getTextChannel().sendMessage(lead.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
