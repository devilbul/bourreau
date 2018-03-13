package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Bourreau.commands;
import static fr.warframe.devilbul.command.config.ConfigCommand.checkCommandConfiguration;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.ConfigInfoCommand.messageInfoCommand;
import static fr.warframe.devilbul.utils.Find.findCommandStringList;
import static fr.warframe.devilbul.utils.Find.findIndexStringArray;
import static fr.warframe.devilbul.utils.JSON.jsonArrayDeleteDuplicates;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageCommandCommand extends SimpleCommand {

    @SubCommand(name = "command", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageCommand(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
                JSONObject configCommandJson = new JSONObject(configCommand);
                FileWriter fileCommand = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configCommand.json");
                String[] commandes;
                String[] commandesWithMoreConfig = {"aubucher", "createsalonclan", "deletesalonclan", "tenno"};

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 1) {
                            if (commande.split(" ")[1].equals("all")) {
                                for (String s : commands.keySet()) {
                                    if (findCommandStringList(commandesWithMoreConfig, s))
                                        checkCommandConfiguration(event, configCommandJson, s);
                                    else {
                                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(s);
                                        event.getTextChannel().sendMessage("Commande **" + s + "** ajoutée.").queue();
                                    }
                                }
                            } else {
                                commandes = recupString(commande).split(" ");

                                for (String s : commandes)
                                    if (commands.containsKey(s)) {
                                        if (findCommandStringList(commandesWithMoreConfig, s))
                                            checkCommandConfiguration(event, configCommandJson, s);
                                        else {
                                            configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(s);
                                            event.getTextChannel().sendMessage("Commande **" + s + "** ajoutée.").queue();
                                        }
                                    } else
                                        event.getTextChannel().sendMessage("La commande **" + s + "** n'existe pas.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucune commande saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            commandes = recupString(commande).split(" ");

                            for (String s : commandes)
                                if (commands.containsKey(s)) {
                                    configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").remove(findIndexStringArray(configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes"), s));
                                    event.getTextChannel().sendMessage("Commande **" + s + "** supprimée.").queue();
                                } else
                                    event.getTextChannel().sendMessage("La commande **" + s + "** n'existe pas.").queue();
                        } else
                            event.getTextChannel().sendMessage("Aucune commande saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoCommand()).queue();
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "info"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                jsonArrayDeleteDuplicates(configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes"));
                fileCommand.write(configCommandJson.toString(3));
                fileCommand.flush();
                fileCommand.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
