package fr.warframe.devilbul.utils.find;

import java.util.List;

public class Contains {

    public static boolean messageContains(String message, List<String> mots) {
        for (String mot : mots)
            if (message.contains(mot))
                return true;

        return false;
    }
}
