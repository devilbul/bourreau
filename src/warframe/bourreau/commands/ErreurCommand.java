package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Find.FindJsonKey;
import static warframe.bourreau.util.Find.FindModo;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class ErreurCommand extends SimpleCommand {

    @Command(name="erreur")
    public static void Erreur(MessageReceivedEvent event) {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
                String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                switch (commande) {
                    case "liste":
                        ListErreur(event);
                        break;
                    case "detail":
                        DetailErreur(event);
                        break;
                    case "delete":
                        DeleteErreur(event);
                        break;
                    case "purge":
                        PurgeErreur(event);
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();
                        String[] commandeErreur = {"liste", "detail","delete"};

                        event.getTextChannel().sendMessage(CompareCommande(commande, commandeErreur)).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    private static void ListErreur(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String erreur = new String(Files.readAllBytes(Paths.get("erreur" + File.separator + "Erreur.json")));
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

    private static void DetailErreur(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = recupString(event.getMessage().getContent().toLowerCase());

                if (commande.contains(" ")) {
                    String erreurID = "erreur " + recupString(commande);
                    String erreur = new String(Files.readAllBytes(Paths.get("erreur" + File.separator + "Erreur.json")));
                    JSONObject erreurJson = new JSONObject(erreur);

                    if (FindJsonKey(erreurJson, erreurID)) {
                        EmbedBuilder message = new EmbedBuilder();

                        message.setTitle("Detail erreur :", null);
                        message.setDescription("commande : " + erreurJson.getJSONObject(erreurID).getString("commande"));
                        message.setThumbnail("http://forgedesheros.fr/wp-content/uploads/2014/08/warning.png");
                        message.setColor(new Color(178, 10, 22));

                        message.addField("date :", erreurJson.getJSONObject(erreurID).getString("date") + " " + erreurJson.getJSONObject(erreurID).getString("heure"), false);
                        message.addField("auteur", erreurJson.getJSONObject(erreurID).getString("auteur commande (name)"), false);
                        message.addField("text channel", erreurJson.getJSONObject(erreurID).getString("text channel"), false);
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

    private static void DeleteErreur(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = recupString(event.getMessage().getContent().toLowerCase());

                if (commande.contains(" ")) {
                    String erreurID = "erreur " + recupString(commande);
                    String erreur = new String(Files.readAllBytes(Paths.get("erreur" + File.separator + "Erreur.json")));
                    JSONObject erreurJson = new JSONObject(erreur);

                    if (FindJsonKey(erreurJson, erreurID)) {
                        String adresseErreur = System.getProperty("user.dir") + File.separator + "erreur" + File.separator + "Erreur.json";
                        FileWriter file = new FileWriter(adresseErreur);

                        erreurJson.remove(erreurID);

                        file.write(erreurJson.toString());
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

    private static void PurgeErreur(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                JSONObject erreurJson = new JSONObject();
                String adresseErreur = System.getProperty("user.dir") + File.separator + "erreur" + File.separator + "Erreur.json";
                FileWriter file = new FileWriter(adresseErreur);

                file.write(erreurJson.toString());
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
