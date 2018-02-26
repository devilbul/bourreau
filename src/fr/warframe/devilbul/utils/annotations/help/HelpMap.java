package fr.warframe.devilbul.utils.annotations.help;

import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;

import java.lang.reflect.Method;

import static fr.warframe.devilbul.Bourreau.helpDetail;
import static fr.warframe.devilbul.Bourreau.helpList;

public final class HelpMap {

    public static void registerCommandHelp(String commande, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Help.class) && method.isAnnotationPresent(Command.class)
                    && method.getAnnotation(Command.class).name().equals(commande)) {
                Help help = method.getAnnotation(Help.class);
                helpList.get(help.categorie().toString()).add(commande);
                helpDetail.put(commande, help.field());
            }
        }
    }

    public static void registerSubCommandHelp(String commande, Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Help.class) && method.isAnnotationPresent(SubCommand.class)
                    && method.getAnnotation(SubCommand.class).name().equals(commande.split(" ")[1])) {
                Help help = method.getAnnotation(Help.class);
                helpList.get(help.categorie().toString()).add(commande);
                helpDetail.put(commande, help.field());
            }
        }
    }
}
