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

public class DefiCommand extends SimpleCommand {

    @SubCommand(name="defi", commande = "riven")
    @Help(field = "**syntaxe** :      !riven defi\n**effet :**           affiche tous les défis possible", categorie = Categorie.Riven)
    public static void defi(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder defi = new EmbedBuilder();

            if (commande.contains(" ")) {
                String categorie = recupString(commande);
                String numDefi = "";

                if (categorie.contains(" ")) {
                    numDefi = categorie.split(" ")[1];
                    categorie = categorie.split(" ")[0];
                }

                if (findJsonKey(rivenJson, categorie)) {
                    if (numDefi.isEmpty()) {
                        StringBuilder defis = new StringBuilder();

                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length(); i++)
                            defis.append("**").append(i).append("** : ").append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(i)).append("\n");

                        defi.setTitle("Defi pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        defi.setDescription("Riven : defi");
                        defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        defi.addField("résultat :", defis.toString(), true);
                        defi.addField("pour savoir les complications possibles pour un défi", "tapez **__!riven defi " + categorie + " <numéro du défi>__**", true);
                        defi.setColor(new Color(155, 55, 255));
                        defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
                    }
                    else {
                        if (Integer.valueOf(numDefi) < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length()) {
                            StringBuilder complication = new StringBuilder();
                            StringBuilder weight = new StringBuilder();
                            StringBuilder multiplier = new StringBuilder();
                            String[] complications;
                            String[] weights;
                            String[] multipliers;

                            for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().length(); i++) {
                                if (!rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i).equals("complication chance")) {
                                    complication.append("- ").append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)).append("$");
                                    weight.append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("weight")).append("$");
                                    multiplier.append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("multiplier")).append("$");
                                }
                            }

                            complications = complication.toString().split("[$]");
                            weights = weight.toString().split("[$]");
                            multipliers = multiplier.toString().split("[$]");

                            defi.setTitle("Defi n°" + numDefi + " avec complication de la categorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            defi.setDescription("Riven : defi / complications");
                            defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            defi.addField("defi n°" + numDefi + " :", rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)).toString(), false);
                            defi.addField("complication chance", rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).getString("complication chance"), false);
                            for (int i=0; i<complications.length; i++)
                                defi.addField(complications[i], "poids : " + weights[i] + "    /    multiplicateur : " + multipliers[i], false);
                            defi.setColor(new Color(155, 55, 255));
                            defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
                        }
                        else
                            event.getTextChannel().sendMessage("pas de défi associé à ce numéro").queue();
                    }

                    event.getTextChannel().sendMessage(defi.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
            }
            else {
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                defi.setTitle("Defi : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                defi.setDescription("Riven : defi");
                defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                defi.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
                defi.addField("afin d'afficher tous les défis possibles pour cette catégorie d'objet", "", true);
                defi.addField("**__!riven defi <categorie>__**", "", true);
                defi.setColor(new Color(155, 55, 255));
                defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                event.getTextChannel().sendMessage(defi.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
