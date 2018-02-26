package warframe.bourreau.util;

import warframe.bourreau.commands.SimpleCommand;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.Bourreau.subCommands;

public final class CommandMap {

    public static void registerCommands(Object...objects) {
        for(Object object : objects) registerCommand(object);
    }

    public static void registerCommand(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Command.class)) {
                Command command = method.getAnnotation(Command.class);
                commands.put(command.name(), new SimpleCommand());

                if (command.subCommand()) {
                    subCommands.put(command.name(), new ArrayList<>());
                    SubCommandMap.registerCommands(command.name(), object);
                }
            }
        }
    }
}
