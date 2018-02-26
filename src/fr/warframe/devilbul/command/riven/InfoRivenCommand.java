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

public class InfoRivenCommand extends SimpleCommand {

    @SubCommand(name = "info", commande = "riven")
    @Help(field = "**syntaxe** :      !riven info <catégorie>\n**effet :**           donne toutes les informations sur la catégorie <catégorie>",categorie = Categorie.Riven)
    public static void info(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder info = new EmbedBuilder();
            StringBuilder polarity = new StringBuilder();
            StringBuilder curseAttenuation = new StringBuilder();
            StringBuilder buffAttenuation = new StringBuilder();
            String curseNumber = "";
            String buffNumber = "";

            if (commande.contains(" ")) {
                String categorie = recupString(commande);

                if (findJsonKey(rivenJson, categorie)) {
                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").length(); i++) {
                        String str = rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").getString(i);

                        switch (str) {
                            case "ap_attack":
                                polarity.append("- madurai\n");
                                break;
                            case "ap_defense":
                                polarity.append("- vazarin\n");
                                break;
                            case "ap_tactic":
                                polarity.append("- naramon\n");
                                break;
                            case "ap_1":
                                polarity.append("- zenurik\n");
                                break;
                            case "ap_2":
                                polarity.append("- penjaga\n");
                                break;
                            case "ap_3":
                                polarity.append("- unairu\n");
                                break;
                            default:
                                polarity.append("- **à ajouter : ").append(str).append("**\n");
                                break;
                        }
                    }

                    info.addField("polarité : ", polarity.toString(), false);

                    info.addField("max rank limite : ", "8", false);

                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(0) + " - ";
                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(1);

                    info.addField("nombre de buff : ", buffNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").length(); i++)
                        buffAttenuation.append(rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").getString(i)).append(" / ");

                    buffAttenuation = new StringBuilder(buffAttenuation.substring(0, buffAttenuation.length() - 3));

                    info.addField("facteur de reduction buff : ", buffAttenuation.toString(), false);

                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(0) + " - ";
                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(1);

                    info.addField("nombre de debuff : ", curseNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").length(); i++)
                        curseAttenuation.append(rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").getString(i)).append(" / ");

                    curseAttenuation = new StringBuilder(curseAttenuation.substring(0, curseAttenuation.length() - 3));

                    info.addField("facteur de reduction debuff : ", curseAttenuation.toString(), false);

                    info.setTitle("Info pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                    info.setDescription("Riven : info");
                    info.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                    info.setColor(new Color(155, 55, 255));
                    info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
                }
                else
                    event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
            }
            else {
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                info.setTitle("Info : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                info.setDescription("Riven : info");
                info.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                info.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
                info.addField("afin d'afficher toutes les informations pour cette catégorie d'objet", "", true);
                info.addField("**__!riven info <categorie>__**", "", true);
                info.setColor(new Color(155, 55, 255));
                info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
            }

            event.getTextChannel().sendMessage(info.build()).queue();

        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
