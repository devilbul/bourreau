package fr.warframe.devilbul.command.riven;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.riven.TxtToJson.sortie;
import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class StatCommand extends SimpleCommand {

    @SubCommand(name = "stat", commande = "riven")
    @Help(field = "**syntaxe** :      !riven stat <catégorie>\n**effet :**           donne toutes les statistique pour la catégorie <catégorie>", categorie =  Categorie.Riven)
    public static void stat(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder stat = new EmbedBuilder();

            if (commande.contains(" ")) {
                String stats;
                String categorie = recupString(commande);

                if (categorie.contains(" ")) categorie = categorie.split(" ")[0];

                if (recupString(commande).contains(" ")) {
                    if (recupString(recupString(commande)).equals("detail")) {
                        if (findJsonKey(rivenJson, categorie)) {
                            for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONArray("upgrades").length(); i++) {
                                stats = "";

                                stats += "peut-être un buff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be buff");
                                stats += "\npeut-être un debuff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be curse");
                                stats += "\nrareté : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("rarity");
                                stats += "\nprefix : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("prefix");
                                stats += "\nsuffix : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("suffix");
                                stats += "\ntype d'opération : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("operation type");

                                if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("|")) {
                                    String[] str = rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("[|]");

                                    stat.addField("- " + str[0] + str[2], stats, false);
                                } else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                                else
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                            }

                            stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                            stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            stat.setDescription("Riven : stat");
                            stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            stat.setColor(new Color(155, 55, 255));
                            stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                            event.getTextChannel().sendMessage(stat.build()).queue();
                        } else
                            event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                    } else
                        event.getTextChannel().sendMessage("essaye plutôt avec \"detail\"").queue();
                } else {
                    if (findJsonKey(rivenJson, categorie)) {
                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONArray("upgrades").length(); i++) {
                            stats = "";

                            stats += "peut-être un buff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be buff");
                            stats += "\npeut-être un debuff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be curse");

                            if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("|")) {
                                String[] str = rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("[|]");

                                stat.addField("- " + str[0] + str[2], stats, false);
                            } else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                            else
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                        }

                        stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                        stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        stat.setDescription("Riven : stat");
                        stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        stat.setColor(new Color(155, 55, 255));
                        stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                        event.getTextChannel().sendMessage(stat.build()).queue();
                    } else
                        event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                }
            } else {
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                stat.setTitle("Stat : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                stat.setDescription("Riven : stat");
                stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                stat.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
                stat.addField("afin d'afficher tous les statistique possibles pour cette catégorie d'objet", "", true);
                stat.addField("**__!riven stat <categorie>__**", "", true);
                stat.setColor(new Color(155, 55, 255));
                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                event.getTextChannel().sendMessage(stat.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

}
