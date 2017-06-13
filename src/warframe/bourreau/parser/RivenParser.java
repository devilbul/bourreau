package warframe.bourreau.parser;

import static warframe.bourreau.util.Find.FindRivenJsonInfluenceCatgorie;

public class RivenParser {

    public static String ParserLowerToUpperCase(String arg) {
        String res = "";
        String[] str;

        if (arg.contains(" "))
            str = arg.split(" ");
        else {
            str = new String[1];
            str[0] = arg;
        }

        for (int i=0; i<str.length; i++) {
            if (str.length == 1)
                res += String.valueOf(str[i].charAt(0)).toUpperCase() + str[i].substring(1);
            else if (str.length > 1) {
                if (i == 0)
                    res += String.valueOf(str[i].charAt(0)).toUpperCase() + str[i].substring(1);
                else
                    res += "_" + String.valueOf(str[i].charAt(0)).toUpperCase() + str[i].substring(1);
            }
        }

        return res;
    }

    public static String ParserSpaceToUnderScore(String arg) {
        String res = "";
        String[] str;

        switch (arg) {
            case "flux rifle":
                res = "fusil_a_flux";
                break;
            case "twin gremlins":
                res = "gremlins_jumeaux";
                break;
            case "twin vipers":
                res = "vipers_jumeaux";
                break;
            case "twin vipers wraith":
                res = "vipers_jumeaux_wraith";
                break;
            case "ceramic dagger":
                res = "dague_en_ceramique";
                break;
            case "dark dagger":
                res = "dague_sombre";
                break;
            case "dark sword":
                res = "epee_sombre";
                break;
            case "dual cleaver":
                res = "double_hachoirs";
                break;
            case "dual ether":
                res = "double_ether";
                break;
            case "dual heat swords":
                res = "double_epee";
                break;
            case "dual ichor":
                res = "double_ichor";
                break;
            case "dual kama":
                res = "double_kama";
                break;
            case "dual kama prime":
                res = "double_kama_prime";
                break;
            case "dual skana":
                res = "double_skana";
                break;
            case "dual zoren":
                res = "double_zoren";
                break;
            case "ether dagger":
                res = "dagues_ether";
                break;
            case "ether reaper":
                res = "reaper_ether";
                break;
            case "ether sword":
                res = "epee_ether";
                break;
            case "heat dagger":
                res = "dague_de_chaleur";
                break;
            case "heat sword":
                res = "epee_de_chaleur";
                break;
            case "jaw sword":
                res = "epee_jaw";
                break;
            case "pangolin sword":
                res = "epee_pangolin";
                break;
            case "plasma sword":
                res = "epee_a_plasma";
                break;
            case "silva & aegis":
                res = "silva_et_aegis";
                break;
            case "silva & aegis prime":
                res = "silva_et_aegis_prime";
                break;
            case "deconstructor":
                res = "deconstructeur";
                break;
            case "deth machine rifle":
                res = "fusil_machine_de_mort";
                break;
            case "laser rifle":
                res = "fusil_laser";
                break;
            case "prime laser rifle":
                res = "fusil_laser_prime";
                break;
            case "burst laser":
                res = "laser_a_rafale";
                break;
            default:
                if (arg.contains(" "))
                    str = arg.split(" ");
                else {
                    str = new String[1];
                    str[0] = arg;
                }

                for (int i = 0; i < str.length; i++) {
                    if (str.length == 1)
                        res += str[i];
                    else if (str.length > 1) {
                        if (i == 0)
                            res += str[i];
                        else
                            res += "_" + str[i];
                    }
                }
                break;
        }

        return res;
    }

    public static String ParserCategorieBuilder(String cherche) {
        String res = "";
        String arg = FindRivenJsonInfluenceCatgorie(cherche);

        if (cherche.equals("artax") || cherche.equals("burst laser")  || cherche.equals("deconstructor") || cherche.equals("deconstructor prime")
                || cherche.equals("deth machine rifle") || cherche.equals("laser rifle") || cherche.equals("prime laser rifle")
                || cherche.equals("prisma burst laser") || cherche.equals("stinger") || cherche.equals("sweeper") || cherche.equals("sweeper prime")
                || cherche.equals("vulklok"))
            res = "armes_de_sentinelles";
        else{
            assert arg != null;
            switch (arg) {
                case "rifle":
                case "shotgun":
                case "archgun":
                    res = "armes_principales";
                    break;
                case "pistol":
                    res = "armes_secondaires";
                    break;
                case "melee":
                case "archmelee":
                    res = "armes_de_melee";
                    break;
                case "warframe":
                case "archwing":
                    res = "warframes";
                    break;
            }
        }

        return res;
    }

    public static String ParserCategorie(String arg) {
        String res = "";

        switch (arg) {
            case "rifle":
                res = "Fusil";
                break;
            case "shotgun":
                res = "Fusil à pompe";
                break;
            case "archgun":
                res = "Arch-Gun";
                break;
            case "pistol":
                res = "Secondaire";
                break;
            case "melee":
                res = "Mélée";
                break;
            case "archmelee":
                res = "Arch_mélée";
                break;
            case "warframe":
                res = "Warframe";
                break;
            case "archwing":
                res = "Arch-Wing";
                break;
        }

        return res;
    }
}
