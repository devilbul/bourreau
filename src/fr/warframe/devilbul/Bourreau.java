package fr.warframe.devilbul;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.listener.bot.event.*;
import fr.warframe.devilbul.parser.CommandParser;
import fr.warframe.devilbul.parser.CommandParserPrivate;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import org.json.JSONException;
import org.json.JSONObject;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Bourreau {

    public static JDA jda;
    public static CommandParser parser = new CommandParser();
    public static CommandParserPrivate parserPrivate = new CommandParserPrivate();
    public static HashMap<String, String> functionalities = new HashMap<>();
    public static HashMap<String, SimpleCommand> commands = new HashMap<>();
    public static HashMap<String, List<String>> subCommands = new HashMap<>();
    public static HashMap<String, List<String>> helpList = new HashMap<>();
    public static HashMap<String, String> helpDetail = new HashMap<>();
    public static String botVersion;
    public static String prefixTag;

    public static void main(String[] args) {
        try {
            String config = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "bot.json")));
            JSONObject configJson = new JSONObject(config);
            String botToken = configJson.getString("botToken");
            String game = configJson.getString("game");
            botVersion = configJson.getString("botVersion");
            prefixTag = configJson.getString("prefixTag");

            jda = new JDABuilder(AccountType.BOT).addEventListener(
                    new MessageReceivedListener(),
                    new PrivateMessageReceivedListener(),
                    new GuildJoinListener(),
                    new GuildMemberJoinListener(),
                    new GuildLeaveListener(),
                    new GuildMemberLeaveListener(),
                    new MessageDeleteListener())
                    .setGame(Game.of(Game.GameType.STREAMING, game, "https://trello.com/b/JEEkreCv/bot-discord-alliance"))
                    .setStatus(OnlineStatus.ONLINE)
                    .setToken(botToken).setBulkDeleteSplittingEnabled(false).buildBlocking();
            jda.setAutoReconnect(true);

            new Init(jda).InitMain();
        } catch (IllegalArgumentException e) {
            System.out.println("The config was not populated. Please provide a token.");
        } catch (LoginException e) {
            System.out.println("The provided botToken was incorrect. Please provide valid details.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            System.err.println("Encountered a JSON error. Most likely caused due to an outdated or ill-formated config.\n" +
                    "Please delete the config so that it can be regenerated. JSON Error:\n");
            e.printStackTrace();
        } catch (IOException e) {
            JSONObject obj = new JSONObject();
            obj.put("botToken", "");
            try {
                Files.write(Paths.get("Config.json"), obj.toString(4).getBytes());
                System.out.println("No config file was found. Config.json has been generated, please populate it!");
            } catch (IOException e1) {
                System.out.println("No config file was found and we failed to generate one.");
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Une erreur est survenue, mauvais token ou problème de connection.");
        }
    }
}
