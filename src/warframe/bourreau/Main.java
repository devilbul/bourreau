package warframe.bourreau;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONException;
import org.json.JSONObject;
import warframe.bourreau.commands.Command;
import warframe.bourreau.listener.BotListener;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.parser.CommandParserPrivate;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import static warframe.bourreau.InitID.InitIDMain;
import static warframe.bourreau.timer.LanceTimer.LanceRaidTimer;

public class Main {

    public static final float DEFAULT_VOLUME = 0.05f;
    protected static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static  final CommandParserPrivate parserPrivate = new CommandParserPrivate();
    public static HashMap<String, Command> commands = new HashMap<>();
    public static final long VINGT_QUATRE_HEURES = 1000 * 60 * 60 * 24;
    public static String botVersion;
    public static String game;

    public static void main(String[] args) {
        try {
            String config = new String(Files.readAllBytes(Paths.get("config" + File.separator + "bot.json")));
            JSONObject configJson = new JSONObject(config);
            String botToken = configJson.getString("botToken");
            game = configJson.getString("game");
            botVersion = configJson.getString("botVersion");
            //String clientID = configJson.getString("clientID");

            jda = new JDABuilder(AccountType.BOT).addEventListener(new BotListener()).setGame(Game.of(game)).setToken(botToken).setBulkDeleteSplittingEnabled(false).buildBlocking();
            jda.setAutoReconnect(true);
        }
        catch (IllegalArgumentException e) {
            System.out.println("The config was not populated. Please provide a token.");
        }
        catch (LoginException e) {
            System.out.println("The provided botToken was incorrect. Please provide valid details.");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("Encountered a JSON error. Most likely caused due to an outdated or ill-formated config.\n" +
                "Please delete the config so that it can be regenerated. JSON Error:\n");
            e.printStackTrace();
        }
        catch (IOException e) {
            JSONObject obj = new JSONObject();
            obj.put("botToken", "");
            try {
                Files.write(Paths.get("Config.json"), obj.toString(4).getBytes());
                System.out.println("No config file was found. Config.json has been generated, please populate it!");
            }
            catch (IOException e1) {
                System.out.println("No config file was found and we failed to generate one.");
                e1.printStackTrace();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue, mauvais token ou problème de connection.");
        }

        InitIDMain(jda);
        //MessageDeConnection(jda);
        //LanceRaidTimer(jda);

        // liste des commandes
        // commande help
        commands.put("help" , new Command());

        // command son
        commands.put("ah" , new Command());
        commands.put("bucher" , new Command());
        commands.put("gg" , new Command());
        commands.put("gogole" , new Command());
        commands.put("leave" , new Command());
        commands.put("nah" , new Command());
        commands.put("pigeon" , new Command());
        commands.put("son", new Command());
        commands.put("souffrir" , new Command());
        commands.put("trump" , new Command());
        commands.put("trumpcomp" , new Command());
        commands.put("trumpcomp2" , new Command());
        commands.put("trumpcomp3" , new Command());

        // commande troll
        commands.put("pute" , new Command());
        commands.put("RIP" , new Command());
        commands.put("rip" , new Command());
        commands.put("segpa" , new Command());
        commands.put("tg" , new Command());

        // commande info
        commands.put("alerts", new Command());
        commands.put("alliance", new Command());
        commands.put("baro", new Command());
        commands.put("clan" , new Command());
        commands.put("discordwf", new Command());
        commands.put("goals", new Command());
        commands.put("idée" , new Command());
        commands.put("idee" , new Command());
        commands.put("info", new Command());
        commands.put("invasions", new Command());
        commands.put("invite" , new Command());
        commands.put("lead" , new Command());
        commands.put("progres" , new Command());
        commands.put("pvp" , new Command());
        commands.put("raid" , new Command());
        commands.put("regle" , new Command());
        commands.put("site" , new Command());
        commands.put("sortie" , new Command());
        commands.put("steam" , new Command());
        commands.put("syndicat" , new Command());
        commands.put("ts3", new Command());
        commands.put("up", new Command());
        commands.put("updates" , new Command());
        commands.put("void" , new Command());

        // commande riven
        commands.put("riven" , new Command());

        // commande sondage
        commands.put("sondage", new Command());

        // commande raid
        commands.put("affiche", new Command());
        commands.put("cancel", new Command());
        commands.put("present", new Command());

        // commande admin / modo
        commands.put("addclan", new Command());
        commands.put("addurl", new Command());
        commands.put("aubucher", new Command());
        commands.put("ban", new Command());
        commands.put("createsalonclan", new Command());
        commands.put("deafen", new Command());
        commands.put("deletesalonclan", new Command());
        commands.put("kick", new Command());
        commands.put("mute", new Command());
        commands.put("ping", new Command());
        commands.put("removeclan", new Command());
        commands.put("setgame", new Command());
        commands.put("tenno", new Command());
        commands.put("unban", new Command());
        commands.put("undeafen", new Command());
        commands.put("unmute", new Command());

        // commande privé
        commands.put("candidate", new Command());
        commands.put("claim", new Command());

        // commande erreur
        commands.put("erreur", new Command());

        // commande test
        commands.put("test", new Command());

        // commande bot
        commands.put("about", new Command());
        commands.put("botnews", new Command());

        // commande arrêt
        commands.put("shutdown", new Command());
    }

    public static JDA getJda() { return jda; }
}
