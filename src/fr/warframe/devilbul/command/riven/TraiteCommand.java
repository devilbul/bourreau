package fr.warframe.devilbul.command.riven;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.tuple.Pair;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static net.dv8tion.jda.core.utils.tuple.Pair.of;

public class TraiteCommand extends SimpleCommand {

    @SubCommand(name = "traite", commande = "riven")
    @Help(field = "**syntaxe** :      !riven traite\n**effet :**           traite les nouvelles valeurs des rivens", categorie = Categorie.Admin)
    public static void traite(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String adresse = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "RivenMods.json")));
                String adresseSortie = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "info" + File.separator + "RivenMods.json";
                String adressePrimary = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven_primary.txt";
                String adresseMelee = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven_melee.txt";
                String adresseSecondary = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven_secondary.txt";
                String adresseShotgun = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "riven" + File.separator + "riven_shotgun.txt";
                FileWriter file = new FileWriter(adresseSortie);
                JSONObject rivenJson = new JSONObject(adresse);
                Scanner scanner;
                String line, arme;
                File currentFile;
                boolean fini = false;
                List<Pair<String, String>> categoryRiven = Arrays.asList(of("rifle", adressePrimary), of("melee", adresseMelee), of("pistol", adresseSecondary), of("shotgun", adresseShotgun));

                for (Pair<String, String> category : categoryRiven) {
                    fini = false;

                    if ((currentFile = new File(category.getRight())).exists()) {
                        scanner = new Scanner(currentFile);

                        while (scanner.hasNext()) {
                            line = scanner.nextLine().toLowerCase();
                            arme = line.replaceFirst(" ", "@").split("@")[1];

                            if (findValueObjectList(rivenJson.getJSONObject("melee").getJSONObject("item").names().toList(), arme))
                                rivenJson.getJSONObject(category.getLeft()).getJSONObject("item").put(arme, String.valueOf((100 + Integer.valueOf(line.split(" ")[0])) / 100.0));
                            else
                                rivenJson.getJSONObject(category.getLeft()).getJSONObject("item").put(arme, String.valueOf((100 + Integer.valueOf(line.split(" ")[0])) / 100.0));
                        }

                        fini = true;
                    }
                }

                if (fini)
                    event.getTextChannel().sendMessage("Traitement effectué.").queue();

                file.write(rivenJson.toString(3));
                file.flush();
                file.close();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
