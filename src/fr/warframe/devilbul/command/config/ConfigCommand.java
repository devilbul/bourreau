package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Bourreau.subCommands;
import static fr.warframe.devilbul.command.config.ConfigInformation.information;
import static fr.warframe.devilbul.command.config.ManageCategoryCommand.manageCategory;
import static fr.warframe.devilbul.command.config.ManageCommandCommand.manageCommand;
import static fr.warframe.devilbul.command.config.ManageEmoteCommand.manageEmote;
import static fr.warframe.devilbul.command.config.ManageFunctionalityCommand.manageFunctionality;
import static fr.warframe.devilbul.command.config.ManageRoleCommand.manageRole;
import static fr.warframe.devilbul.command.config.ManageTextChannelCommand.manageTextChannel;
import static fr.warframe.devilbul.command.config.ManageVoiceChannelCommand.manageVoiceChannel;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ConfigCommand extends SimpleCommand {

    @Command(name = "config", subCommand = true)
    @Help(field = "", categorie = Categorie.Admin)
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
                } else
                    event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
            } else {
                MessageBuilder message = new MessageBuilder();

                event.getTextChannel().sendMessage("Aucune action de configuration saisie").queue();

                message.append("You know nothing, ");
                message.append(event.getAuthor());

                event.getTextChannel().sendMessage(message.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    public static void checkCommandConfiguration(MessageReceivedEvent event, JSONObject configCommandJson, String commande) {
        try {
            String configCategory = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCategory.json")));
            String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            String configVoiceChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
            JSONObject configCategoryJson = new JSONObject(configCategory);
            JSONObject configRoleJson = new JSONObject(configRole);
            JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);

            switch (commande) {
                case "aubucher":
                    if (!configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject("bucher").getString("idVoiceChannel").isEmpty()
                            && !configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("bucher").getString("idRole").isEmpty()) {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                    } else
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
                    } else
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin de la commande : **createsalonclan**.").queue();
                    break;
                case "tenno":
                    if (!configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole").isEmpty()) {
                        configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes").put(commande);
                        event.getTextChannel().sendMessage("Commande **" + commande + "** ajoutée.").queue();
                    } else
                        event.getTextChannel().sendMessage("Cette commande, **" + commande + "**, a besoin du rôle : **tenno**.").queue();

                    break;
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    public static void checkFunctionalityConfiguration(MessageReceivedEvent event, JSONObject configFoucntionalityJson, String commande) {
        try {
            String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRoleJson = new JSONObject(configRole);

            switch (commande) {
                case "presentation":
                    if (!configRoleJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole").isEmpty()) {
                        configFoucntionalityJson.getJSONObject("fonctionnalites").getJSONObject(event.getGuild().getId()).getJSONArray("fonctionnalites").put(commande);
                        event.getTextChannel().sendMessage("Fonctionnalité **" + commande + "** ajoutée.").queue();
                    } else
                        event.getTextChannel().sendMessage("Cette fonctionnalité, **" + commande + "**, a besoin du rôle : **tenno**.").queue();

                    break;
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
