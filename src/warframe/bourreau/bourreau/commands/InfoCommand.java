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
import static warframe.bourreau.api.wfRaidAPI.*;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.findClanKey;
import static warframe.bourreau.util.Recup.recupString;

public class InfoCommand extends SimpleCommand {

    @Command(name="alerts", subCommand=false)
    public static void alerts(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) alertWithInterest(event);
            else alert(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="alliance", subCommand=false)
    public static void alliance(MessageReceivedEvent event) {
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

    @Command(name="discordwf", subCommand=false)
    public static void discordWarframe(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.gg/K4GbEUe").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="goals", subCommand=false)
    public static void goals(MessageReceivedEvent event) {
        try {
            goal(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="idee", subCommand=false)
    public static void idee(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://docs.google.com/document/d/1kb-sIRzCQlau5JL2q2WZRFlUy94JKWLF8p4xvfRXJFU/edit?usp=sharing").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="info", subCommand=false)
    public static void info(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://deathsnacks.com/wf").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="invasions", subCommand=false)
    public static void invasions(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay().toLowerCase();

            if (commande.contains(" ") && recupString(commande).equals("interest")) invasionWithInterest(event);
            else invasion(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="invite", subCommand=false)
    public static void invitationServeur(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://discord.me/frenchco").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="clan", subCommand=false)
    public static void listClan(MessageReceivedEvent event) {
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

    @Command(name="lead", subCommand=false)
    public static void listLeader(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContentDisplay();
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
                if (clanJson.names().toString().toLowerCase().contains(clan.toString()) && clanJson.getJSONObject(findClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl").isEmpty())
                    lead.setThumbnail("http://i.imgur.com/BUkD1OV.png");
                else
                    lead.setThumbnail(clanJson.getJSONObject(findClanKey(clanJson.names(), clan.toString().toLowerCase())).getString("logoUrl"));
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

    @Command(name="proges", subCommand=false)
    public static void progression(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Voici ma progression : \nhttps://trello.com/b/JEEkreCv").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="pvp", subCommand=false)
    public static void pvpChallenges(MessageReceivedEvent event) {
        try {
            pvpChallenge(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="raid", subCommand=false)
    public static void raid(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains("detail")) raidStatDetails(event);
            else raidStat(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="site", subCommand=false)
    public static void site(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://wfraid.teamfr.net/").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="sortie", subCommand=false)
    public static void sorties(MessageReceivedEvent event) {
        try {
            sortie(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="steam", subCommand=false)
    public static void steam(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("http://steamcommunity.com/groups/wfraid").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="syndicat", subCommand=false)
    public static void syndicats(MessageReceivedEvent event) {
        try {
            syndicat(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="ts", subCommand=false)
    public static void ts(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("mine.ts-devil.eu:8334").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="up", subCommand=false)
    public static void upcoming(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("https://warframe.wikia.com/wiki/Upcoming_Features").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="updates", subCommand=false)
    public static void updateHotfix(MessageReceivedEvent event) {
        try {
            updates(event);
        }
        catch (Exception e) {
                afficheErreur(event, e);
                saveErreur(event, e);
        }
    }

    @Command(name="baro", subCommand=false)
    public static void voidTraders(MessageReceivedEvent event) {
        try {
            baro(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="void", subCommand=false)
    public static void voiD(MessageReceivedEvent event) {
        try {
            voidFissure(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
