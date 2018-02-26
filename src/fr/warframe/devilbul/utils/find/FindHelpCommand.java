package fr.warframe.devilbul.utils.find;

import static fr.warframe.devilbul.Bourreau.helpDetail;

public class FindHelpCommand {

    public static boolean isCommandInHelpDetail(String command) {
        for (String key : helpDetail.keySet()) {
            if (key.equals(command))
                return true;
        }
        return false;
    }
}
