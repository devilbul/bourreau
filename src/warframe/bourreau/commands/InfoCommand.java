package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

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
import static warframe.bourreau.util.Recup.recupString;

public class InfoCommand extends SimpleCommand {

    @Command(name="alerts")
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

    @Command(name="alliance")
    public static void Alliance(MessageReceivedEvent event) {
        try {
            String info = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
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

    @Command(name="discordwf")
    public static void DiscordWarframe(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.gg/K4GbEUe").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="goals")
    public static void Goals(MessageReceivedEvent event) {
        try {
            Goal(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="idee")
    public static void Idee(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://docs.google.com/document/d/1kb-sIRzCQlau5JL2q2WZRFlUy94JKWLF8p4xvfRXJFU/edit?usp=sharing").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="info")
    public static void Info(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://deathsnacks.com/wf").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="invasions")
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

    @Command(name="invite")
    public static void InvitationServeur(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.me/frenchco").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="clan")
    public static void ListClan(MessageReceivedEvent event) {
        try {
            StringBuilder clanString = new StringBuilder();
            String alliance = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder clan = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            for (int i=0; i<nbClan; i++)
                clanString.append(clanJson.names().get(i)).append("\n");

            clan.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
            clan.setThumbnail("http://i.imgur.com/BUkD1OV.png");
            clan.addField("__liste des " + nbClan + " clans :__", clanString.toString(), false);
            clan.setColor(new Color(13, 237, 255));
            clan.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(clan.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="lead")
    public static void ListLeader(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContent();
            StringBuilder leader = new StringBuilder();
            StringBuilder clan = new StringBuilder();
            String alliance = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);
            JSONObject clanJson = allianceJson.getJSONObject("clans");
            EmbedBuilder lead = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = clanJson.length();

            if (commande.contains(" ")) {
                clan = new StringBuilder(recupString(commande).toLowerCase());

                for (int i=0; i<nbClan; i++) {
                    if(clanJson.names().getString(i).toLowerCase().equals(clan.toString().toLowerCase())) {
                        JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                        for (int j=0; j<leaders.length(); j++)
                            leader.append(leaders.getString(j)).append("\n");
                    }
                }

                lead.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
                lead.setDescription(clan.toString());
                if (clanJson.names().toString().toLowerCase().contains(clan.toString()) && clanJson.getJSONObject(FindClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl").isEmpty())
                    lead.setThumbnail("http://i.imgur.com/BUkD1OV.png");
                else
                    lead.setThumbnail(clanJson.getJSONObject(FindClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl"));
                lead.addField("Warlord(s) :", leader.toString(), false);
                lead.setColor(new Color(13, 237, 255));
                lead.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                if (!leader.toString().equals(""))
                    event.getTextChannel().sendMessage(lead.build()).queue();
                else
                    event.getTextChannel().sendMessage("ce clan ne fait pas parti de l'alliance ou il est mal écrit.").queue();
            }
            else {
                for (int i=0; i<nbClan; i++) {
                    JSONArray leaders = clanJson.getJSONObject(clanJson.names().getString(i)).getJSONArray("leaders");

                    for (int j=0; j<leaders.length(); j++) {
                        if (j !=leaders.length()-1)
                            leader.append(leaders.getString(j)).append(" / ");
                        else if (j == leaders.length()-1)
                            leader.append(leaders.getString(j)).append("\n");
                    }

                    clan.append(clanJson.names().get(i)).append(" :     \n");
                }

                lead.setTitle("**__Alliance :__** " + nomAlliance, "http://wfraid.teamfr.net/");
                lead.setThumbnail("http://i.imgur.com/BUkD1OV.png");
                lead.setDescription(nbClan + " clans");
                lead.addField("Clans : ", clan.toString(), true);
                lead.addField("Warlods : ", leader.toString(), true);
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

    @Command(name="proges")
    public static void Progression(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Voici ma progression : \nhttps://trello.com/b/JEEkreCv").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="pvp")
    public static void PvpChallenge(MessageReceivedEvent event) {
        try {
            PVPChallenge(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="raid")
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

    @Command(name="site")
    public static void Site(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://wfraid.teamfr.net/").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="sortie")
    public static void Sorties(MessageReceivedEvent event) {
        try {
            Sortie(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="steam")
    public static void Steam(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://steamcommunity.com/groups/wfraid").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="syndicat")
    public static void Syndicats(MessageReceivedEvent event) {
        try {
            Syndicat(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="ts")
    public static void Ts(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("mine.ts-devil.eu:8334").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="up")
    public static void Upcoming(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://warframe.wikia.com/wiki/Upcoming_Features").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="updates")
    public static void UpdateHotfix(MessageReceivedEvent event) {
        try {
            Updates(event);
        }
        catch (Exception e) {
                afficheErreur(event, e);
                saveErreur(event, e);
        }
    }

    @Command(name="baro")
    public static void VoidTraders(MessageReceivedEvent event) {
        try {
            Baro(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="void")
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
