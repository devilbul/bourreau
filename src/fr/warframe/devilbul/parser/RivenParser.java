package fr.warframe.devilbul.parser;

import static fr.warframe.devilbul.utils.Find.findRivenJsonInfluenceCatgorie;

public class RivenParser {

    public static String parserLowerToUpperCase(String arg) {
        StringBuilder res = new StringBuilder();
        String[] str;

        if (arg.contains(" "))
            str = arg.split(" ");
        else {
            str = new String[1];
            str[0] = arg;
        }

        for (int i = 0; i < str.length; i++) {
            if (str.length == 1)
                res.append(String.valueOf(str[i].charAt(0)).toUpperCase()).append(str[i].substring(1));
            else {
                if (i == 0)
                    res.append(String.valueOf(str[i].charAt(0)).toUpperCase()).append(str[i].substring(1));
                else
                    res.append("_").append(String.valueOf(str[i].charAt(0)).toUpperCase()).append(str[i].substring(1));
            }
        }

        return res.toString();
    }

    public static String parserSpaceToUnderScore(String arg) {
        StringBuilder res = new StringBuilder();
        String[] str;

        switch (arg) {
            case "flux rifle":
                res = new StringBuilder("fusil_a_flux");
                break;
            case "twin gremlins":
                res = new StringBuilder("gremlins_jumeaux");
                break;
            case "twin vipers":
                res = new StringBuilder("vipers_jumeaux");
                break;
            case "twin vipers wraith":
                res = new StringBuilder("vipers_jumeaux_wraith");
                break;
            case "ceramic dagger":
                res = new StringBuilder("dague_en_ceramique");
                break;
            case "dark dagger":
                res = new StringBuilder("dague_sombre");
                break;
            case "dark sword":
                res = new StringBuilder("epee_sombre");
                break;
            case "dual cleaver":
                res = new StringBuilder("double_hachoirs");
                break;
            case "dual ether":
                res = new StringBuilder("double_ether");
                break;
            case "dual heat swords":
                res = new StringBuilder("double_epee");
                break;
            case "dual ichor":
                res = new StringBuilder("double_ichor");
                break;
            case "dual kama":
                res = new StringBuilder("double_kama");
                break;
            case "dual kama prime":
                res = new StringBuilder("double_kama_prime");
                break;
            case "dual skana":
                res = new StringBuilder("double_skana");
                break;
            case "dual zoren":
                res = new StringBuilder("double_zoren");
                break;
            case "ether dagger":
                res = new StringBuilder("dagues_ether");
                break;
            case "ether reaper":
                res = new StringBuilder("reaper_ether");
                break;
            case "ether sword":
                res = new StringBuilder("epee_ether");
                break;
            case "heat dagger":
                res = new StringBuilder("dague_de_chaleur");
                break;
            case "heat sword":
                res = new StringBuilder("epee_de_chaleur");
                break;
            case "jaw sword":
                res = new StringBuilder("epee_jaw");
                break;
            case "pangolin sword":
                res = new StringBuilder("epee_pangolin");
                break;
            case "plasma sword":
                res = new StringBuilder("epee_a_plasma");
                break;
            case "silva & aegis":
                res = new StringBuilder("silva_et_aegis");
                break;
            case "silva & aegis prime":
                res = new StringBuilder("silva_et_aegis_prime");
                break;
            case "deconstructor":
                res = new StringBuilder("deconstructeur");
                break;
            case "deth machine rifle":
                res = new StringBuilder("fusil_machine_de_mort");
                break;
            case "laser rifle":
                res = new StringBuilder("fusil_laser");
                break;
            case "prime laser rifle":
                res = new StringBuilder("fusil_laser_prime");
                break;
            case "burst laser":
                res = new StringBuilder("laser_a_rafale");
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
                        res.append(str[i]);
                    else {
                        if (i == 0)
                            res.append(str[i]);
                        else
                            res.append("_").append(str[i]);
                    }
                }
                break;
        }

        return res.toString();
    }

    public static String parserCategorieBuilder(String cherche) {
        String res = "";
        String arg = findRivenJsonInfluenceCatgorie(cherche);

        if (cherche.equals("artax") || cherche.equals("burst laser") || cherche.equals("deconstructor") || cherche.equals("deconstructor prime")
                || cherche.equals("deth machine rifle") || cherche.equals("laser rifle") || cherche.equals("prime laser rifle")
                || cherche.equals("prisma burst laser") || cherche.equals("stinger") || cherche.equals("sweeper") || cherche.equals("sweeper prime")
                || cherche.equals("vulklok"))
            res = "armes_de_sentinelles";
        else {
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

    public static String parserCategorie(String arg) {
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
