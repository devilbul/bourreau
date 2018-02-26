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

public class ClearCommand extends SimpleCommand {

    @SubCommand(name = "clear", commande = "sondage")
    @Help(field = "**syntaxe** :      !sondage clear\n**condition :**   avoir un sondage en cours\n**effet :**            supprime le sondage en cours", categorie = Categorie.Sondage)
    public static void clearSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
            JSONObject sondageJson = new JSONObject(sondage);
            JSONObject voteJson = new JSONObject(vote);
            FileWriter fileSondage = new FileWriter(adresseSondage);
            FileWriter fileVote = new FileWriter(adresseVote);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                sondageJson.remove(event.getGuild().getId());
                voteJson.remove(event.getGuild().getId());

                fileSondage.write(sondageJson.toString(3));
                fileSondage.flush();
                fileSondage.close();
                fileVote.write(voteJson.toString(3));
                fileVote.flush();
                fileVote.close();

                event.getTextChannel().sendMessage("sondage supprimé.").queue();
            } else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
