package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.SubCommand;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.Bourreau.subCommands;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.messsage.MessageOnEvent.*;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.JSON.jsonArrayDeleteDuplicates;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class ConfigCommand extends SimpleCommand {

    @Command(name="config", subCommand=true)
    public static void configurationBotServer(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                if (event.getMember().getPermissions().contains(Permission.ADMINISTRATOR) || findAdminSupreme(event.getAuthor().getId())) {
                    String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "category":
                            manageCategory(event);
                            break;
                        case "command":
                            manageCommand(event);
                            break;
                        case "functionality":
                            manageFunctionality(event);
                            break;
                        case "role":
                            manageRole(event);
                            break;
                        case "textchannel":
                            manageTextChannel(event);
                            break;
                        case "voicechannel":
                            manageVoiceChannel(event);
                            break;
                        case "info":
                            information(event);
                            break;
                        case "emote":
                            if (findAdminSupreme(event.getAuthor().getId())) {
                                manageEmote(event);
                                break;
                            }
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("config").toArray())).queue();
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

    private static void manageEmote(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configEmote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
                JSONObject configEmoteJson = new JSONObject(configEmote);
                FileWriter fileEmote = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configEmote.json");
                Guild guild;
                Emote emote;
                User user;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            if (findGuildJDAList(event.getJDA().getGuilds(), commande.split(" ")[1])) {
                                guild = event.getJDA().getGuildById(commande.split(" ")[1]);

                                if (findEmoteGuildList(guild.getEmotes(), commande.split(" ")[2])) {
                                    emote = guild.getEmoteById(commande.split(" ")[2]);

                                    if (event.getMessage().getMentionedUsers().size() > 0) {
                                        user = event.getMessage().getMentionedUsers().get(0);

                                        if (!findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                            configEmoteJson.getJSONObject("emotes").put(user.getId(), new JSONObject().put("idEmote", emote.getId()).put("nameEmote", emote.getName()).put("nameUser", user.getName()).put("idServer", guild.getId()).put("nameServer", guild.getName()));
                                            event.getTextChannel().sendMessage("Emote : **" + emote.getName() + " " + emote.getAsMention() + "** ajoutée pour l'utilisateur : **" + user.getAsMention() + "**.").queue();
                                        }
                                        else
                                            event.getTextChannel().sendMessage("Il y a déjà une emote associé à cet utilisateur").queue();
                                    }
                                    else
                                        event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();
                                }
                                else
                                    event.getTextChannel().sendMessage("Le serveur " + guild.getName() + " n'a pas cet emote (" + commande.split(" ")[2] + ").").queue();
                            }
                            else
                                event.getTextChannel().sendMessage("Le bot n'est pas sur ce serveur (" + commande.split(" ")[1] + ").").queue();
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune emote saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun serveur saisie.").queue();

                        break;
                    case "remove":
                        if (event.getMessage().getMentionedUsers().size() > 0) {
                            user = event.getMessage().getMentionedUsers().get(0);

                            if (findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                configEmoteJson.getJSONObject("emotes").remove(user.getId());
                                event.getTextChannel().sendMessage("L'utilisateur : " + user.getAsMention() + "n'a plus d'emote associé.").queue();
                            }
                            else
                                event.getTextChannel().sendMessage("Il n'y a déjà plus emote associé à cet utilisateur").queue();
                        }
                        else
                            event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            if (findGuildJDAList(event.getJDA().getGuilds(), commande.split(" ")[1])) {
                                guild = event.getJDA().getGuildById(commande.split(" ")[1]);

                                if (findEmoteGuildList(guild.getEmotes(), commande.split(" ")[2])) {
                                    emote = guild.getEmoteById(commande.split(" ")[2]);

                                    if (event.getMessage().getMentionedUsers().size() > 0) {
                                        user = event.getMessage().getMentionedUsers().get(0);

                                        if (!findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                            configEmoteJson.getJSONObject("emotes").getJSONObject(user.getId())
                                                    .put("nameEmote", emote.getName())
                                                    .put("nameServer", guild.getName())
                                                    .put("idServer", guild.getId())
                                                    .put("idServer", emote.getId())
                                            ;
                                            event.getTextChannel().sendMessage("Emote : **" + emote.getName() + " " + emote.getAsMention() + "** ajoutée pour l'utilisateur : **" + user.getAsMention() + "**.").queue();
                                        }
                                        else
                                            event.getTextChannel().sendMessage("Il y a déjà une emote associé à cet utilisateur").queue();
                                    }
                                    else
                                        event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();
                                }
                                else
                                    event.getTextChannel().sendMessage("Le serveur " + guild.getName() + " n'a pas cet emote (" + commande.split(" ")[2] + ").").queue();
                            }
                            else
                                event.getTextChannel().sendMessage("Le bot n'est pas sur ce serveur (" + commande.split(" ")[1] + ").").queue();
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune emote saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun serveur saisie.").queue();

                        break;

                    case "getall":
                        if (commande.contains(" "))
                            guild = event.getJDA().getGuildById(commande.split(" ")[1]);
                        else
                            guild = event.getGuild();

                        for (Emote emote1 : guild.getEmotes())
                            event.getTextChannel().sendMessage(emote1.getName() + " :\n    id : " + emote1.getId() + "\n    emote : " + emote1.getAsMention()).queue();

                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "modify", "getall"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                fileEmote.write(configEmoteJson.toString(3));
                fileEmote.flush();
                fileEmote.close();
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="info")
    private static void information(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage(messageInfoConfiguration()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="category")
    private static void manageCategory(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String categoryJson;
                String configCategory = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCategory.json")));
                JSONObject configCategoryJson = new JSONObject(configCategory);
                FileWriter fileCategory = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCategory.json");
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
                                }
                                else
                                    event.getTextChannel().sendMessage("Cette categorie, **" + categoryJson + "**, est déjà configurée.").queue();
                            }
                            else
                                event.getTextChannel().sendMessage("**" + categoryJson + "** n'est pas une catagorie utile.").queue();
                        }
                        else if (commande.split(" ").length == 2)
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
                        }
                        else
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
                        }
                        else if (commande.split(" ").length == 2)
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
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="command")
    private static void manageCommand(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
                JSONObject configCommandJson = new JSONObject(configCommand);
                FileWriter fileCommand = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configCommand.json");
                String[] commandes;
                String[] commandesWithMoreConfig = {"aubucher", "createsalonclan", "deletesalonclan", "tenno"};

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 1) {
                            commandes = recupString(commande).split(" ");

                            for (String s : commandes)
                                if (commands.containsKey(s)) {
                                    if (findCommandStringList(commandesWithMoreConfig, s))
                                        checkCommandConfiguration(event, configCommandJson, s);
                                    else {
                                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(s);
                                        event.getTextChannel().sendMessage("Commande **" + s + "** ajoutée.").queue();
                                    }
                                }
                                else
                                    event.getTextChannel().sendMessage("La commande **" + s + "** n'existe pas.").queue();
                        }
                        else
                            event.getTextChannel().sendMessage("Aucune commande saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            commandes = recupString(commande).split(" ");

                            for (String s : commandes)
                                if (commands.containsKey(s)) {
                                    configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").remove(findIndexStringArray(configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes"), s));
                                    event.getTextChannel().sendMessage("Commande **" + s + "** supprimée.").queue();
                                }
                                else
                                    event.getTextChannel().sendMessage("La commande **" + s + "** n'existe pas.").queue();
                        }
                        else
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
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="functionality")
    private static void manageFunctionality(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configFunctionality = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configfunctionality.json")));
                JSONObject configFunctionalityJson = new JSONObject(configFunctionality);
                FileWriter filefunctionality = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configfunctionality.json");
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
                        }
                        else
                            event.getTextChannel().sendMessage("Aucune fonctionnalité saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            functionalities = recupString(commande).split(" ");

                            for (String s : functionalities) {
                                configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites").remove(findIndexStringArray(configFunctionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites"), s));
                                event.getTextChannel().sendMessage("Fonctionnalité **" + s + "** supprimée.").queue();
                            }
                        }
                        else
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
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="role")
    private static void manageRole(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String roleJson;
                String configRole = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
                JSONObject configRoleJson = new JSONObject(configRole);
                FileWriter fileRole = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configRole.json");
                Role role;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            roleJson = commande.split(" ")[1];

                            if (event.getMessage().getMentionedRoles().size() > 0)
                                role = event.getMessage().getMentionedRoles().get(0);
                            else
                                role = findRole(event.getGuild(), commande.split(" ")[2]);

                            if (findValueObjectList(configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").names().toList(), roleJson) && role != null) {
                                if (configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).getString("idRole").isEmpty()) {
                                    configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("nameRole", role.getName());
                                    configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("idRole", role.getId());

                                    if (role.isMentionable())
                                        event.getTextChannel().sendMessage("Rôle **" + roleJson + "** ajouté (avec " + role.getAsMention() + ").").queue();
                                    else
                                        event.getTextChannel().sendMessage("Rôle **" + roleJson + "** ajouté (avec " + role.getName() + ").").queue();
                                }
                                else
                                    event.getTextChannel().sendMessage("Ce rôle, **" + roleJson + "**, est déjà configuré.").queue();
                            }
                            else
                                event.getTextChannel().sendMessage("**" + roleJson + "** n'est pas un rôle utile.").queue();
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun role saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de role saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            roleJson = commande.split(" ")[1];

                            if (findValueObjectList(configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").names().toList(), roleJson)) {
                                if (!configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).getString("idRole").isEmpty()) {
                                    configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("nameRole", "");
                                    configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("idRole", "");
                                    event.getTextChannel().sendMessage("Rôle **" + roleJson + "** supprimé.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce rôle, **" + roleJson + "**, est vide.").queue();
                            }
                        }
                        else
                            event.getTextChannel().sendMessage("Aucun type de role saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            roleJson = commande.split(" ")[1];

                            if (event.getMessage().getMentionedRoles().size() > 0)
                                role = event.getMessage().getMentionedRoles().get(0);
                            else
                                role = findRole(event.getGuild(), commande.split(" ")[2]);

                            if (findValueObjectList(configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").names().toList(), roleJson) && role != null) {
                                configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("nameRole", role.getName());
                                configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject(roleJson).put("idRole", role.getId());

                                if (role.isMentionable())
                                    event.getTextChannel().sendMessage("Rôle **" + roleJson + "** modifiée (avec " + role.getAsMention() + ").").queue();
                                else
                                    event.getTextChannel().sendMessage("Rôle **" + roleJson + "** modifiée (avec " + role.getName() + ").").queue();
                            }
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun role saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de role saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoRole()).queue();
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

                fileRole.write(configRoleJson.toString(3));
                fileRole.flush();
                fileRole.close();
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="textchannel")
    private static void manageTextChannel(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String textChannelJson;
                String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
                JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                FileWriter fileTextChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configtextChannel.json");
                TextChannel textChannel;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            textChannelJson = commande.split(" ")[1];
                            textChannel = event.getMessage().getMentionedChannels().get(0);

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson) && textChannel != null) {
                                if (configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).getString("idTextChannel").isEmpty()) {
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", textChannel.getName());
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", textChannel.getId());
                                    event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** ajouté (avec " + textChannel.getAsMention() + ").").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon textuel, **" + textChannelJson + "**, est déjà configuré.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + textChannelJson + "** n'est pas un salon textuel utile.").queue();
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon textuel saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            textChannelJson = commande.split(" ")[1];

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson)) {
                                if (!configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).getString("idTextChannel").isEmpty()) {
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", "");
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", "");
                                    event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** supprimé.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon textuel, **" + textChannelJson + "**, est vide.").queue();
                            }
                        }
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            textChannelJson = commande.split(" ")[1];
                            textChannel = event.getMessage().getMentionedChannels().get(0);

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson) && textChannel != null) {
                                configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", textChannel.getName());
                                configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", textChannel.getId());
                                event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** modifiée (avec " + textChannel.getAsMention() + ").").queue();
                            }
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon textuel saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoTextChannel()).queue();
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

                fileTextChannel.write(configTextChannelJson.toString(3));
                fileTextChannel.flush();
                fileTextChannel.close();
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="voicechannel")
    private static void manageVoiceChannel(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String voiceChannelJson;
                String configVoiceChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
                JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);
                FileWriter fileVoiceChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "configVoiceChannel.json");
                VoiceChannel voiceChannel;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            voiceChannelJson = commande.split(" ")[1];
                            voiceChannel = findVoiceChannel(event.getGuild(), commande.split(" : ")[1]);

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson) && voiceChannel != null) {
                                System.out.println(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).toString());
                                if (configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).getString("idVoiceChannel").isEmpty()) {
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", voiceChannel.getName());
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", voiceChannel.getId());
                                    event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** ajouté (avec " + voiceChannel.getName() + ").").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon vocal, **" + voiceChannelJson + "**, est déjà configuré.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + voiceChannelJson + "** n'est pas un salon vocal utile.").queue();
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon vocal saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            voiceChannelJson = commande.split(" ")[1];

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson)) {
                                if (!configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).getString("idVoiceChannel").isEmpty()) {
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", "");
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", "");
                                    event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** supprimé.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon vocal, **" + voiceChannelJson + "**, est vide.").queue();
                            }
                        }
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            voiceChannelJson = commande.split(" ")[1];
                            voiceChannel = findVoiceChannel(event.getGuild(), commande.split(" : ")[1]);

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson) && voiceChannel != null) {
                                configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", voiceChannel.getName());
                                configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", voiceChannel.getId());
                                event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** modifiée (avec " + voiceChannel.getName() + ").").queue();
                            }
                        }
                        else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon vocal saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoVoiceChannel()).queue();
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

                fileVoiceChannel.write(configVoiceChannelJson.toString(3));
                fileVoiceChannel.flush();
                fileVoiceChannel.close();
            }
            else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void checkCommandConfiguration(MessageReceivedEvent event, JSONObject configCommandJson, String commande) {
        try {
            String configCategory = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCategory.json")));
            String configRole = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
            String configVoiceChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
            JSONObject configCategoryJson = new JSONObject(configCategory);
            JSONObject configRoleJson = new JSONObject(configRole);
            JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);

            switch (commande) {
                case "aubucher":
                    if (!configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject("bucher").getString("idVoiceChannel").isEmpty()
                            && !configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("bucher").getString("idRole").isEmpty()) {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin du rôle : **tenno** et du salon vocal : **bucher**.").queue();

                    break;
                case "createsalonclan":
                    if (configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject("clan").getString("idCategory").isEmpty())
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin de renseigner une categorie pour le salon.\nPour cela tapez : **__!config category add clan <nom de la categorie>__**.").queue();
                    else {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put("deletesalonclan");
                        event.getTextChannel().sendMessage("Commande **deletesalonclan** ajoutée.").queue();
                    }
                    break;
                case "deletesalonclan":
                    if (configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").toString().contains("createsalonclan")) {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin de la commande : **createsalonclan**.").queue();
                    break;
                case "tenno":
                    if (!configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole").isEmpty()) {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin du rôle : **tenno**.").queue();

                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void checkFunctionalityConfiguration(MessageReceivedEvent event, JSONObject configFoucntionalityJson, String commande) {
        try {
            String configRole = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRoleJson = new JSONObject(configRole);

            switch (commande) {
                case "presentation":
                    if (!configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole").isEmpty()) {
                        configFoucntionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites").put(commande);
                        event.getTextChannel().sendMessage("Fonctionnalité **" + commande + "** ajoutée.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Cette fonctionnalité, **" + commande + "**, a besoin du rôle : **tenno**.").queue();

                    break;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
