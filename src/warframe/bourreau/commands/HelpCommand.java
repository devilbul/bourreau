package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import warframe.bourreau.util.Command;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.help.EmbedAdminHelp.MessageHelpAdmin;
import static warframe.bourreau.help.EmbedAdminList.MessageListeCommandeAdmin;
import static warframe.bourreau.help.EmbedGeneralHelp.MessageHelpGeneral;
import static warframe.bourreau.help.EmbedCommandList.MessageListeCommande;
import static warframe.bourreau.help.EmbedModoHelp.MessageHelpModo;
import static warframe.bourreau.help.EmbedModoList.MessageListeCommandeModo;
import static warframe.bourreau.help.EmbedRaidHelp.MessageHelpRaid;
import static warframe.bourreau.help.EmbedRivenHelp.MessageHelpRiven;
import static warframe.bourreau.help.EmbedSonHelp.MessageHelpSon;
import static warframe.bourreau.help.EmbedSondageHelp.MessageHelpSondage;
import static warframe.bourreau.help.EmbedTrollHelp.MessageHelpTroll;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class HelpCommand extends SimpleCommand {

    @Command(name="help")
    public static void Help(MessageReceivedEvent event) {
        try {
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
                    case "rip":
                    case "segpa":
                    case "tg":
                        event.getTextChannel().sendMessage(MessageHelpTroll(help)).queue();
                        break;
                    // commandes que tout le monde peut faire
                    case "alerts":
                    case "alliance":
                    case "baro":
                    case "clan":
                    case "discordwf":
                    case "goals":
                    case "idee":
                    case "info":
                    case "invasions":
                    case "invite":
                    case "lead":
                    case "progres":
                    case "pvp":
                    case "pvp hebdo":
                    case "raid":
                    case "regle":
                    case "site":
                    case "steam":
                    case "sortie":
                    case "syndicat":
                    case "ts3":
                    case "up":
                    case "updates":
                    case "void":
                        event.getTextChannel().sendMessage(MessageHelpGeneral(help)).queue();
                        break;
                    // commande riven
                    case "riven":
                    case "riven calcul":
                    case "riven defi":
                    case "riven influence":
                    case "riven info":
                    case "riven nom":
                    case "riven stat":
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
            } else {
                if (FindAdmin(event, event.getMember())) {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageListeCommandeAdmin()).queue();
                } else if (FindModo(event, event.getMember())) {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageListeCommandeModo()).queue();
                }

                event.getTextChannel().sendMessage(MessageListeCommande(false, false)).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
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
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpSon(help)).queue();
                    break;
                // commande troll
                case "pute":
                case "rip":
                case "segpa":
                case "tg":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpTroll(help)).queue();
                    break;
                // commandes que tout le monde peut faire
                case "alerts":
                case "alliance":
                case "baro":
                case "clan":
                case "discordwf":
                case "goals":
                case "idee":
                case "info":
                case "invasions":
                case "invite":
                case "lead":
                case "progres":
                case "pvp":
                case "pvp hebdo":
                case "raid":
                case "regle":
                case "site":
                case "steam":
                case "sortie":
                case "syndicat":
                case "ts3":
                case "up":
                case "updates":
                case "void":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpGeneral(help)).queue();
                    break;
                // commande riven
                case "riven":
                case "riven calcul":
                case "riven defi":
                case "riven influence":
                case "riven info":
                case "riven nom":
                case "riven stat":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpRiven(help)).queue();
                    break;
                // commande sondage
                case "sondage":
                case "sondage affiche":
                case "sondage reponses":
                case "sondage clear":
                case "sondage create":
                case "sondage resultat":
                case "sondage vote":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpSondage(help)).queue();
                    break;
                // commande raid
                case "affiche":
                case "cancel":
                case "present":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpRaid(help)).queue();
                    break;
                // commande admin / modo
                case "aubucher":
                case "deafen":
                case "kick":
                case "mute":
                case "tenno":
                case "undeafen":
                case "unmute":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpAdmin(help)).queue();
                    break;
                case "addclan":
                case "ban":
                case "removeclan":
                case "setgame":
                case "unban":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(MessageHelpModo(help)).queue();
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    message.append("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.");
                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getAuthor().openPrivateChannel().complete().sendMessage(message.build()).queue();
                    break;
            }
        }
        else
            event.getAuthor().openPrivateChannel().complete().sendMessage(MessageListeCommande(FindAdminPrive(event, event.getAuthor()), FindModoPrive(event, event.getAuthor()))).queue();
    }
}
