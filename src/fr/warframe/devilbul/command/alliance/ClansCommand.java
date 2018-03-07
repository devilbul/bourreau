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
import static fr.warframe.devilbul.utils.Find.findClanKey;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.enumeration.ClanWarframe.*;
import static fr.warframe.devilbul.utils.enumeration.ClanWarframe.Lune;
import static fr.warframe.devilbul.utils.enumeration.ClanWarframe.Montagne;

public class ClansCommand extends SimpleCommand {

    @Command(name = "clans")
    @Help(field = "**syntaxe** :      !clans\n**effet :**         affiche les clans faisant paarti de l'alliance", categorie = Categorie.Alliance)
    public static void listClan(MessageReceivedEvent event) {
        try {
            StringBuilder clanString = new StringBuilder();
            String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder clan = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            List<String> buffer = new ArrayList<>();
            int nbClan = clanJson.length();

            for (int i = 0; i < nbClan; i++)
                buffer.add(clanJson.names().getString(i));

            Collections.sort(buffer);

            for (int j = 0; j < nbClan; j++)
                clanString.append(buffer.get(j)).append("\n");

            clan.setTitle("**Alliance :** " + nomAlliance, "http://wfraid.teamfr.net/");
            clan.setThumbnail(logoUrlAlliance);
            clan.addField("Liste des " + nbClan + " clans :", clanString.toString(), false);
            clan.setColor(new Color(0, 0, 0));
            clan.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

            event.getTextChannel().sendMessage(clan.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    @Command(name = "clan")
    @Help(field = "**syntaxe 1** :   !clan <nom du clan>\n**effet :**            affiche les leaders du clan <nom du clan>", categorie = Categorie.Alliance)
    public static void leader(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay();
            StringBuilder leader = new StringBuilder();
            StringBuilder clan;
            String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder lead = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            if (commande.contains(" ")) {
                clan = new StringBuilder(recupString(commande));

                for (int i = 0; i < nbClan; i++) {
                    if (clanJson.names().getString(i).toLowerCase().equals(clan.toString().toLowerCase())) {
                        JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                        for (int j = 0; j < leaders.length(); j++)
                            leader.append(leaders.getString(j)).append("\n");
                    }
                }

                if (clanJson.names().toString().toLowerCase().contains(clan.toString().toLowerCase()) && clanJson.getJSONObject(findClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl").isEmpty())
                    lead.setThumbnail(logoUrlAlliance);
                else
                    lead.setThumbnail(clanJson.getJSONObject(findClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl"));

                if (clanJson.getJSONObject(clan.toString()).getString("tailleClan").equals(Fantome.getType()))
                    lead.setImage(Fantome.getUrlImage());
                else if (clanJson.getJSONObject(clan.toString()).getString("tailleClan").equals(Ombre.getType()))
                    lead.setImage(Ombre.getUrlImage());
                else if (clanJson.getJSONObject(clan.toString()).getString("tailleClan").equals(Tempete.getType()))
                    lead.setImage(Tempete.getUrlImage());
                else if (clanJson.getJSONObject(clan.toString()).getString("tailleClan").equals(Montagne.getType()))
                    lead.setImage(Montagne.getUrlImage());
                else if (clanJson.getJSONObject(clan.toString()).getString("tailleClan").equals(Lune.getType()))
                    lead.setImage(Lune.getUrlImage());

                lead.setTitle("**Alliance :** " + nomAlliance, "http://wfraid.teamfr.net/");
                lead.setDescription(clan.toString());
                lead.addField("Warlord(s) :", leader.toString(), false);
                lead.addField("Taille du clan :", clanJson.getJSONObject(clan.toString()).getString("tailleClan"), false);
                lead.setColor(new Color(0, 0, 0));
                lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                if (!leader.toString().equals(""))
                    event.getTextChannel().sendMessage(lead.build()).queue();
                else
                    event.getTextChannel().sendMessage("Ce clan ne fait pas parti de l'alliance ou il est mal écrit.").queue();
            } else
                event.getTextChannel().sendMessage("Aucun clan saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
