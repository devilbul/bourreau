package fr.warframe.devilbul.command.help;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Bourreau.helpDetail;
import static fr.warframe.devilbul.Bourreau.helpList;
import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Find.findModo;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.access.CanGet.canGetHelpDetail;
import static fr.warframe.devilbul.utils.find.FindHelpCommand.getCategorieCommand;
import static fr.warframe.devilbul.utils.find.FindHelpCommand.isCommandInHelpDetail;

public class HelpCommand extends SimpleCommand {

    @Command(name = "help")
    @Help(field = "**syntaxe** :    !help\n**effet :**         affiche toutes les commandes", categorie = Categorie.Help)
    public static void help(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if (isCommandInHelpDetail(commande)) {
                    if (canGetHelpDetail(event, getCategorieCommand(commande))) {
                        EmbedBuilder helpEmbed = new EmbedBuilder();

                        helpEmbed.addField("__Aide pour__ :   " + commande, helpDetail.get(commande), false);
                        helpEmbed.setTitle("Bourreau : Aide Commande", null);
                        helpEmbed.setDescription("information sur la commande");
                        helpEmbed.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
                        helpEmbed.setColor(new Color(70, 70, 255));
                        helpEmbed.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                        event.getTextChannel().sendMessage(helpEmbed.build()).queue();
                    } else
                        event.getTextChannel().sendMessage("Commande " + event.getMessage().getContentDisplay() + " invalide.").queue();
                } else
                    event.getTextChannel().sendMessage("Commande " + event.getMessage().getContentDisplay() + " invalide.").queue();
            } else {
                String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
                JSONArray configCommandJson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes");
                EmbedBuilder helpEmbed = new EmbedBuilder();
                EmbedBuilder helpEmbedPrive = new EmbedBuilder();
                boolean isEmpty, canSendPriv = false;

                for (String key : helpList.keySet()) {
                    StringBuilder list = new StringBuilder();
                    isEmpty = true;

                    if (!helpList.get(key).isEmpty()) {
                        if (!key.equals(Categorie.Admin.toString()) && !key.equals(Categorie.Modo.toString()) && !key.equals(Categorie.Supreme.toString())) {
                            for (String value : helpList.get(key)) {
                                if (configCommandJson.toList().contains(value)) {
                                    list.append("\n•  !").append(value);
                                    isEmpty = false;
                                }
                            }

                            if (!isEmpty)
                                helpEmbed.addField("**" + key + " :**", list.toString(), true);
                        }

                        if (key.equals(Categorie.Supreme.toString()) && findAdminSupreme(event.getMember().getUser().getId())) {
                            for (String value : helpList.get(key))
                                if (configCommandJson.toList().contains(value)) {
                                    list.append("\n•  !").append(value);
                                    isEmpty = false;
                                }

                            if (!isEmpty) {
                                helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
                                canSendPriv = true;
                            }
                        }

                        if (key.equals(Categorie.Admin.toString()) && findAdmin(event, event.getMember())) {
                            for (String value : helpList.get(key))
                                if (configCommandJson.toList().contains(value) || value.startsWith("config")) {
                                    list.append("\n•  !").append(value);
                                    isEmpty = false;
                                }

                            if (!isEmpty) {
                                helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
                                canSendPriv = true;
                            }
                        }

                        if (key.equals(Categorie.Modo.toString()) && (findModo(event, event.getMember()) || findAdmin(event, event.getMember()))) {
                            for (String value : helpList.get(key))
                                if (configCommandJson.toList().contains(value)) {
                                    list.append("\n•  !").append(value);
                                    isEmpty = false;
                                }

                            if (!isEmpty) {
                                helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
                                canSendPriv = true;
                            }
                        }
                    }
                }

                helpEmbedPrive.setTitle("Bourreau : Aide Commande", null);
                helpEmbedPrive.setDescription("liste des commandes\n`!help <nom_de_la_commande>`  pour plus d'information");
                helpEmbedPrive.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
                helpEmbedPrive.setColor(new Color(70, 70, 255));
                helpEmbedPrive.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                helpEmbed.setTitle("Bourreau : Aide Commande", null);
                helpEmbed.setDescription("liste des commandes\n`!help <nom_de_la_commande>`  pour plus d'information");
                helpEmbed.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
                helpEmbed.setColor(new Color(70, 70, 255));
                helpEmbed.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                if (canSendPriv)
                    if (findModo(event, event.getMember()) || findAdmin(event, event.getMember()) || findAdminSupreme(event.getMember().getUser().getId()))
                        event.getMember().getUser().openPrivateChannel().complete().sendMessage(helpEmbedPrive.build()).queue();

                event.getTextChannel().sendMessage(helpEmbed.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
