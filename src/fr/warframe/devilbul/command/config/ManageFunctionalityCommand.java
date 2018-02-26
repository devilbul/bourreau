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

import static fr.warframe.devilbul.command.config.ConfigCommand.checkFunctionalityConfiguration;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.ConfigInfoFunctionality.messageInfoFunctionality;
import static fr.warframe.devilbul.utils.Find.findCommandStringList;
import static fr.warframe.devilbul.utils.Find.findIndexStringArray;
import static fr.warframe.devilbul.utils.JSON.jsonArrayDeleteDuplicates;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageFunctionalityCommand extends SimpleCommand {

    @SubCommand(name = "functionality", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageFunctionality(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configFunctionality = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
                JSONObject configFunctionalityJson = new JSONObject(configFunctionality);
                FileWriter filefunctionality = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configFunctionality.json");
                String[] functionalities;
                String[] functionalitiesWithMoreConfig = {"presentation"};

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 1) {
                            functionalities = recupString(commande).split(" ");

                            for (String s : functionalities)
                                if (findCommandStringList(functionalitiesWithMoreConfig, s))
                                    checkFunctionalityConfiguration(event, configFunctionalityJson, s);
                                else {
                                    configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites").put(s);
                                    event.getTextChannel().sendMessage("Fonctionnalité **" + s + "** ajoutée.").queue();
                                }
                        } else
                            event.getTextChannel().sendMessage("Aucune fonctionnalité saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            functionalities = recupString(commande).split(" ");

                            for (String s : functionalities) {
                                configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites").remove(findIndexStringArray(configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites"), s));
                                event.getTextChannel().sendMessage("Fonctionnalité **" + s + "** supprimée.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucune fonctionnalité saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoFunctionality()).queue();
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

                jsonArrayDeleteDuplicates(configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites"));
                filefunctionality.write(configFunctionalityJson.toString(3));
                filefunctionality.flush();
                filefunctionality.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
