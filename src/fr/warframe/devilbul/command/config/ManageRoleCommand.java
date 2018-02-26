package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.ConfigInfoRole.messageInfoRole;
import static fr.warframe.devilbul.utils.Find.findRole;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageRoleCommand extends SimpleCommand {

    @SubCommand(name = "role", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageRole(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String roleJson;
                String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
                JSONObject configRoleJson = new JSONObject(configRole);
                FileWriter fileRole = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configRole.json");
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
                                } else
                                    event.getTextChannel().sendMessage("Ce rôle, **" + roleJson + "**, est déjà configuré.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + roleJson + "** n'est pas un rôle utile.").queue();
                        } else if (commande.split(" ").length == 2)
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
                        } else
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
                        } else if (commande.split(" ").length == 2)
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
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
