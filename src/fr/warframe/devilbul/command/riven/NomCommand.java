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
import static fr.warframe.devilbul.utils.Recup.recupString;

public class NomCommand extends SimpleCommand {

    @SubCommand(name = "nom", commande = "riven")
    @Help(field = "**syntaxe** :      !riven nom <nom du riven>\n**effet :**           donne les statistiques par rapport au nom du riven <nom de riven>", categorie = Categorie.Riven)
    public static void nom(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);
                EmbedBuilder nom = new EmbedBuilder();
                StringBuilder cherche = new StringBuilder(recupString(commande));
                String[] stat;
                StringBuilder rawStat = new StringBuilder();
                StringBuilder res = new StringBuilder("statistique :\n");

                for (Object name : rivenJson.names()) {
                    for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").length(); i++) {
                        if (cherche.toString().contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.toString().split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"));
                            cherche = new StringBuilder();

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche.append(aSplit);
                            }

                            rawStat.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade")).append("$");
                        }

                        if (cherche.toString().contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.toString().split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"));
                            cherche = new StringBuilder();

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche.append(aSplit);
                            }

                            rawStat.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade")).append("$");
                        }
                    }
                }

                stat = rawStat.toString().split("[$]");

                for (String aStat : stat) {
                    if (aStat.contains("%"))
                        res.append("- ").append(aStat.split("% ")[1]).append("\n");
                    else
                        res.append("- ").append(aStat.replaceFirst(" ", "%").split("%")[1]).append("\n");
                }

                if (recupString(commande).length() < 6)
                    res.append("");

                if (recupString(commande).length() < 6)
                    nom.setTitle("Statistique : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                else
                    nom.setTitle("Statistique pour le nom de riven : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                nom.addField("résultat :", res.toString(), true);
                nom.setColor(new Color(155, 55, 255));
                nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                if (cherche.toString().contains("a") || cherche.toString().contains("b") || cherche.toString().contains("c") || cherche.toString().contains("d") || cherche.toString().contains("e") || cherche.toString().contains("f") || cherche.toString().contains("g") || cherche.toString().contains("h")
                        || cherche.toString().contains("i") || cherche.toString().contains("j") || cherche.toString().contains("k") || cherche.toString().contains("l") || cherche.toString().contains("m") || cherche.toString().contains("n") || cherche.toString().contains("o") || cherche.toString().contains("p")
                        || cherche.toString().contains("q") || cherche.toString().contains("r") || cherche.toString().contains("s") || cherche.toString().contains("t") || cherche.toString().contains("u") || cherche.toString().contains("v") || cherche.toString().contains("w") || cherche.toString().contains("x")
                        || cherche.toString().contains("y") || cherche.toString().contains("z"))
                    event.getTextChannel().sendMessage("faute d'orthographe sur le nom saisi").queue();
                else
                    event.getTextChannel().sendMessage(nom.build()).queue();
            } else
                event.getTextChannel().sendMessage("aucun nom de riven saisi").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
