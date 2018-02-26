package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.*;
import static fr.warframe.devilbul.message.event.ConfigInfoCategory.messageInfoCategory;
import static fr.warframe.devilbul.utils.Find.findCategory;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageCategoryCommand extends SimpleCommand {

    @SubCommand(name = "category", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageCategory(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String categoryJson;
                String configCategory = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCategory.json")));
                JSONObject configCategoryJson = new JSONObject(configCategory);
                FileWriter fileCategory = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configCategory.json");
                Category category;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            categoryJson = commande.split(" ")[1];
                            category = findCategory(event.getGuild(), commande.split(" ")[2]);

                            if (findValueObjectList(configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").names().toList(), categoryJson) && category != null) {
                                if (configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).getString("idCategory").isEmpty()) {
                                    configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("nameCategory", category.getName());
                                    configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("idCategory", category.getId());
                                    event.getTextChannel().sendMessage("Categorie **" + categoryJson + "** ajoutée (avec " + category.getName() + ").").queue();
                                } else
                                    event.getTextChannel().sendMessage("Cette categorie, **" + categoryJson + "**, est déjà configurée.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + categoryJson + "** n'est pas une catagorie utile.").queue();
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune categorie saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de categorie saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            categoryJson = commande.split(" ")[1];

                            if (findValueObjectList(configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").names().toList(), categoryJson)) {
                                if (!configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).getString("idCategory").isEmpty()) {
                                    configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("nameCategory", "");
                                    configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("idCategory", "");
                                    event.getTextChannel().sendMessage("Categorie **" + categoryJson + "** supprimée.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Cette categorie, **" + categoryJson + "**, est vide.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucun type de categorie saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            categoryJson = commande.split(" ")[1];
                            category = findCategory(event.getGuild(), commande.split(" ")[2]);

                            if (findValueObjectList(configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").names().toList(), categoryJson) && category != null) {
                                configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("nameCategory", category.getName());
                                configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject(categoryJson).put("idCategory", category.getId());
                                event.getTextChannel().sendMessage("Commande **" + categoryJson + "** modifiée (avec " + category.getName() + ").").queue();
                            }
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune categorie saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de categorie saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoCategory()).queue();
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "modify", "info"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                fileCategory.write(configCategoryJson.toString(3));
                fileCategory.flush();
                fileCategory.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
