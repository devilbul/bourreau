package fr.warframe.devilbul.utils.annotations.sub.command;

import fr.warframe.devilbul.utils.annotations.help.HelpMap;

import java.lang.reflect.Method;

import static fr.warframe.devilbul.Bourreau.subCommands;

public final class SubCommandMap {

    public static void registerCommand(Object object, Method method) {
        if (method.isAnnotationPresent(SubCommand.class)) {
            SubCommand subCommand = method.getAnnotation(SubCommand.class);
            subCommands.get(subCommand.commande()).add(subCommand.name());
            HelpMap.registerSubCommandHelp(subCommand.commande() + " " + subCommand.name(), object);
        }
    }
}
