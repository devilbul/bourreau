package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import warframe.bourreau.util.Command;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.parser.RivenParser.*;
import static warframe.bourreau.riven.JsonToTxt.Traitement;
import static warframe.bourreau.riven.TxtToJson.sortie;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class RivenCommand extends SimpleCommand {

    @Command(name="riven")
    public static void Riven(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContent().contains(" ")) {
                String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
                String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                switch (commande) {
                    case "calcul":
                        Calcul(event);
                        break;
                    case "defi":
                        Defi(event);
                        break;
                    case "influence":
                        Influence(event);
                        break;
                    case "info":
                        Info(event);
                        break;
                    case "nom":
                        Nom(event);
                        break;
                    case "stat":
                        Stat(event);
                        break;
                    case "traite":
                        Traite(event);
                        break;
                    case "update":
                        Update(event);
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();
                        String[] commandeRiven = {"calcul", "defi", "influence", "info", "nom", "stat", "parse"};

                        event.getTextChannel().sendMessage(CompareCommande(commande, commandeRiven)).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }
            }
            else {
                MessageBuilder message = new MessageBuilder();

                event.getTextChannel().sendMessage("Aucune action de riven saisie").queue();

                message.append("You know nothing, ");
                message.append(event.getAuthor());

                event.getTextChannel().sendMessage(message.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Calcul(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Coming Soon").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Defi(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder defi = new EmbedBuilder();

            if (commande.contains(" ")) {
                String categorie = recupString(commande);
                String numDefi = "";

                if (categorie.contains(" ")) {
                    numDefi = categorie.split(" ")[1];
                    categorie = categorie.split(" ")[0];
                }

                if (FindJsonKey(rivenJson, categorie)) {
                    if (numDefi.isEmpty()) {
                        String defis = "";

                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length(); i++)
                            defis += "**" + i + "** : " + rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(i) + "\n";

                        defi.setTitle("Defi pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        defi.setDescription("Riven : defi");
                        defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        defi.addField("résultat :", defis, true);
                        defi.addField("pour savoir les complications possibles pour un défi", "tapez **__!riven defi " + categorie + " <numéro du défi>__**", true);
                        defi.setColor(new Color(155, 55, 255));
                        defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");
                    }
                    else {
                        if (Integer.valueOf(numDefi) < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length()) {
                            String complication = "";
                            String weight = "";
                            String multiplier = "";
                            String[] complications;
                            String[] weights;
                            String[] multipliers;

                            for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().length(); i++) {
                                if (!rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i).equals("complication chance")) {
                                    complication += "- " + rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i) + "$";
                                    weight += rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("weight") + "$";
                                    multiplier += rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("multiplier") + "$";
                                }
                            }

                            complications = complication.split("[$]");
                            weights = weight.split("[$]");
                            multipliers = multiplier.split("[$]");

                            defi.setTitle("Defi n°" + numDefi + " avec complication de la categorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            defi.setDescription("Riven : defi / complications");
                            defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            defi.addField("defi n°" + numDefi + " :", rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)).toString(), false);
                            defi.addField("complication chance", rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).getString("complication chance"), false);
                            for (int i=0; i<complications.length; i++)
                                defi.addField(complications[i], "poids : " + weights[i] + "    /    multiplicateur : " + multipliers[i], false);
                            defi.setColor(new Color(155, 55, 255));
                            defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");
                        }
                        else
                            event.getTextChannel().sendMessage("pas de défi associé à ce numéro").queue();
                    }

                    event.getTextChannel().sendMessage(defi.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
            }
            else {
                String mes = "";

                for (Object name : rivenJson.names()) mes += "- " + name.toString() + "\n";

                defi.setTitle("Defi : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                defi.setDescription("Riven : defi");
                defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                defi.addField("veuillez indiquer une catégorie, de la liste suivante :", mes, true);
                defi.addField("afin d'afficher tous les défis possibles pour cette catégorie d'objet", "", true);
                defi.addField("**__!riven defi <categorie>__**", "", true);
                defi.setColor(new Color(155, 55, 255));
                defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(defi.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Influence(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());

            if (commande.contains(" ")) {
                String cherche = recupString(commande);
                String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);

                if (FindRivenJsonInfluenceTransforme(cherche) != null) {
                    EmbedBuilder influence = new EmbedBuilder();

                    influence.setTitle(ParserCategorie(FindRivenJsonInfluenceCatgorie(cherche)) + " : " + cherche, "http://warframe.wikia.com/wiki/" + ParserLowerToUpperCase(cherche));
                    influence.setDescription("Riven : influence");
                    influence.setThumbnail("http://warframe-builder.com/web/images/" + ParserCategorieBuilder(cherche) + "/" + ParserSpaceToUnderScore(cherche) + ".png");
                    influence.setColor(new Color(155, 55, 255));
                    influence.addField("résultat :" , FindRivenJsonInfluenceTransforme(cherche), false);
                    if (FindRivenJsonInfluenceDouble(cherche) >= 0  && FindRivenJsonInfluenceDouble(cherche) < 0.7)
                        influence.setImage("http://i.imgur.com/0Hkjs2t.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) >= 0.70 && FindRivenJsonInfluenceDouble(cherche) < 0.875)
                        influence.setImage("http://i.imgur.com/eKbr2if.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) >= 0.875 && FindRivenJsonInfluenceDouble(cherche) < 1.125)
                        influence.setImage("http://i.imgur.com/nWnOJFb.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) >= 1.125 && FindRivenJsonInfluenceDouble(cherche) < 1.3)
                        influence.setImage("http://i.imgur.com/GhBf9df.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) >= 1.3 && FindRivenJsonInfluenceDouble(cherche) <  2)
                        influence.setImage("http://i.imgur.com/cWaXulC.png");
                    influence.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                    event.getTextChannel().sendMessage(influence.build()).queue();
                }
                else {
                    String str = "";
                    String[] armes;

                    for (Object name : rivenJson.names()){
                        for (int i=0; i<rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                            str += rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i) + "$";
                    }

                    armes = str.split("[$]");

                    event.getTextChannel().sendMessage(CompareCommande(cherche, armes)).queue();
                    event.getTextChannel().sendMessage("erreur, l'objet saisi n'existe pas, ou est mal écrit").queue();
                }
            }
            else
                event.getTextChannel().sendMessage("aucun objet saisi").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Info(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder info = new EmbedBuilder();
            String polarity = "";
            String curseAttenuation = "";
            String buffAttenuation = "";
            String curseNumber = "";
            String buffNumber = "";

            if (commande.contains(" ")) {
                String categorie = recupString(commande);

                if (FindJsonKey(rivenJson, categorie)) {
                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").length(); i++) {
                        String str = rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").getString(i);

                        switch (str) {
                            case "ap_attack":
                                polarity += "- madurai\n";
                                break;
                            case "ap_defense":
                                polarity += "- vazarin\n";
                                break;
                            case "ap_tactic":
                                polarity += "- naramon\n";
                                break;
                            case "ap_1":
                                polarity += "- zenurik\n";
                                break;
                            case "ap_2":
                                polarity += "- penjaga\n";
                                break;
                            case "ap_3":
                                polarity += "- unairu\n";
                                break;
                            default:
                                polarity += "- **à ajouter : " + str + "**\n";
                                break;
                        }
                    }

                    info.addField("polarité : ", polarity, false);

                    info.addField("max rank limite : ", "8", false);

                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(0) + " - ";
                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(1);

                    info.addField("nombre de buff : ", buffNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").length(); i++)
                        buffAttenuation += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").getString(i) + " / ";

                    buffAttenuation = buffAttenuation.substring(0, buffAttenuation.length() - 3);

                    info.addField("facteur de reduction buff : ", buffAttenuation, false);

                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(0) + " - ";
                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(1);

                    info.addField("nombre de debuff : ", curseNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").length(); i++)
                        curseAttenuation += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").getString(i) + " / ";

                    curseAttenuation = curseAttenuation.substring(0, curseAttenuation.length() - 3);

                    info.addField("facteur de reduction debuff : ", curseAttenuation, false);

                    info.setTitle("Info pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                    info.setDescription("Riven : info");
                    info.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                    info.setColor(new Color(155, 55, 255));
                    info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");
                }
                else
                    event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
            }
            else {
                String mes = "";

                for (Object name : rivenJson.names()) mes += "- " + name.toString() + "\n";

                info.setTitle("Info : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                info.setDescription("Riven : info");
                info.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                info.addField("veuillez indiquer une catégorie, de la liste suivante :", mes, true);
                info.addField("afin d'afficher toutes les informations pour cette catégorie d'objet", "", true);
                info.addField("**__!riven info <categorie>__**", "", true);
                info.setColor(new Color(155, 55, 255));
                info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");
            }

            event.getTextChannel().sendMessage(info.build()).queue();

        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Nom(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);
                EmbedBuilder nom = new EmbedBuilder();
                String cherche = recupString(commande);
                String[] stat;
                String rawStat = "";
                String res = "statistique :\n";

                for (Object name : rivenJson.names()) {
                    for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").length(); i++) {
                        if (cherche.contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"));
                            cherche = "";

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche += aSplit;
                            }

                            rawStat += rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade") + "$";
                        }

                        if (cherche.contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"));
                            cherche = "";

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche += aSplit;
                            }

                            rawStat += rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade") + "$";
                        }
                    }
                }

                stat = rawStat.split("[$]");

                for (String aStat : stat) {
                    if (aStat.contains("%"))
                        res += "- " + aStat.split("% ")[1] + "\n";
                    else
                        res += "- " + aStat.replaceFirst(" ", "%").split("%")[1] + "\n";
                }

                if (recupString(commande).length() < 6)
                    res += "";

                if (recupString(commande).length() < 6) nom.setTitle("Statistique : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                else nom.setTitle("Statistique pour le nom de riven : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                nom.addField("résultat :" , res, true);
                nom.setColor(new Color(155, 55, 255));
                nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                if (cherche.contains("a") || cherche.contains("b") || cherche.contains("c") || cherche.contains("d") || cherche.contains("e") || cherche.contains("f") || cherche.contains("g") || cherche.contains("h")
                        || cherche.contains("i") || cherche.contains("j") || cherche.contains("k") || cherche.contains("l") || cherche.contains("m") || cherche.contains("n") || cherche.contains("o") || cherche.contains("p")
                        || cherche.contains("q") || cherche.contains("r") || cherche.contains("s") || cherche.contains("t") || cherche.contains("u") || cherche.contains("v") || cherche.contains("w") || cherche.contains("x")
                        || cherche.contains("y") || cherche.contains("z"))
                    event.getTextChannel().sendMessage("faute d'orthographe sur le nom saisi").queue();
                else
                    event.getTextChannel().sendMessage(nom.build()).queue();
            } else
                event.getTextChannel().sendMessage("aucun nom de riven saisi").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Stat(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder stat = new EmbedBuilder();

            if (commande.contains(" ")) {
                String stats;
                String categorie = recupString(commande);

                if (categorie.contains(" ")) categorie = categorie.split(" ")[0];

                if (recupString(commande).contains(" ")) {
                    if (recupString(recupString(commande)).equals("detail")) {
                        if (FindJsonKey(rivenJson, categorie)) {
                            for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONArray("upgrades").length(); i++) {
                                stats = "";

                                stats += "peut-être un buff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be buff");
                                stats += "\npeut-être un debuff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be curse");
                                stats += "\nrareté : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("rarity");
                                stats += "\nprefix : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("prefix");
                                stats += "\nsuffix : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("suffix");
                                stats += "\ntype d'opération : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("operation type");

                                if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("|")) {
                                    String[] str = rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("[|]");

                                    stat.addField("- " + str[0] + str[2], stats, false);
                                }
                                else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                                else
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                            }

                            stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                            stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            stat.setDescription("Riven : stat");
                            stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            stat.setColor(new Color(155, 55, 255));
                            stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                            event.getTextChannel().sendMessage(stat.build()).queue();
                        } else
                            event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("essaye plutôt avec \"detail\"").queue();
                }
                else {
                    if (FindJsonKey(rivenJson, categorie)) {
                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONArray("upgrades").length(); i++) {
                            stats = "";

                            stats += "peut-être un buff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be buff");
                            stats += "\npeut-être un debuff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be curse");

                            if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("|")) {
                                String[] str = rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("[|]");

                                stat.addField("- " + str[0] + str[2], stats, false);
                            }
                            else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                            else
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                        }

                        stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                        stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        stat.setDescription("Riven : stat");
                        stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        stat.setColor(new Color(155, 55, 255));
                        stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                        event.getTextChannel().sendMessage(stat.build()).queue();
                    } else
                        event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                }
            }
            else {
                String mes = "";

                for (Object name : rivenJson.names()) mes += "- " + name.toString() + "\n";

                stat.setTitle("Stat : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                stat.setDescription("Riven : stat");
                stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                stat.addField("veuillez indiquer une catégorie, de la liste suivante :", mes, true);
                stat.addField("afin d'afficher tous les statistique possibles pour cette catégorie d'objet", "", true);
                stat.addField("**__!riven stat <categorie>__**", "", true);
                stat.setColor(new Color(155, 55, 255));
                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(stat.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Traite(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String adresse = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + "RivenMods.json")));
                String adresseSortie = System.getProperty("user.dir") + File.separator + "res" + File.separator + "info" + File.separator + "RivenMods.json";
                String adressePrimary = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_primary.txt";
                String adresseMelee = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_melee.txt";
                String adresseSecondary = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_secondary.txt";
                String adresseShotgun = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_shotgun.txt";
                FileWriter file = new FileWriter(adresseSortie);
                JSONObject rivenJson = new JSONObject(adresse);
                Scanner scanner;
                String line;
                String arme;
                boolean fini = false;

                if (new File(adressePrimary).exists()) {
                    scanner = new Scanner(new File(adressePrimary));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (rivenJson.getJSONObject("rifle").getJSONObject("item").names().toString().contains(arme))
                            rivenJson.getJSONObject("rifle").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseMelee).exists()) {
                    scanner = new Scanner(new File(adresseMelee));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (rivenJson.getJSONObject("melee").getJSONObject("item").names().toString().contains(arme))
                            rivenJson.getJSONObject("melee").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseSecondary).exists()) {
                    scanner = new Scanner(new File(adresseSecondary));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (rivenJson.getJSONObject("pistol").getJSONObject("item").names().toString().contains(arme))
                            rivenJson.getJSONObject("pistol").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseShotgun).exists()) {
                    scanner = new Scanner(new File(adresseShotgun));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (rivenJson.getJSONObject("shotgun").getJSONObject("item").names().toString().contains(arme))
                            rivenJson.getJSONObject("shotgun").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (fini)
                    event.getTextChannel().sendMessage("Traitement effectué.").queue();

                file.write(rivenJson.toString());
                file.flush();
                file.close();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void ParseHTML(MessageReceivedEvent event) {
        try {
            String url = "https://semlar.com/rivencalc";
            Document doc = Jsoup.connect(url).get();
            Element scriptRiven = doc.select("script").last();

            String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "output" + File.separator + "riven.html";
            String adresseJson = System.getProperty("user.dir") + File.separator + "res" + File.separator + "output" + File.separator + "riven.json";
            FileWriter file = new FileWriter(adresse);
            FileWriter fileJson = new FileWriter(adresseJson);
            File fileRead = new File(adresse);
            Scanner scan = new Scanner(fileRead);
            boolean isCopying = true;
            boolean isCom = false;
            String nextLine;
            String previous = "";

            file.write(scriptRiven.toString());
            file.flush();
            file.close();

            scan.nextLine();
            scan.nextLine();
            fileJson.write("{");

            while (scan.hasNextLine() && isCopying) {
                nextLine = scan.nextLine();

                if (nextLine.contains("var RivenTypeOrder")) isCopying = false;

                if (nextLine.contains("/*")) isCom = true;

                if (isCopying && !isCom) {
                    if (nextLine.contains("]") && previous.contains("},"))
                        fileJson.write("\n{}\n" + nextLine);
                    else if (!nextLine.contains("//"))
                        fileJson.write("\n" + nextLine);
                }

                if (nextLine.contains("*/")) isCom = false;

                previous = nextLine;
            }

            scan.close();

            fileJson.flush();
            fileJson.close();

            if (new File(adresse).exists())
                new File(adresse).delete();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Update(MessageReceivedEvent event) {
        try {
            String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "output" + File.separator + "riven.json";

            ParseHTML(event);
            Traitement();

            if (new File(adresse).exists())
                new File(adresse).delete();

            Traite(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
