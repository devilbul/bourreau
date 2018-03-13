package fr.warframe.devilbul.utils.find;

import static fr.warframe.devilbul.Bourreau.helpDetail;
import static fr.warframe.devilbul.Bourreau.helpList;

public class FindHelpCommand {

    public static boolean isCommandInHelpDetail(String command) {
        for (String key : helpDetail.keySet()) {
            if (key.equals(command))
                return true;
        }
        return false;
    }

    public static String getCategorieCommand(String command) {
        for (String categorie : helpList.keySet())
            for (String cmd : helpList.get(categorie))
                if (cmd.equals(command))
                    return categorie;

        return null;
    }
}
