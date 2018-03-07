package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class RemoveCensureCommand extends SimpleCommand {

    @Command(name = "uncensure")
    @Help(field = "**syntaxe** :      !uncensure <nb>\n**effet :**         retire l'utilisateur à la liste des censurés", categorie = Categorie.Admin)
    public static void censure(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getMessage().getContentDisplay().contains(" ")) {
                    String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
                    JSONArray antiSpamJson = new JSONArray(antiSpam);
                    int index;

                    try {
                        index = Integer.valueOf(recupString(event.getMessage().getContentDisplay()));
                    } catch (NumberFormatException e) {
                        event.getTextChannel().sendMessage("Invalide").queue();
                        return;
                    }

                    if (index > -1) {
                        if (index < antiSpamJson.length()) {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "anti_spam.json");

                            antiSpamJson.remove(index);

                            file.write(antiSpamJson.toString(3));
                            file.flush();
                            file.close();

                            event.getTextChannel().sendMessage("Supprimé.").queue();
                        } else
                            event.getTextChannel().sendMessage("index incorrect").queue();
                    }
                } else
                    event.getTextChannel().sendMessage("Aucun index saisie.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
