package fr.warframe.devilbul.command.son;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.Bourreau.subCommands;
import static fr.warframe.devilbul.command.son.ChangeCommand.changeStateThread;
import static fr.warframe.devilbul.command.son.ListCommand.list;
import static fr.warframe.devilbul.command.son.SkipCommand.skip;
import static fr.warframe.devilbul.command.son.StopCommand.stop;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class SonCommand extends SimpleCommand {

    @Command(name = "son", subCommand = true)
    @Help(field = "**Coming son**", categorie = Categorie.Son)
    public static void son(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getMessage().getContentDisplay().contains(" ")) {
                    String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "change":
                            changeStateThread(event);
                            break;
                        case "list":
                            list(event);
                            break;
                        case "skip":
                            skip(event);
                            break;
                        case "stop":
                            stop(event);
                            break;
                        default:
                            MessageBuilder message = new MessageBuilder();

                            event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("son").toArray())).queue();
                            event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                            message.append("You know nothing, ");
                            message.append(event.getAuthor());

                            event.getTextChannel().sendMessage(message.build()).queue();
                            break;
                    }
                } else {
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Aucune action de son saisie").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getTextChannel().sendMessage(message.build()).queue();
                }
            } else event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
