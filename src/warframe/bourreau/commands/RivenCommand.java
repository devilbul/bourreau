package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.parser.RivenParser.*;
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

    }

    private static void Defi(MessageReceivedEvent event) {

    }

    private static void Influence(MessageReceivedEvent event) {
        String commande = event.getMessage().getContent().toLowerCase();

        if (commande.contains(" ")) {
            String cherche = recupString(recupString(commande));

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
                influence.setFooter(new java.text.SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new java.util.Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(influence.build()).queue();
            }
            else
                event.getTextChannel().sendMessage("erreur, l'objet saisi n'existe pas, ou est mal écrit").queue();
        }
        else
            event.getTextChannel().sendMessage("aucun objet saisi").queue();
    }

    private static void Nom(MessageReceivedEvent event) {
        try {
            String commande = event.getMessage().getContent().toLowerCase();

            if (commande.contains(" ")) {
                String riven = new String(Files.readAllBytes(Paths.get("output" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);
                EmbedBuilder nom = new EmbedBuilder();
                String cherche = recupString(recupString(commande));
                String[] stat;
                String rawStat = "";
                String res = "statitisque :\n";

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

                for (String aStat : stat)
                    res += "- " + aStat.split("% ")[1] + "\n";

                if (cherche.equals("-"))
                    res += "avec une statistique négative, inconnue\n";
                else if (cherche.length() == 0)
                    res += "avec peut-être une statistique négative, inconnue\n";

                res += "\nà savoir que les statistiques négatives n'apparaissent pas dans le nom d'un riven";

                nom.setTitle("Statistique pour le nom de riven : " + recupString(recupString(commande)), "http://warframe.wikia.com/wiki/Riven_Mods");
                nom.setDescription("Riven : nom / statistique");
                nom.setThumbnail("http://i.imgur.com/stLoMSN.png");
                nom.addField("résultat :" , res, true);
                nom.setColor(new Color(155, 55, 255));
                nom.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(nom.build()).queue();

            } else
                event.getTextChannel().sendMessage("aucun objet saisi").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void Stat(MessageReceivedEvent event) {

    }
}
