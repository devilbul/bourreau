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

public class DisplayCommand extends SimpleCommand {

    @SubCommand(name = "show", commande = "sondage")
    @Help(field = "**syntaxe** :      !sondage show\n**condition :**   avoir un sondage en cours" +
            "\n**effet :**            affiche le sondage en cours, question + réponses possibles", categorie = Categorie.Sondage)
    public static void afficheSondage(MessageReceivedEvent event) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                    EmbedBuilder sondageDisplay = new EmbedBuilder();
                    JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                    StringBuilder reponses = new StringBuilder();

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses.append(reponsesJson.getString("reponse" + (i + 1))).append("\n");

                    sondageDisplay.setTitle("Sondage en cours :", null);
                    sondageDisplay.setDescription("fait par : " + sondageJson.getJSONObject(event.getGuild().getId()).getString("createur"));
                    sondageDisplay.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    sondageDisplay.addField(sondageJson.getJSONObject(event.getGuild().getId()).getString("question"), reponses.toString(), false);
                    sondageDisplay.addField("Pour voter :", "!sondage vote <choix>\navec <choix> doit être parmis la liste ci-dessus", false);
                    sondageDisplay.setColor(new Color(147, 117, 0));
                    sondageDisplay.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                    event.getTextChannel().sendMessage(sondageDisplay.build()).queue();
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
