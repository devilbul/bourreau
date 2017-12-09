package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.messsage.MessageOnEvent.*;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class ConfigCommand extends SimpleCommand {

    @Command(name="config")
    public static void ConfigurationBotServer(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContent().contains(" ")) {
                if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR)) {
                    String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "category":
                            ManageCategory(event);
                            break;
                        case "command":
                            ManageCommand(event);
                            break;
                        case "role":
                            ManageRole(event);
                            break;
                        case "textChannel":
                            ManageTextChannel(event);
                            break;
                        case "voiceChannel":
                            ManageVoiceChannel(event);
                            break;
                        case "info":
                            Information(event);
                            break;
                    default:
                        MessageBuilder message = new MessageBuilder();
                        String[] commandeSondage = {"category", "command", "role", "textChannel", "voiceChannel"};

                        event.getTextChannel().sendMessage(CompareCommande(commande, commandeSondage)).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                    }
                }
                else
                    event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
            }
            else {
                MessageBuilder message = new MessageBuilder();

                event.getTextChannel().sendMessage("Aucune action de configuration saisie").queue();

                message.append("You know nothing, ");
                message.append(event.getAuthor());

                event.getTextChannel().sendMessage(message.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Information(MessageReceivedEvent event) {
        try {
            MessageInfoConfiguration(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ManageCategory(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent());
            String configCategory = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCategory.json")));
            JSONObject configCategoryJson = new JSONObject(configCategory);
            FileWriter fileCategory = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCategory.json");

            switch (commande.split("@")[0]) {
                case "add":

                    break;
                case "remove":

                    break;
                case "info":
                    event.getTextChannel().sendMessage(MessageInfoCategory(event)).queue();
                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ManageCommand(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent());
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONObject configCommandJson = new JSONObject(configCommand);
            FileWriter fileCommand = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCommand.json");

            switch (commande.split("@")[0]) {
                case "add":

                    break;
                case "remove":

                    break;
                case "info":
                    event.getTextChannel().sendMessage(MessageInfoCommand(event)).queue();
                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ManageRole(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent());
            String configRole = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRoleJson = new JSONObject(configRole);
            FileWriter fileRole = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configRole.json");

            switch (commande.split("@")[0]) {
                case "add":

                    break;
                case "remove":

                    break;
                case "info":
                    event.getTextChannel().sendMessage(MessageInfoRole(event)).queue();
                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ManageTextChannel(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent());
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            FileWriter fileTextChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configtextChannel.json");

            switch (commande.split("@")[0]) {
                case "add":

                    break;
                case "remove":

                    break;
                case "info":
                    event.getTextChannel().sendMessage(MessageInfoTextChannel(event)).queue();
                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ManageVoiceChannel(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent());
            String configVoiceChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
            JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);
            FileWriter fileVoiceChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configVoiceChannel.json");

            switch (commande.split("@")[0]) {
                case "add":

                    break;
                case "remove":

                    break;
                case "info":
                    event.getTextChannel().sendMessage(MessageInfoVoiceChannel(event)).queue();
                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
