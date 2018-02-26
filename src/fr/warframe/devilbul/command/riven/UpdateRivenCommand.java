package fr.warframe.devilbul.command.riven;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import static fr.warframe.devilbul.command.riven.TraiteCommand.traite;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.riven.JsonToTxt.traitement;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findRivenScript;

public class UpdateRivenCommand extends SimpleCommand {

    @SubCommand(name = "update", commande = "riven")
    @Help(field = "**syntaxe** :      !riven update <catégorie>" + "\n**effet :**           donne toutes les statistique pour la catégorie <catégorie>", categorie = Categorie.Admin)
    public static void update(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String adresse = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven.json";

                parseHTML(event);
                traitement();

                if (new File(adresse).exists())
                  new File(adresse).delete();

                traite(event);
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }

    private static void parseHTML(MessageReceivedEvent event) {
        try {
            String url = "https://semlar.com/rivencalc";
            Document doc = Jsoup.connect(url).get();
            Elements scripts = doc.select("script");
            Element scriptRiven = scripts.get(findRivenScript(scripts));

            String adresse = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven.html";
            String adresseJson = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven.json";
            FileWriter file = new FileWriter(adresse), fileJson = new FileWriter(adresseJson);
            File fileRead = new File(adresse);
            Scanner scan = new Scanner(fileRead);
            boolean isCopying = true, isCom = false;
            String nextLine, previous = "";

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
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
