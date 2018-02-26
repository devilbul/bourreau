package fr.warframe.devilbul.command.help;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
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
import static fr.warframe.devilbul.utils.find.FindHelpCommand.isCommandInHelpDetail;

public class HelpCommand extends SimpleCommand {

    @Command(name = "help")
    @Help(field = "**syntaxe** :    !help\n**effet :**         affiche toutes les commandes", categorie = Categorie.Help)
    public static void help(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if (isCommandInHelpDetail(commande)) {
                    EmbedBuilder helpEmbed = new EmbedBuilder();

                    helpEmbed.addField("__Aide pour__ :   " + commande, helpDetail.get(commande), false);
                    helpEmbed.setTitle("Bourreau : Aide Commande", null);
                    helpEmbed.setDescription("information sur la commande");
                    helpEmbed.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
                    helpEmbed.setColor(new Color(70, 70, 255));
                    helpEmbed.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                    event.getTextChannel().sendMessage(helpEmbed.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("Commande " + event.getMessage().getContentDisplay() + " invalide.").queue();
            } else {
                EmbedBuilder helpEmbed = new EmbedBuilder();
                EmbedBuilder helpEmbedPrive = new EmbedBuilder();

                /**à modifier, pour afficher seulement les commandes accepté par le serveur*/
                for (String key : helpList.keySet()) {
                    StringBuilder list = new StringBuilder();

                    if (!helpList.get(key).isEmpty()) {
                        if (!key.equals(Categorie.Admin.toString()) && !key.equals(Categorie.Modo.toString()) && !key.equals(Categorie.Supreme.toString())) {
                            for (String value : helpList.get(key))
                                list.append("\n•  !").append(value);

                            helpEmbed.addField("**" + key + " :**", list.toString(), true);
                        }

                        if (key.equals(Categorie.Supreme.toString()) && findAdminSupreme(event.getMember().getUser().getId())) {
                            for (String value : helpList.get(key))
                                list.append("\n•  !").append(value);

                            helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
                        }

                        if (key.equals(Categorie.Admin.toString()) && findAdmin(event, event.getMember())) {
                            for (String value : helpList.get(key))
                                list.append("\n•  !").append(value);

                            helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
                        }

                        if (key.equals(Categorie.Modo.toString()) && (findModo(event, event.getMember()) || findAdmin(event, event.getMember()))) {
                            for (String value : helpList.get(key))
                                list.append("\n•  !").append(value);

                            helpEmbedPrive.addField("**" + key + " :**", list.toString(), true);
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
