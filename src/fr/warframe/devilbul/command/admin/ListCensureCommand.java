package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;

public class ListCensureCommand extends SimpleCommand {

    @Command(name = "listcensure")
    @Help(field = "**syntaxe** :      !listcensure \n**effet :**         liste les censurés", categorie = Categorie.Admin)
    public static void censure(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
                JSONArray antiSpamJson = new JSONArray(antiSpam);
                StringBuilder message = new StringBuilder();

                if (antiSpamJson.length() == 0) {
                    event.getTextChannel().sendMessage("Aucun utilisateur censuré").queue();
                    return;
                }

                message.append("Liste des censurés :");

                for (int i = 0; i < antiSpamJson.length(); i++)
                    message.append("\n**").append(i).append("** : **").append(antiSpamJson.getJSONObject(i).getString("username"))
                            .append("** (").append(antiSpamJson.getJSONObject(i).getString("id")).append(") **")
                            .append(antiSpamJson.getJSONObject(i).getInt("time")).append("** ms");

                event.getTextChannel().sendMessage(message.toString()).queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
