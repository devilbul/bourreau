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

public class CreateCommand extends SimpleCommand {

    @SubCommand(name = "create", commande = "sondage")
    @Help(field = "**syntaxe** :      !sondage create <question>" + "\n**condition :**   ne pas avoir un sondage en cours" +
            "\n**effet :**            créer le sondage", categorie = Categorie.Sondage)
    public static void createSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (!sondageJson.names().toString().contains(event.getGuild().getId())) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if (commande.contains(" ")) {
                    String question = recupString(commande);
                    String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                    JSONObject sondageJSON = new JSONObject();
                    JSONObject voteJSON = new JSONObject(vote);
                    FileWriter fileSondage = new FileWriter(adresseSondage);
                    FileWriter fileVote = new FileWriter(adresseVote);

                    sondageJSON.put("createur", event.getAuthor().getName());
                    sondageJSON.put("createurID", event.getAuthor().getId());
                    sondageJSON.put("question", question);
                    sondageJson.put(event.getGuild().getId(), sondageJSON);
                    voteJSON.put(event.getGuild().getId(), new JSONObject().put("nbVote", 0));

                    event.getTextChannel().sendMessage("sondage créé").queue();
                    event.getTextChannel().sendMessage("tapez **__!sondage reponses__**, suivi des diverses réponses souhaitées.").queue();

                    fileSondage.write(sondageJson.toString(3));
                    fileSondage.flush();
                    fileSondage.close();
                    fileVote.write(voteJSON.toString(3));
                    fileVote.flush();
                    fileVote.close();
                } else
                    event.getTextChannel().sendMessage("pas de question saisie.").queue();
            } else
                event.getTextChannel().sendMessage("il y a déjà un sondage en cours.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
