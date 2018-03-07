package fr.warframe.devilbul.command.sondage;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findList;
import static fr.warframe.devilbul.utils.Find.findReponse;
import static fr.warframe.devilbul.utils.Find.findVotant;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class VoteCommand extends SimpleCommand {

    @SubCommand(name = "vote", commande = "sondage")
    @Help(field = "**syntaxe** :      !sondage vote <reponse>\n**condition :**   avoir un sondage en cours\n**effet :**            permet de voter", categorie = Categorie.Sondage)
    public static void voteSondage(MessageReceivedEvent event, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                    String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

                    if (commande.contains(" ")) {
                        String vote = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "sondage" + File.separator + "vote.json")));
                        JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                        JSONObject voteJson = new JSONObject(vote);
                        JSONObject voteFaitJson = new JSONObject();
                        String[] reponsesList = new String[reponsesJson.getInt("nb")];
                        String reponse = recupString(commande).toLowerCase();
                        Member auteur = event.getMember();

                        for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                            reponsesList[i] = reponsesJson.getString("reponse" + (i + 1));

                        if (!findVotant(auteur, voteJson.getJSONObject(event.getGuild().getId()))) {
                            if (findList(reponsesList, reponse)) {
                                FileWriter file = new FileWriter(adresseVote);
                                int nbVote = voteJson.getJSONObject(event.getGuild().getId()).getInt("nbVote") + 1;
                                int reponseFaite = findReponse(reponsesList, reponse) + 1;

                                voteFaitJson.put("auteur", event.getAuthor().getName());
                                voteFaitJson.put("auteurID", event.getAuthor().getId());
                                voteFaitJson.put("reponses", reponse);

                                voteJson.getJSONObject(event.getGuild().getId()).put("nbReponse" + reponseFaite, voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + reponseFaite) + 1);

                                voteJson.getJSONObject(event.getGuild().getId()).put("nbVote", nbVote);
                                voteJson.getJSONObject(event.getGuild().getId()).put("vote" + nbVote, voteFaitJson);

                                event.getTextChannel().sendMessage("a voté.").queue();

                                file.write(voteJson.toString(3));
                                file.flush();
                                file.close();
                            } else
                                event.getTextChannel().sendMessage("ton vote n'est pas dans les réponses possibles.").queue();
                        } else
                            event.getTextChannel().sendMessage("vous avez déjà voté.").queue();
                    } else
                        event.getTextChannel().sendMessage("aucune réponses n'a été apportée.").queue();
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
