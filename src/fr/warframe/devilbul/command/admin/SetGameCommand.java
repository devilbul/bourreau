package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class SetGameCommand extends SimpleCommand {

    @Command(name = "setgame")
    @Help(field = "**syntaxe** :      !setgame <nouveau jeu>\n**effet :**         change le jeu auquel joue le bot", categorie = Categorie.Admin)
    public static void setGame(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                String bot = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "bot.json")));
                JSONObject botjson = new JSONObject(bot);
                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ") && !recupString(commande).equals("null")) {
                    FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "bot.json");

                    botjson.put("game", recupString(commande));
                    file.write(botjson.toString(3));
                    file.flush();
                    file.close();

                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.STREAMING, recupString(commande), "https://trello.com/b/JEEkreCv/bot-discord-alliance"));
                    event.getTextChannel().sendMessage("jeu changé.").queue();
                } else
                    event.getTextChannel().sendMessage("pas de jeu saisi.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
