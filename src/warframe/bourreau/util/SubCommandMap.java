package warframe.bourreau.util;

import java.lang.reflect.Method;

import static warframe.bourreau.Bourreau.subCommands;

public final class SubCommandMap {

    public static void registerCommands(String commande, Object...objects) {
        for(Object object : objects) registerCommand(commande, object);
    }

    public static void registerCommand(String commande, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(SubCommand.class)) {
                SubCommand command = method.getAnnotation(SubCommand.class);
                subCommands.get(commande).add(command.name());
            }
        }
    }
}
