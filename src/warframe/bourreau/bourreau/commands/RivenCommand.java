package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.SubCommand;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

import static warframe.bourreau.Bourreau.subCommands;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.parser.RivenParser.*;
import static warframe.bourreau.riven.JsonToTxt.traitement;
import static warframe.bourreau.riven.TxtToJson.sortie;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class RivenCommand extends SimpleCommand {

    @Command(name="riven", subCommand=true)
    public static void riven(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                switch (commande) {
                    case "calcul":
                        calcul(event);
                        break;
                    case "defi":
                        defi(event);
                        break;
                    case "influence":
                        influence(event);
                        break;
                    case "info":
                        info(event);
                        break;
                    case "nom":
                        nom(event);
                        break;
                    case "stat":
                        stat(event);
                        break;
                    case "traite":
                        traite(event);
                        break;
                    case "update":
                        update(event);
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("riven").toArray())).queue();
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

    @SubCommand(name="calcul")
    private static void calcul(MessageReceivedEvent event) {
        try {
            event.getTextChannel().sendMessage("Coming Soon").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="defi")
    private static void defi(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
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

                if (findJsonKey(rivenJson, categorie)) {
                    if (numDefi.isEmpty()) {
                        StringBuilder defis = new StringBuilder();

                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length(); i++)
                            defis.append("**").append(i).append("** : ").append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(i)).append("\n");

                        defi.setTitle("Defi pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        defi.setDescription("Riven : defi");
                        defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        defi.addField("résultat :", defis.toString(), true);
                        defi.addField("pour savoir les complications possibles pour un défi", "tapez **__!riven defi " + categorie + " <numéro du défi>__**", true);
                        defi.setColor(new Color(155, 55, 255));
                        defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");
                    }
                    else {
                        if (Integer.valueOf(numDefi) < rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().length()) {
                            StringBuilder complication = new StringBuilder();
                            StringBuilder weight = new StringBuilder();
                            StringBuilder multiplier = new StringBuilder();
                            String[] complications;
                            String[] weights;
                            String[] multipliers;

                            for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().length(); i++) {
                                if (!rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i).equals("complication chance")) {
                                    complication.append("- ").append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)).append("$");
                                    weight.append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("weight")).append("$");
                                    multiplier.append(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi))))
                                            .getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").getJSONObject(String.valueOf(rivenJson.getJSONObject(categorie).getJSONObject("challenge").names().get(Integer.valueOf(numDefi)))).names().get(i)))
                                            .getString("multiplier")).append("$");
                                }
                            }

                            complications = complication.toString().split("[$]");
                            weights = weight.toString().split("[$]");
                            multipliers = multiplier.toString().split("[$]");

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
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                defi.setTitle("Defi : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                defi.setDescription("Riven : defi");
                defi.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                defi.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
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

    @SubCommand(name="influence")
    private static void influence(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String cherche = recupString(commande);
                String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);

                if (findRivenJsonInfluenceTransforme(cherche) != null) {
                    EmbedBuilder influence = new EmbedBuilder();

                    influence.setTitle(parserCategorie(findRivenJsonInfluenceCatgorie(cherche)) + " : " + cherche, "http://warframe.wikia.com/wiki/" + parserLowerToUpperCase(cherche));
                    influence.setDescription("Riven : influence");
                    influence.setThumbnail("http://warframe-builder.com/web/images/" + parserCategorieBuilder(cherche) + "/" + parserSpaceToUnderScore(cherche) + ".png");
                    influence.setColor(new Color(155, 55, 255));
                    influence.addField("résultat :" , findRivenJsonInfluenceTransforme(cherche), false);
                    if (findRivenJsonInfluenceDouble(cherche) >= 0  && findRivenJsonInfluenceDouble(cherche) < 0.7)
                        influence.setImage("http://i.imgur.com/0Hkjs2t.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 0.70 && findRivenJsonInfluenceDouble(cherche) < 0.875)
                        influence.setImage("http://i.imgur.com/eKbr2if.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 0.875 && findRivenJsonInfluenceDouble(cherche) < 1.125)
                        influence.setImage("http://i.imgur.com/nWnOJFb.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 1.125 && findRivenJsonInfluenceDouble(cherche) < 1.3)
                        influence.setImage("http://i.imgur.com/GhBf9df.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 1.3 && findRivenJsonInfluenceDouble(cherche) <  2)
                        influence.setImage("http://i.imgur.com/cWaXulC.png");
                    influence.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                    event.getTextChannel().sendMessage(influence.build()).queue();
                }
                else {
                    StringBuilder str = new StringBuilder();
                    String[] armes;

                    for (Object name : rivenJson.names()){
                        for (int i=0; i<rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                            str.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)).append("$");
                    }

                    armes = str.toString().split("[$]");

                    event.getTextChannel().sendMessage(compareCommande(cherche, armes)).queue();
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

    @SubCommand(name="info")
    private static void info(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder info = new EmbedBuilder();
            StringBuilder polarity = new StringBuilder();
            StringBuilder curseAttenuation = new StringBuilder();
            StringBuilder buffAttenuation = new StringBuilder();
            String curseNumber = "";
            String buffNumber = "";

            if (commande.contains(" ")) {
                String categorie = recupString(commande);

                if (findJsonKey(rivenJson, categorie)) {
                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").length(); i++) {
                        String str = rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("polarities").getString(i);

                        switch (str) {
                            case "ap_attack":
                                polarity.append("- madurai\n");
                                break;
                            case "ap_defense":
                                polarity.append("- vazarin\n");
                                break;
                            case "ap_tactic":
                                polarity.append("- naramon\n");
                                break;
                            case "ap_1":
                                polarity.append("- zenurik\n");
                                break;
                            case "ap_2":
                                polarity.append("- penjaga\n");
                                break;
                            case "ap_3":
                                polarity.append("- unairu\n");
                                break;
                            default:
                                polarity.append("- **à ajouter : ").append(str).append("**\n");
                                break;
                        }
                    }

                    info.addField("polarité : ", polarity.toString(), false);

                    info.addField("max rank limite : ", "8", false);

                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(0) + " - ";
                    buffNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs").getString(1);

                    info.addField("nombre de buff : ", buffNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").length(); i++)
                        buffAttenuation.append(rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs attenuation").getString(i)).append(" / ");

                    buffAttenuation = new StringBuilder(buffAttenuation.substring(0, buffAttenuation.length() - 3));

                    info.addField("facteur de reduction buff : ", buffAttenuation.toString(), false);

                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(0) + " - ";
                    curseNumber += rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of curses").getString(1);

                    info.addField("nombre de debuff : ", curseNumber, false);

                    for (int i=0; i<rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").length(); i++)
                        curseAttenuation.append(rivenJson.getJSONObject(categorie).getJSONObject("info").getJSONArray("number of buffs curse attenuation").getString(i)).append(" / ");

                    curseAttenuation = new StringBuilder(curseAttenuation.substring(0, curseAttenuation.length() - 3));

                    info.addField("facteur de reduction debuff : ", curseAttenuation.toString(), false);

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
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                info.setTitle("Info : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                info.setDescription("Riven : info");
                info.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                info.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
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

    @SubCommand(name="nom")
    private static void nom(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);
                EmbedBuilder nom = new EmbedBuilder();
                StringBuilder cherche = new StringBuilder(recupString(commande));
                String[] stat;
                StringBuilder rawStat = new StringBuilder();
                StringBuilder res = new StringBuilder("statistique :\n");

                for (Object name : rivenJson.names()) {
                    for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").length(); i++) {
                        if (cherche.toString().contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.toString().split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix"));
                            cherche = new StringBuilder();

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche.append(aSplit);
                            }

                            rawStat.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade")).append("$");
                        }

                        if (cherche.toString().contains(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"))
                                && !rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("prefix").isEmpty()) {
                            String[] split = cherche.toString().split(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("suffix"));
                            cherche = new StringBuilder();

                            for (String aSplit : split) {
                                if (!aSplit.isEmpty())
                                    cherche.append(aSplit);
                            }

                            rawStat.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).getString("upgrade")).append("$");
                        }
                    }
                }

                stat = rawStat.toString().split("[$]");

                for (String aStat : stat) {
                    if (aStat.contains("%"))
                        res.append("- ").append(aStat.split("% ")[1]).append("\n");
                    else
                        res.append("- ").append(aStat.replaceFirst(" ", "%").split("%")[1]).append("\n");
                }

                if (recupString(commande).length() < 6)
                    res.append("");

                if (recupString(commande).length() < 6) nom.setTitle("Statistique : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                else nom.setTitle("Statistique pour le nom de riven : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                nom.addField("résultat :" , res.toString(), true);
                nom.setColor(new Color(155, 55, 255));
                nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                if (cherche.toString().contains("a") || cherche.toString().contains("b") || cherche.toString().contains("c") || cherche.toString().contains("d") || cherche.toString().contains("e") || cherche.toString().contains("f") || cherche.toString().contains("g") || cherche.toString().contains("h")
                        || cherche.toString().contains("i") || cherche.toString().contains("j") || cherche.toString().contains("k") || cherche.toString().contains("l") || cherche.toString().contains("m") || cherche.toString().contains("n") || cherche.toString().contains("o") || cherche.toString().contains("p")
                        || cherche.toString().contains("q") || cherche.toString().contains("r") || cherche.toString().contains("s") || cherche.toString().contains("t") || cherche.toString().contains("u") || cherche.toString().contains("v") || cherche.toString().contains("w") || cherche.toString().contains("x")
                        || cherche.toString().contains("y") || cherche.toString().contains("z"))
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

    @SubCommand(name="stat")
    private static void stat(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            EmbedBuilder stat = new EmbedBuilder();

            if (commande.contains(" ")) {
                String stats;
                String categorie = recupString(commande);

                if (categorie.contains(" ")) categorie = categorie.split(" ")[0];

                if (recupString(commande).contains(" ")) {
                    if (recupString(recupString(commande)).equals("detail")) {
                        if (findJsonKey(rivenJson, categorie)) {
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
                    if (findJsonKey(rivenJson, categorie)) {
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
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                stat.setTitle("Stat : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                stat.setDescription("Riven : stat");
                stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                stat.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
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

    @SubCommand(name="traite")
    private static void traite(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
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

                        if (findValueObjectList(rivenJson.getJSONObject("rifle").getJSONObject("item").names().toList(), arme))
                            rivenJson.getJSONObject("rifle").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseMelee).exists()) {
                    scanner = new Scanner(new File(adresseMelee));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (findValueObjectList(rivenJson.getJSONObject("melee").getJSONObject("item").names().toList(), arme))
                            rivenJson.getJSONObject("melee").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseSecondary).exists()) {
                    scanner = new Scanner(new File(adresseSecondary));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (findValueObjectList(rivenJson.getJSONObject("pistol").getJSONObject("item").names().toList(), arme))
                            rivenJson.getJSONObject("pistol").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (new File(adresseShotgun).exists()) {
                    scanner = new Scanner(new File(adresseShotgun));

                    while (scanner.hasNext()) {
                        line = scanner.nextLine().toLowerCase();
                        arme = line.replaceFirst(" ", "@").split("@")[1];

                        if (findValueObjectList(rivenJson.getJSONObject("shotgun").getJSONObject("item").names().toList(), arme))
                            rivenJson.getJSONObject("shotgun").getJSONObject("item").getJSONArray(arme).put(1, String.valueOf((100 + Integer.valueOf(line.split(" ")[0]))/100.0));
                    }
                    fini = true;
                }

                if (fini)
                    event.getTextChannel().sendMessage("Traitement effectué.").queue();

                file.write(rivenJson.toString(3));
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

    private static void parseHTML(MessageReceivedEvent event) {
        try {
                String url = "https://semlar.com/rivencalc";
                Document doc = Jsoup.connect(url).get();
                Elements scripts = doc.select("script");
                Element scriptRiven = scripts.get(findRivenScript(scripts));

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

    @SubCommand(name="update")
    private static void update(MessageReceivedEvent event) {
        try {
            String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "output" + File.separator + "riven.json";

            parseHTML(event);
            traitement();

            if (new File(adresse).exists())
                new File(adresse).delete();

            traite(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
