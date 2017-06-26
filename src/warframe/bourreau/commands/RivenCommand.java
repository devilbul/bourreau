package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.parser.RivenParser.*;
import static warframe.bourreau.riven.TxtToJson.Traitement;
import static warframe.bourreau.riven.TxtToJson.sortie;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class RivenCommand extends Command {

    public static void Riven(MessageReceivedEvent event) {
        if (event.getMessage().getContent().contains(" ")) {
            String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
            String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

            switch (commande) {
                case "influence":
                    Influence(event);
                    break;
                case "nom":
                    Nom(event);
                    break;
                case "calcul":
                    Calcul(event);
                    break;
                case "defi":
                    Defi(event);
                    break;
                case "stat":
                    Stat(event);
                    break;
                case "traite":
                    Traite(event);
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

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

    private static void Calcul(MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Coming Soon").queue();
    }

    private static void Defi(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("riven" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            String version = new String(Files.readAllBytes(Paths.get("riven" + File.separator + "riven_mods_info.json")));
            JSONObject versionJson = new JSONObject(version);
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
                        if (FindJsonKey(versionJson, "current version"))
                            defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                        else
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
                            if (FindJsonKey(versionJson, "current version"))
                                defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                            else
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
                if (FindJsonKey(versionJson, "current version"))
                    defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                else
                    defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(defi.build()).queue();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void Influence(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());

            if (commande.contains(" ")) {
                String cherche = recupString(commande);
                String version = new String(Files.readAllBytes(Paths.get("riven" + File.separator + "riven_mods_info.json")));
                JSONObject versionJson = new JSONObject(version);

                if (FindRivenJsonInfluenceTransforme(cherche) != null) {
                    EmbedBuilder influence = new EmbedBuilder();

                    influence.setTitle(ParserCategorie(FindRivenJsonInfluenceCatgorie(cherche)) + " : " + cherche, "http://warframe.wikia.com/wiki/" + ParserLowerToUpperCase(cherche));
                    influence.setDescription("Riven : influence");
                    influence.setThumbnail("http://warframe-builder.com/web/images/" + ParserCategorieBuilder(cherche) + "/" + ParserSpaceToUnderScore(cherche) + ".png");
                    influence.addField("résultat :" , FindRivenJsonInfluenceTransforme(cherche), true);
                    if (FindRivenJsonInfluenceDouble(cherche) > 0  && FindRivenJsonInfluenceDouble(cherche) < 0.7)
                        influence.setImage("http://i.imgur.com/0Hkjs2t.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) > 0.75 && FindRivenJsonInfluenceDouble(cherche) < 0.875)
                        influence.setImage("http://i.imgur.com/eKbr2if.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) > 0.95 && FindRivenJsonInfluenceDouble(cherche) < 1.125)
                        influence.setImage("http://i.imgur.com/nWnOJFb.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) > 1.125 && FindRivenJsonInfluenceDouble(cherche) < 1.3)
                        influence.setImage("http://i.imgur.com/GhBf9df.png");
                    else if (FindRivenJsonInfluenceDouble(cherche) > 1.3 && FindRivenJsonInfluenceDouble(cherche) <  1.7)
                        influence.setImage("http://i.imgur.com/cWaXulC.png");
                    influence.setColor(new Color(155, 55, 255));
                    if (FindJsonKey(versionJson, "current version"))
                        influence.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                    else
                        influence.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                    event.getTextChannel().sendMessage(influence.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("erreur, l'objet saisi n'existe pas, ou est mal écrit").queue();
            }
            else
                event.getTextChannel().sendMessage("aucun objet saisi").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void Info(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("riven" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            String version = new String(Files.readAllBytes(Paths.get("riven" + File.separator + "riven_mods_info.json")));
            JSONObject versionJson = new JSONObject(version);
            EmbedBuilder stat = new EmbedBuilder();


            stat.setTitle("Stat : ", "http://warframe.wikia.com/wiki/Riven_Mods");
            stat.setDescription("Riven : stat");
            stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
            stat.setColor(new Color(155, 55, 255));
            if (FindJsonKey(versionJson, "current version"))
                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
            else
                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            event.getTextChannel().sendMessage(stat.build()).queue();

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void Nom(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("riven" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);
                String version = new String(Files.readAllBytes(Paths.get("riven" + File.separator + "riven_mods_info.json")));
                JSONObject versionJson = new JSONObject(version);
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
                else {
                    if (cherche.equals("-"))
                        res += "- statistique négative, inconnue\n";
                    else if (cherche.length() == 0)
                        res += "\n\navec peut-être une statistique négative, inconnue\n";

                    res += "\nà savoir que les statistiques négatives n'apparaissent pas dans le nom d'un riven";
                }

                if (recupString(commande).length() < 6) nom.setTitle("Statistique : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                else nom.setTitle("Statistique pour le nom de riven : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                nom.addField("résultat :" , res, true);
                nom.setColor(new Color(155, 55, 255));
                if (FindJsonKey(versionJson, "current version"))
                    nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                else
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Stat(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContent().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("riven" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            String version = new String(Files.readAllBytes(Paths.get("riven" + File.separator + "riven_mods_info.json")));
            JSONObject versionJson = new JSONObject(version);
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

                                if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                                else
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                            }

                            stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                            stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            stat.setDescription("Riven : stat");
                            stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            stat.setColor(new Color(155, 55, 255));
                            if (FindJsonKey(versionJson, "current version"))
                                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                            else
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

                            if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                            else
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                        }

                        stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                        stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        stat.setDescription("Riven : stat");
                        stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        stat.setColor(new Color(155, 55, 255));
                        if (FindJsonKey(versionJson, "current version"))
                            stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                        else
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
                if (FindJsonKey(versionJson, "current version"))
                    stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | wf version : " + versionJson.getString("current version"), "http://i.imgur.com/BUkD1OV.png");
                else
                    stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(stat.build()).queue();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void Traite(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = recupString(event.getMessage().getContent().toLowerCase());
                String adresseSortie = System.getProperty("user.dir") + File.separator + "riven" + File.separator + "riven_mods_info.json";
                FileWriter rivenFile = new FileWriter(adresseSortie);
                JSONObject rivenJson = new JSONObject();

                if (commande.contains(" ")) {
                    String str = recupString(commande);

                    if (Traitement()) {
                        rivenJson.put("current version", str);
                        rivenJson.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date(Instant.now().toEpochMilli())));
                        rivenJson.put("heure", new SimpleDateFormat("HH:mm:ss").format(new Date(Instant.now().toEpochMilli())));

                        event.getTextChannel().sendMessage("Traitement effectué par rapport à l'update " + str).queue();
                    }
                }
                else {
                    if(Traitement()) {
                        rivenJson.put("date", new SimpleDateFormat("dd/MM/yyyy").format(new Date(Instant.now().toEpochMilli())));
                        rivenJson.put("heure", new SimpleDateFormat("HH:mm:ss").format(new Date(Instant.now().toEpochMilli())));

                        event.getTextChannel().sendMessage("Traitement effectué.").queue();
                    }
                }

                rivenFile.write(rivenJson.toString());
                rivenFile.flush();
                rivenFile.close();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
