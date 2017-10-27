package warframe.bourreau.commands;

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

import static warframe.bourreau.api.warframeAPI.*;
import static warframe.bourreau.api.warframeAPI.Invasion;
import static warframe.bourreau.api.wfRaidAPI.*;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindClanKey;
import static warframe.bourreau.util.MessageOnEvent.MessageReglement;
import static warframe.bourreau.util.Recup.recupString;

public class InfoCommand extends Command {

    public static void Alerts(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContent().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) AlertWithInterest(event);
            else Alert(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Alliance(MessageReceivedEvent event) {
        try {
            String info = new String(Files.readAllBytes(Paths.get("info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(info);
            EmbedBuilder alliance = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = allianceJson.getJSONObject("infos").getInt("nbClan");

            alliance.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
            alliance.setThumbnail("http://i.imgur.com/BUkD1OV.png");
            alliance.setDescription("regroupant " + nbClan + " clans");
            alliance.addField("contact : **devilbul**, **SkiLLoF**, **I3i0s**", "tapez **__!clan__**, pour afficher tous les clans\n" +
                    "tapez **__!lead__**, pour afficher tous les leaders de clans\n" +
                    "tapez **__!lead <nom de clan>__**, pour afficher tous les leaders du clan", false);
            alliance.setColor(new Color(13, 237, 255));
            alliance.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(alliance.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void DiscordWarframe(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.gg/K4GbEUe").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Goals(MessageReceivedEvent event) {
        try {
            Goal(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Idee(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://docs.google.com/document/d/1kb-sIRzCQlau5JL2q2WZRFlUy94JKWLF8p4xvfRXJFU/edit?usp=sharing").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Info(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://deathsnacks.com/wf").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Invasions(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContent().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) InvasionWithInterest(event);
            else Invasion(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void InvitationServeur(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.gg/ZKMkTxJ").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void ListClan(MessageReceivedEvent event) {
        try {
            String clanString = "";
            String alliance = new String(Files.readAllBytes(Paths.get("info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder clan = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            for (int i=0; i<nbClan; i++)
                clanString += clanJson.names().get(i) + "\n";

            clan.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
            clan.setThumbnail("http://i.imgur.com/BUkD1OV.png");
            clan.addField("__liste des " + nbClan + " clans :__", clanString, false);
            clan.setColor(new Color(13, 237, 255));
            clan.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(clan.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void ListLeader(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContent();
            String leader = "";
            String clan = "";
            String alliance = new String(Files.readAllBytes(Paths.get("info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder lead = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            if (commande.contains(" ")) {
                clan = recupString(commande).toLowerCase();

                for (int i=0; i<nbClan; i++) {
                    if(clanJson.names().getString(i).toLowerCase().equals(clan.toLowerCase())) {
                        JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                        for (int j=0; j<leaders.length(); j++)
                            leader += leaders.getString(j) + "\n";
                    }
                }

                lead.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
                lead.setDescription(clan);
                if (clanJson.names().toString().toLowerCase().contains(clan) && clanJson.getJSONObject(FindClanKey(clanJson.names(),clan.toLowerCase())).getString("logoUrl").isEmpty())
                    lead.setThumbnail("http://i.imgur.com/BUkD1OV.png");
                else
                    lead.setThumbnail(clanJson.getJSONObject(FindClanKey(clanJson.names(),clan.toLowerCase())).getString("logoUrl"));
                lead.addField("Warlord(s) :", leader, false);
                lead.setColor(new Color(13, 237, 255));
                lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                if (!leader.equals(""))
                    event.getTextChannel().sendMessage(lead.build()).queue();
                else
                    event.getTextChannel().sendMessage("ce clan ne fait pas parti de l'alliance ou il est mal écrit.").queue();
            }
            else {
                for (int i=0; i<nbClan; i++) {
                    JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                    for (int j=0; j<leaders.length(); j++) {
                        if (j !=leaders.length()-1)
                            leader += leaders.getString(j) + " / ";
                        else if (j == leaders.length()-1)
                            leader += leaders.getString(j) + "\n";
                    }

                    clan += clanJson.names().get(i) + " :     \n";
                }

                lead.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
                lead.setThumbnail("http://i.imgur.com/BUkD1OV.png");
                lead.setDescription(nbClan + " clans");
                lead.addField("Clans : ", clan, true);
                lead.addField("Warlods : ", leader, true);
                lead.setColor(new Color(13, 237, 255));
                lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(lead.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Progression(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Voici ma progression : \nhttps://trello.com/b/JEEkreCv").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void PvpChallenge(MessageReceivedEvent event) {
        try {
            PVPChallenge(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Raid(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContent().contains("detail")) RaidStatDetails(event);
            else RaidStat(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Site(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://wfraid.teamfr.net/").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Sorties(MessageReceivedEvent event) {
        try {
            Sortie(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Steam(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://steamcommunity.com/groups/wfraid").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Syndicats(MessageReceivedEvent event) {
        try {
            Syndicat(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Ts (MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("mine.ts-devil.eu:8334").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Upcoming(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://warframe.wikia.com/wiki/Upcoming_Features").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void UpdateHotfix(MessageReceivedEvent event) {
        try {
            Updates(event);
        }
        catch (Exception e) {
                afficheErreur(event, e);
                saveErreur(event, e);
        }
    }

    public static void VoidTraders(MessageReceivedEvent event) {
        try {
            Baro(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Void(MessageReceivedEvent event) {
        try {
            VoidFissure(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
