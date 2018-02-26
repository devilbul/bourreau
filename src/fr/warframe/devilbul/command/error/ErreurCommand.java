package fr.warframe.devilbul.command.error;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.Bourreau.subCommands;
import static fr.warframe.devilbul.command.error.DeleteErreurCommand.deleteErreur;
import static fr.warframe.devilbul.command.error.DetailErreurCommand.detailErreur;
import static fr.warframe.devilbul.command.error.ListErreurCommand.listErreur;
import static fr.warframe.devilbul.command.error.PurgeErreurCommand.purgeErreur;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ErreurCommand extends SimpleCommand {

    @Command(name = "erreur", subCommand = true)
    @Help(field = "", categorie = Categorie.Supreme)
    public static void erreur(MessageReceivedEvent event) {
        if (findAdminSupreme(event.getAuthor().getId())) {
            if (event.getMessage().getContentDisplay().contains(" ")) {
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
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }
}
