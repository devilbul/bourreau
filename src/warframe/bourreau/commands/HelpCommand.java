package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import static warframe.bourreau.help.EmbedMessageHelp.*;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class HelpCommand extends Command {

    public static void Help(MessageReceivedEvent event) {
        if (event.getMessage().getContent().contains(" ")) {
            String help = recupString(event.getMessage().getContent().toLowerCase());

            switch (help) {
                // command son
                case "ah":
                case "bucher":
                case "gg":
                case "gogole":
                case "nah":
                case "pigeon":
                case "souffrir":
                case "trump":
                    event.getTextChannel().sendMessage(MessageHelpSon(help)).queue();
                    break;
                // commande troll
                case "pute":
                case "RIP":
                case "rip":
                case "segpa":
                case "tg":
                    event.getTextChannel().sendMessage(MessageHelpTroll(help)).queue();
                    break;
                // commandes que tout le monde peut faire
                case "alliance":
                case "baro":
                case "clan":
                case "discordwf":
                case "idée":
                case "info":
                case "invite":
                case "lead":
                case "progres":
                case "pvp":
                case "pvp hebdo":
                case "raid":
                case "site":
                case "steam":
                case "sortie":
                case "syndicat":
                case "ts3":
                case "up":
                case "updates":
                    event.getTextChannel().sendMessage(MessageHelpGeneral(help)).queue();
                    break;
                // commande riven
                case "riven":
                case "riven defi":
                case "riven influence":
                case "riven nom":
                    event.getTextChannel().sendMessage(MessageHelpRiven(help)).queue();
                    break;
                // commande sondage
                case "sondage":
                case "sondage affiche":
                case "sondage reponses":
                case "sondage clear":
                case "sondage create":
                case "sondage resultat":
                case "sondage vote":
                    event.getTextChannel().sendMessage(MessageHelpSondage(help)).queue();
                    break;
                // commande raid
                case "affiche":
                case "cancel":
                case "present":
                    event.getTextChannel().sendMessage(MessageHelpRaid(help)).queue();
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes  (sur un salon textuel ou en message privé). \nPS : apprends à écrire.").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getTextChannel().sendMessage(message.build()).queue();
                    break;
            }
        }
        else {
            if (FindAdmin(event, event.getMember())) {
                event.getAuthor().openPrivateChannel().complete();
                event.getAuthor().getPrivateChannel().sendMessage(MessageListeCommandeAdmin()).queue();
                event.getAuthor().getPrivateChannel().close();
            } else if (FindModo(event, event.getMember())) {
                event.getAuthor().openPrivateChannel().complete();
                event.getAuthor().getPrivateChannel().sendMessage(MessageListeCommandeModo()).queue();
                event.getAuthor().getPrivateChannel().close();
            }

            event.getTextChannel().sendMessage(MessageListeCommande(false, false)).queue();
        }
    }

    public static void HelpPrivate(PrivateMessageReceivedEvent event) {
        if (event.getMessage().getContent().contains(" ")) {
            String help = recupString(event.getMessage().getContent().toLowerCase());

            switch (help) {
                // command son
                case "ah":
                case "bucher":
                case "gg":
                case "gogole":
                case "nah":
                case "pigeon":
                case "souffrir":
                case "trump":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpSon(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commande troll
                case "pute":
                case "RIP":
                case "rip":
                case "segpa":
                case "tg":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpTroll(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commandes que tout le monde peut faire
                case "alliance":
                case "baro":
                case "clan":
                case "discordwf":
                case "idée":
                case "info":
                case "invite":
                case "lead":
                case "progres":
                case "pvp":
                case "pvp hebdo":
                case "raid":
                case "site":
                case "steam":
                case "sortie":
                case "syndicat":
                case "ts3":
                case "up":
                case "updates":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpGeneral(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commande riven
                case "riven":
                case "riven defi":
                case "riven influence":
                case "riven nom":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpRiven(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commande sondage
                case "sondage":
                case "sondage affiche":
                case "sondage reponses":
                case "sondage clear":
                case "sondage create":
                case "sondage resultat":
                case "sondage vote":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpSondage(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commande raid
                case "affiche":
                case "cancel":
                case "present":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpRaid(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                // commande admin / modo
                case "aubucher":
                case "deafen":
                case "getbans":
                case "kick":
                case "mute":
                case "tenno":
                case "undeafen":
                case "unmute":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpAdmin(help)).queue();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpModo(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                case "addclan":
                case "ban":
                case "removeclan":
                case "setgame":
                case "unban":
                    event.getAuthor().openPrivateChannel().complete();
                    event.getAuthor().getPrivateChannel().sendMessage(MessageHelpAdmin(help)).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    event.getAuthor().openPrivateChannel().complete();

                    event.getAuthor().getPrivateChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getAuthor().getPrivateChannel().sendMessage(message.build()).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
            }
        }
        else {
            event.getAuthor().openPrivateChannel().complete();
            event.getAuthor().getPrivateChannel().sendMessage(MessageListeCommande(FindAdminPrive(event, event.getAuthor()), FindModoPrive(event, event.getAuthor()))).queue();
            event.getAuthor().getPrivateChannel().close();
        }
    }
}
