package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.SubCommand;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.Bourreau.subCommands;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class ErreurCommand extends SimpleCommand {

    @Command(name="erreur", subCommand=true)
    public static void erreur(MessageReceivedEvent event) {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                switch (commande) {
                    case "list":
                        listErreur(event);
                        break;
                    case "detail":
                        detailErreur(event);
                        break;
                    case "delete":
                        deleteErreur(event);
                        break;
                    case "purge":
                        purgeErreur(event);
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("erreur").toArray())).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    @SubCommand(name="list")
    private static void listErreur(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String erreur = new String(Files.readAllBytes(Paths.get("res" + File.separator + "erreur" + File.separator + "Erreur.json")));
                JSONObject erreurJson = new JSONObject(erreur);
                EmbedBuilder message = new EmbedBuilder();
                String erreurName;

                message.setTitle("Liste erreur :", null);
                message.setDescription("erreurs  survenues");
                message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
                message.setColor(new Color(178, 10, 22));

                if (erreurJson.length() == 0)
                    event.getTextChannel().sendMessage("Aucune erreur enregistrée").queue();
                else {
                    for (int i = 0; i < erreurJson.names().length(); i++) {
                        erreurName = erreurJson.names().getString(i);

                        message.addField(erreurName, erreurJson.getJSONObject(erreurName).getString("date") + " " + erreurJson.getJSONObject(erreurName).getString("heure") + "\n" +
                                erreurJson.getJSONObject(erreurName).getString("commande") + "\n", false);
                    }

                    event.getTextChannel().sendMessage(message.build()).queue();
                }
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SubCommand(name="detail")
    private static void detailErreur(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

                if (commande.contains(" ")) {
                    String erreurID = "erreur " + recupString(commande);
                    String erreur = new String(Files.readAllBytes(Paths.get("res" + File.separator + "erreur" + File.separator + "Erreur.json")));
                    JSONObject erreurJson = new JSONObject(erreur);

                    if (findJsonKey(erreurJson, erreurID)) {
                        EmbedBuilder message = new EmbedBuilder();

                        message.setTitle("Detail erreur :", null);
                        message.setDescription("commande : " + erreurJson.getJSONObject(erreurID).getString("commande"));
                        message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
                        message.setColor(new Color(178, 10, 22));

                        message.addField("date :", erreurJson.getJSONObject(erreurID).getString("date") + " " + erreurJson.getJSONObject(erreurID).getString("heure"), false);
                        message.addField("auteur", erreurJson.getJSONObject(erreurID).getString("auteur commande (name)"), false);
                        message.addField("text channel", erreurJson.getJSONObject(erreurID).getString("text channel"), false);

                        if (findValueObjectList(erreurJson.getJSONObject(erreurID).names().toList(), "message erreur"))
                            message.addField("message erreur", erreurJson.getJSONObject(erreurID).getString("message erreur"), false);

                        event.getTextChannel().sendMessage(message.build()).queue();
                    }
                    else
                        event.getTextChannel().sendMessage("ID erreur incorrect.").queue();
                }
                else
                    event.getTextChannel().sendMessage("Aucune erreur saisie.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SubCommand(name="delete")
    private static void deleteErreur(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

                if (commande.contains(" ")) {
                    String erreurID = "erreur " + recupString(commande);
                    String erreur = new String(Files.readAllBytes(Paths.get("res" + File.separator + "erreur" + File.separator + "Erreur.json")));
                    JSONObject erreurJson = new JSONObject(erreur);

                    if (findJsonKey(erreurJson, erreurID)) {
                        String adresseErreur = System.getProperty("user.dir") + File.separator + "res" + File.separator + "erreur" + File.separator + "Erreur.json";
                        FileWriter file = new FileWriter(adresseErreur);

                        erreurJson.remove(erreurID);

                        file.write(erreurJson.toString(3));
                        file.flush();
                        file.close();

                        event.getTextChannel().sendMessage("Erreur " + recupString(commande) + " a été supprimé.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("ID erreur incorrect.").queue();
                }
                else
                    event.getTextChannel().sendMessage("Aucune erreur saisie.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @SubCommand(name="purge")
    private static void purgeErreur(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                JSONObject erreurJson = new JSONObject();
                String adresseErreur = System.getProperty("user.dir") + File.separator + "res" + File.separator + "erreur" + File.separator + "Erreur.json";
                FileWriter file = new FileWriter(adresseErreur);

                file.write(erreurJson.toString(3));
                file.flush();
                file.close();

                event.getTextChannel().sendMessage("Toutes les erreurs ont été supprimées.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
