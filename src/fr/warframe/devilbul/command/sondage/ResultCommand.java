package fr.warframe.devilbul.command.sondage;

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

public class ResultCommand extends SimpleCommand {

    @SubCommand(name = "resultat", commande = "sondage")
    @Help(field = "**syntaxe** :      !sondage resultat**condition :**   avoir un sondage en cours\n**effet :**            affiche les résultats du sondage en cours", categorie = Categorie.Sondage)
    public static void resultatSondage(MessageReceivedEvent event) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.toString().contains("reponses")) {
                    String vote = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "sondage" + File.separator + "vote.json")));
                    JSONObject voteJson = new JSONObject(vote);
                    JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                    EmbedBuilder resultat = new EmbedBuilder();
                    StringBuilder reponses = new StringBuilder();
                    int nbReponse = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses").getInt("nb");
                    double[] stats = new double[nbReponse];

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses.append(reponsesJson.getString("reponse" + (i + 1))).append("\n");

                    resultat.setTitle("Résultat sondage en cours :", null);
                    resultat.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    resultat.setDescription("fait par : " + sondageJson.getJSONObject(event.getGuild().getId()).getString("createur"));
                    resultat.addField(sondageJson.getJSONObject(event.getGuild().getId()).getString("question"), reponses.toString(), false);
                    resultat.addField("__résultat :__ ", "", false);

                    for (int i = 0; i < sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses").getInt("nb"); i++) {
                        resultat.addField("nombre de vote pour **__" + reponsesJson.getString("reponse" + (i + 1)) + " :__**",
                                String.valueOf(voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + (i + 1))), true);
                    }

                    resultat.addField("__statistique :__", "", false);

                    for (int i = 0; i < nbReponse; i++) {
                        stats[i] = (voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + (i + 1)) / (voteJson.getJSONObject(event.getGuild().getId()).getInt("nbVote") * 1.0)) * 100;
                        resultat.addField("vote pour **__" + reponsesJson.getString("reponse" + (i + 1)) + " :__**",
                                String.valueOf(stats[i]) + "%", true);
                    }

                    resultat.addField("", "", false);
                    resultat.setColor(new Color(147, 117, 0));
                    resultat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                    event.getTextChannel().sendMessage(resultat.build()).queue();
                } else
                    event.getTextChannel().sendMessage("aucunes réponses n'a encore été spéficié.").queue();
            } else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
