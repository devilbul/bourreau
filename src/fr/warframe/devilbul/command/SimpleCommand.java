package fr.warframe.devilbul.command;

import fr.warframe.devilbul.Bourreau;
import fr.warframe.devilbul.parser.CommandParser;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findIndexStringArray;

public class SimpleCommand {

    public static boolean called(CommandParser.CommandContainer cmd) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(cmd.event.getGuild().getId()).getJSONArray("commandes");

            if (cmd.invoke.equals("config"))
                return true;

            if (configCommandson.length() > 0 && findIndexStringArray(configCommandson, cmd.invoke) != -1)
                return true;
        } catch (Exception e) {
            afficheErreur(cmd.event.getMessage().getContentDisplay(), e);
            saveErreur(cmd.event.getMessage().getTextChannel().getName(), cmd.event.getAuthor().getName(), cmd.event.getAuthor().getId(), cmd.event.getMessage().getContentDisplay(), e);
        }

        return false;
    }

    @Command(name = "test")
    @Help(field = "ceci est un test", categorie = Categorie.Autre)
    public static void test(MessageReceivedEvent event) {
        try {

            /*for (Role role : event.getGuild().getRoles()) {
                event.getTextChannel().sendMessage(role.getName() + " : " + role.getId()).queue();
            }*/

            /*System.out.println("===================================================");
            for (String s : Bourreau.commands.keySet()) {
                System.out.println(s);
            }
            System.out.println("===================================================");*/

        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
