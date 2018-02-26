package fr.warframe.devilbul.command.sondage;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class ResponsesCommand extends SimpleCommand {

    @SubCommand(name = "reponses", commande = "sondage")
    @Help(field = "**syntaxe 1 :**      !sondage reponses <réponse1> / <réponse2>" + "\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande" +
            "\n**effet :**               ajoute <réponse1> et <réponse2>, commme réponses possibles" +
            "**syntaxe 2 :**      !sondage reponses <réponse1> / <réponse2> / ... / <réponseN>\n\n" +
            "\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande\n**effet :**               ajoute <réponse1>, <réponse2>, ... et " +
            "<réponseN>, commme réponses possibles", categorie = Categorie.Sondage)
    public static boolean reponsesSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if (commande.contains(" ")) {
                    String rawReponses = recupString(commande);
                    String[] reponses = rawReponses.split(" / ");

                    if (reponses.length > 1) {
                        String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                        JSONObject voteJson = new JSONObject(vote);

                        if (!sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                            JSONObject reponsesJson = new JSONObject();
                            FileWriter fileSondage = new FileWriter(adresseSondage);
                            FileWriter fileVote = new FileWriter(adresseVote);

                            for (int i = 0; i < reponses.length; i++) {
                                reponsesJson.put("reponse" + (i + 1), reponses[i]);
                                voteJson.getJSONObject(event.getGuild().getId()).put("nbReponse" + (i + 1), 0);
                            }

                            reponsesJson.put("nb", reponses.length);

                            sondageJson.getJSONObject(event.getGuild().getId()).put("reponses", reponsesJson);

                            event.getTextChannel().sendMessage("les réponses sont enregistrées.").queue();

                            fileSondage.write(sondageJson.toString(3));
                            fileSondage.flush();
                            fileSondage.close();
                            fileVote.write(voteJson.toString(3));
                            fileVote.flush();
                            fileVote.close();

                            return true;
                        } else {
                            event.getTextChannel().sendMessage("il y a déjà des réponses enregistrés.").queue();
                            return false;
                        }
                    } else {
                        event.getTextChannel().sendMessage("il n'y a qu'une seule réponse saisie, il en faut au moins deux.").queue();
                        return false;
                    }
                } else {
                    event.getTextChannel().sendMessage("aucunes réponse saisie.").queue();
                    return false;
                }
            } else {
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
                return false;
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
            return false;
        }
    }
}
