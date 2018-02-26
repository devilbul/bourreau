package fr.warframe.devilbul.utils.annotations.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.HelpMap;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommandMap;

import static fr.warframe.devilbul.Bourreau.commands;
import static fr.warframe.devilbul.Bourreau.subCommands;

public final class CommandMap {

    public static void registerListCommands(List<Object> objects) {
        for (Object object : objects) registerCommand(object);
    }

    public static void registerCommands(Object... objects) {
        for (Object object : objects) registerCommand(object);
    }

    public static void registerCommand(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);

                if (!command.isPrivate()) {
                    commands.put(command.name(), new SimpleCommand());
                    HelpMap.registerCommandHelp(command.name(), object);

                    if (command.subCommand())
                        subCommands.put(command.name(), new ArrayList<>());
                }
            }

            if (method.isAnnotationPresent(SubCommand.class))
                SubCommandMap.registerCommand(object, method);
        }
    }
}
