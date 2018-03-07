package fr.warframe.devilbul.command.sondage;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;

import static fr.warframe.devilbul.Bourreau.subCommands;
import static fr.warframe.devilbul.command.sondage.ClearCommand.clearSondage;
import static fr.warframe.devilbul.command.sondage.CreateCommand.createSondage;
import static fr.warframe.devilbul.command.sondage.DisplayCommand.afficheSondage;
import static fr.warframe.devilbul.command.sondage.ResponsesCommand.reponsesSondage;
import static fr.warframe.devilbul.command.sondage.ResultCommand.resultatSondage;
import static fr.warframe.devilbul.command.sondage.VoteCommand.voteSondage;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findModo;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class SondageCommand extends SimpleCommand {

    @Command(name = "sondage", subCommand = true)
    @Help(field = "\nPour créer un sondage :\n- **!sondage create <question>**, pour créer le sondage," +
            "\n- **!sondage reponses <réponse1> / <réponse2> / ... / <réponseN>**, pour déclarer les réponses au \n   sondage" +
            "\n\naprès avoir fait ses deux commandes, le sondage est correctement déclaré\nil sera épinglé dans le salon textuel dans lequel la commande était faite" +
            "\n\npour voter :\n**!sondage vote <réponse>**\n\npour le supprimer :\n**!sondage clear**\n\npour l'afficher :\n**!sondage affiche**" +
            "\n\npour voir les résultat :\n**!sondage resultat**\n\nles réponses peuvent être des phrases \nil faut bien respecter la syntaxe" +
            "\nc'est-à-dire mettre ' / ' pour séparer chaque réponse\n\n**!help sondage <nom_de_la_commande>** pour plus d'information sur la commande.", categorie = Categorie.Sondage)
    public static void sondage(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getMessage().getContentDisplay().contains(" ")) {
                    String adresseSondage = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "sondage" + File.separator + "sondage.json";
                    String adresseVote = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "sondage" + File.separator + "vote.json";
                    String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "affiche":
                            afficheSondage(event);
                            break;
                        case "reponses":
                            if (reponsesSondage(event, adresseSondage, adresseVote))
                                afficheSondage(event);
                            break;
                        case "clear":
                            clearSondage(event, adresseSondage, adresseVote);
                            break;
                        case "create":
                            createSondage(event, adresseSondage, adresseVote);
                            break;
                        case "resultat":
                            resultatSondage(event);
                            break;
                        case "vote":
                            voteSondage(event, adresseVote);
                            break;
                        default:
                            MessageBuilder message = new MessageBuilder();

                            event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("sondage").toArray())).queue();
                            event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                            message.append("You know nothing, ");
                            message.append(event.getAuthor());

                            event.getTextChannel().sendMessage(message.build()).queue();
                            break;
                    }
                } else {
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Aucune action de sondage saisie").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getTextChannel().sendMessage(message.build()).queue();
                }
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
