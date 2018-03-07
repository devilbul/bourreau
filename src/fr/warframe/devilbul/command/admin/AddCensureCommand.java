package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.isUserCensure;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class AddCensureCommand extends SimpleCommand {

    @Command(name = "censure")
    @Help(field = "**syntaxe** :      !censure @<pseudo> <time>\n**effet :**         ajoute l'utilisateur à la liste des censurés, avec un temps entre chaque commande son", categorie = Categorie.Admin)
    public static void censure(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getMessage().toString().contains("@")) {
                    if (!isUserCensure(event.getMessage().getMentionedUsers().get(0).getId())) {
                        String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
                        JSONArray antiSpamJson = new JSONArray(antiSpam);
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "anti_spam.json");
                        String commande = recupString(event.getMessage().getContentDisplay());
                        int time = 1800;

                        if (commande.contains(" ")) {
                            try {
                                time = Integer.valueOf(recupString(commande));
                                System.out.println(time);
                            } catch (NumberFormatException  e) {
                                e.printStackTrace();
                            }
                        }

                        antiSpamJson.put(new JSONObject()
                                .put("username", event.getMessage().getMentionedUsers().get(0).getName())
                                .put("id", event.getMessage().getMentionedUsers().get(0).getId())
                                .put("time", time)
                                .put("next_music_command", 0)
                        );

                        event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getName() + " ajouté à la liste de censure.").queue();

                        file.write(antiSpamJson.toString(3));
                        file.flush();
                        file.close();
                    } else
                        event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getName() + " est déjà censuré.").queue();
                } else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
