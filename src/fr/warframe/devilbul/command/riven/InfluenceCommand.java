package fr.warframe.devilbul.command.riven;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.parser.RivenParser.*;
import static fr.warframe.devilbul.riven.TxtToJson.sortie;
import static fr.warframe.devilbul.utils.Find.findRivenJsonInfluenceCatgorie;
import static fr.warframe.devilbul.utils.Find.findRivenJsonInfluenceDouble;
import static fr.warframe.devilbul.utils.Find.findRivenJsonInfluenceTransforme;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class InfluenceCommand extends SimpleCommand {

    @SubCommand(name = "influence", commande = "riven")
    @Help(field = "**syntaxe** :      !riven influence <nom de l'objet>\n**effet :**           donne l'influence de l'objet <nom de l'objet>", categorie = Categorie.Riven)
    public static void influence(MessageReceivedEvent event) {
        try {
            String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

            if (commande.contains(" ")) {
                String cherche = recupString(commande);
                String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
                JSONObject rivenJson = new JSONObject(riven);

                if (findRivenJsonInfluenceTransforme(cherche) != null) {
                    EmbedBuilder influence = new EmbedBuilder();

                    influence.setTitle(parserCategorie(Objects.requireNonNull(findRivenJsonInfluenceCatgorie(cherche))) + " : " + cherche, "http://warframe.wikia.com/wiki/" + parserLowerToUpperCase(cherche));
                    influence.setDescription("Riven : influence");
                    influence.setThumbnail("http://warframe-builder.com/web/images/" + parserCategorieBuilder(cherche) + "/" + parserSpaceToUnderScore(cherche) + ".png");
                    influence.setColor(new Color(155, 55, 255));
                    influence.addField("résultat :", "**" + findRivenJsonInfluenceTransforme(cherche) + "** soit **" + findRivenJsonInfluenceDouble(cherche) + "** d'influence !", false);
                    if (findRivenJsonInfluenceDouble(cherche) >= 0 && findRivenJsonInfluenceDouble(cherche) < 0.7)
                        influence.setImage("http://i.imgur.com/0Hkjs2t.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 0.70 && findRivenJsonInfluenceDouble(cherche) < 0.875)
                        influence.setImage("http://i.imgur.com/eKbr2if.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 0.875 && findRivenJsonInfluenceDouble(cherche) < 1.125)
                        influence.setImage("http://i.imgur.com/nWnOJFb.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 1.125 && findRivenJsonInfluenceDouble(cherche) < 1.3)
                        influence.setImage("http://i.imgur.com/GhBf9df.png");
                    else if (findRivenJsonInfluenceDouble(cherche) >= 1.3 && findRivenJsonInfluenceDouble(cherche) < 2)
                        influence.setImage("http://i.imgur.com/cWaXulC.png");
                    influence.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                    event.getTextChannel().sendMessage(influence.build()).queue();
                } else {
                    StringBuilder str = new StringBuilder();
                    String[] armes;

                    for (Object name : rivenJson.names()) {
                        for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                            str.append(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)).append("$");
                    }

                    armes = str.toString().split("[$]");

                    event.getTextChannel().sendMessage(compareCommande(cherche, armes)).queue();
                    event.getTextChannel().sendMessage("erreur, l'objet saisi n'existe pas, ou est mal écrit").queue();
                }
            } else
                event.getTextChannel().sendMessage("aucun objet saisi").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
