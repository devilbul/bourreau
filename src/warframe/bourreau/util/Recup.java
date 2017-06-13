package warframe.bourreau.util;

import net.dv8tion.jda.core.entities.User;

import java.util.List;

public class Recup {

    public static String recupPseudo(String str) { return (str.split(" ")[1]).split(" ")[0]; }

    public static String recupString(String str) { return (str.replaceFirst(" ", "%")).split("%")[1]; }

    public static String recupID(String str) { return (str.split("[(]")[1]).split("[)]")[0]; }

    public static User recupUser(List<User> bannis, String userName) {
        for (User banni : bannis) {
            if (banni.getName().equals(userName))
                return banni;
        }

        return null;
    }
}
