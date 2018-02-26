package fr.warframe.devilbul.handle;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.parser.CommandParser;
import fr.warframe.devilbul.utils.annotations.command.Command;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.Init.commandList;
import static fr.warframe.devilbul.message.event.NoThing.messageNoThing;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class HandleCommand {

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandJson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(cmd.event.getGuild().getId()).getJSONArray("commandes");

            if (SimpleCommand.called(cmd)) {
                for (Object object : commandList)
                    for (Method method : object.getClass().getDeclaredMethods())
                        if (method.isAnnotationPresent(Command.class)) {
                            Command command = method.getAnnotation(Command.class);

                            if (!command.isPrivate() && command.name().equals(cmd.invoke))
                                method.invoke(null, cmd.event);
                        }
            } else {
                if (configCommandJson.toList().isEmpty())
                    cmd.event.getTextChannel().sendMessage("Le bot n'a pas encore été configuré pour ce serveur !").queue();
                else {
                    cmd.event.getTextChannel().sendMessage(compareCommande(cmd.invoke, configCommandJson.toList().toArray())).queue();
                    messageNoThing(cmd.event);
                }
            }
        } catch (IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }
}
