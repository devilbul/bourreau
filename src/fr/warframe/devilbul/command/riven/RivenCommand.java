package fr.warframe.devilbul.command.riven;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.Bourreau.subCommands;
import static fr.warframe.devilbul.command.riven.CalculCommand.calcul;
import static fr.warframe.devilbul.command.riven.InfluenceCommand.influence;
import static fr.warframe.devilbul.command.riven.TraiteCommand.traite;
import static fr.warframe.devilbul.command.riven.UpdateRivenCommand.update;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class RivenCommand extends SimpleCommand {

    @Command(name = "riven", subCommand = true)
    @Help(field = "**Coming soon**", categorie =  Categorie.Riven)
    public static void riven(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                switch (commande) {
                    case "calcul":
                        calcul(event);
                        break;
                    case "influence":
                        influence(event);
                        break;
                    case "traite":
                        traite(event);
                        break;
                    case "update":
                        update(event);
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("riven").toArray())).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }
            } else {
                MessageBuilder message = new MessageBuilder();

                event.getTextChannel().sendMessage("Aucune action de riven saisie").queue();

                message.append("You know nothing, ");
                message.append(event.getAuthor());

                event.getTextChannel().sendMessage(message.build()).queue();
            }
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
