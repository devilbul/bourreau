package fr.warframe.devilbul;

import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.riven.TxtToJson.sortie;
import static fr.warframe.devilbul.utils.Find.findJsonKey;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class EnCoursTest {

    /**
     * permet de upload un fichier sur le serveur ou le bot est excécuté
     */

    /*System.out.println(event.getMessage().getContentDisplay());
                for (Message.Attachment attachment : event.getMessage().getAttachments()) {
        String testName = attachment.getFileName();
        //File test = new File(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "music.download" + File.separator + testName);
        System.out.println(testName);
        //attachment.music.download(test);
        test(attachment.getInputStream(), testName);
    }*/
    public void test(InputStream in, String fileName) {
        OutputStream out = null;

        try {
            System.out.println(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "music" + File.separator + "download" + File.separator + fileName);
            File test = new File(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "music" + File.separator + "download" + File.separator + fileName);

            if (!test.getParentFile().mkdirs()) return;
            if (!test.createNewFile()) return;
            if (!test.exists()) return;

            out = new FileOutputStream(test);

            byte[] buffer = new byte[4096];
            int len;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @SubCommand(name = "nom", commande = "riven")
    @Help(field = "**syntaxe** :      !riven nom <nom du riven>\n**effet :**           donne les statistiques par rapport au nom du riven <nom de riven>", categorie = Categorie.Riven)
    public static void nom(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
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

                if (recupString(commande).length() < 6)
                    nom.setTitle("Statistique : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                else
                    nom.setTitle("Statistique pour le nom de riven : " + recupString(commande), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                nom.addField("résultat :", res.toString(), true);
                nom.setColor(new Color(155, 55, 255));
                nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                if (cherche.toString().contains("a") || cherche.toString().contains("b") || cherche.toString().contains("c") || cherche.toString().contains("d") || cherche.toString().contains("e") || cherche.toString().contains("f") || cherche.toString().contains("g") || cherche.toString().contains("h")
                        || cherche.toString().contains("i") || cherche.toString().contains("j") || cherche.toString().contains("k") || cherche.toString().contains("l") || cherche.toString().contains("m") || cherche.toString().contains("n") || cherche.toString().contains("o") || cherche.toString().contains("p")
                        || cherche.toString().contains("q") || cherche.toString().contains("r") || cherche.toString().contains("s") || cherche.toString().contains("t") || cherche.toString().contains("u") || cherche.toString().contains("v") || cherche.toString().contains("w") || cherche.toString().contains("x")
                        || cherche.toString().contains("y") || cherche.toString().contains("z"))
                    event.getTextChannel().sendMessage("faute d'orthographe sur le nom saisi").queue();
                else
                    event.getTextChannel().sendMessage(nom.build()).queue();
            } else
                event.getTextChannel().sendMessage("aucun nom de riven saisi").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @SubCommand(name = "stat", commande = "riven")
    @Help(field = "**syntaxe** :      !riven stat <catégorie>\n**effet :**           donne toutes les statistique pour la catégorie <catégorie>", categorie =  Categorie.Riven)
    public static void stat(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
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
                                } else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                                else
                                    stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                            }

                            stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                            stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                            stat.setDescription("Riven : stat");
                            stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                            stat.setColor(new Color(155, 55, 255));
                            stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                            event.getTextChannel().sendMessage(stat.build()).queue();
                        } else
                            event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                    } else
                        event.getTextChannel().sendMessage("essaye plutôt avec \"detail\"").queue();
                } else {
                    if (findJsonKey(rivenJson, categorie)) {
                        for (int i = 0; i < rivenJson.getJSONObject(categorie).getJSONArray("upgrades").length(); i++) {
                            stats = "";

                            stats += "peut-être un buff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be buff");
                            stats += "\npeut-être un debuff : " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("can be curse");

                            if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("|")) {
                                String[] str = rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("[|]");

                                stat.addField("- " + str[0] + str[2], stats, false);
                            } else if (rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").contains("%"))
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").split("% ")[1], stats, false);
                            else
                                stat.addField("- " + rivenJson.getJSONObject(categorie).getJSONArray("upgrades").getJSONObject(i).getString("upgrade").replaceFirst(" ", "%").split("%")[1], stats, false);
                        }

                        stat.addField("pour plus de détail :", "tapez : **__!riven stat " + categorie + " detail__**", false);
                        stat.setTitle("Stat pour la catégorie : " + categorie, "http://warframe.wikia.com/wiki/Riven_Mods");
                        stat.setDescription("Riven : stat");
                        stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                        stat.setColor(new Color(155, 55, 255));
                        stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                        event.getTextChannel().sendMessage(stat.build()).queue();
                    } else
                        event.getTextChannel().sendMessage("la catégorie saisie n'existe pas, ou est mal orthographié").queue();
                }
            } else {
                StringBuilder mes = new StringBuilder();

                for (Object name : rivenJson.names()) mes.append("- ").append(name.toString()).append("\n");

                stat.setTitle("Stat : ", "http://warframe.wikia.com/wiki/Riven_Mods");
                stat.setDescription("Riven : stat");
                stat.setThumbnail("http://i.imgur.com/2o2SbcK.png");
                stat.addField("veuillez indiquer une catégorie, de la liste suivante :", mes.toString(), true);
                stat.addField("afin d'afficher tous les statistique possibles pour cette catégorie d'objet", "", true);
                stat.addField("**__!riven stat <categorie>__**", "", true);
                stat.setColor(new Color(155, 55, 255));
                stat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                event.getTextChannel().sendMessage(stat.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @SubCommand(name = "info", commande = "riven")
    @Help(field = "**syntaxe** :      !riven info <catégorie>\n**effet :**           donne toutes les informations sur la catégorie <catégorie>",categorie = Categorie.Riven)
    public static void info(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
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
                    info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
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
                info.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
            }

            event.getTextChannel().sendMessage(info.build()).queue();

        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */

    @SubCommand(name="defi", commande = "riven")
    @Help(field = "**syntaxe** :      !riven defi\n**effet :**           affiche tous les défis possible", categorie = Categorie.Riven)
    public static void defi(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
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
                        defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
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
                            defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);
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
                defi.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                event.getTextChannel().sendMessage(defi.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    /**
     * ---------------------------------------------------------------------------------------------------------------
     */
}
