package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import warframe.bourreau.util.Command;

import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.help.EmbedAdminHelp.messageHelpAdmin;
import static warframe.bourreau.help.EmbedAdminList.messageListeCommandeAdmin;
import static warframe.bourreau.help.EmbedGeneralHelp.messageHelpGeneral;
import static warframe.bourreau.help.EmbedCommandList.messageListeCommande;
import static warframe.bourreau.help.EmbedModoHelp.messageHelpModo;
import static warframe.bourreau.help.EmbedModoList.messageListeCommandeModo;
import static warframe.bourreau.help.EmbedRaidHelp.messageHelpRaid;
import static warframe.bourreau.help.EmbedRivenHelp.messageHelpRiven;
import static warframe.bourreau.help.EmbedSonHelp.messageHelpSon;
import static warframe.bourreau.help.EmbedSondageHelp.messageHelpSondage;
import static warframe.bourreau.help.EmbedTrollHelp.messageHelpTroll;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class HelpCommand extends SimpleCommand {

    @Command(name="help", subCommand=false)
    public static void help(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContentDisplay().contains(" ")) {
                String help = recupString(event.getMessage().getContentDisplay().toLowerCase());

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
                        event.getTextChannel().sendMessage(messageHelpSon(help)).queue();
                        break;
                    // commande troll
                    case "pute":
                    case "rip":
                    case "segpa":
                    case "tg":
                        event.getTextChannel().sendMessage(messageHelpTroll(help)).queue();
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
                        event.getTextChannel().sendMessage(messageHelpGeneral(help)).queue();
                        break;
                    // commande riven
                    case "riven":
                    case "riven calcul":
                    case "riven defi":
                    case "riven influence":
                    case "riven info":
                    case "riven nom":
                    case "riven stat":
                        event.getTextChannel().sendMessage(messageHelpRiven(help)).queue();
                        break;
                    // commande sondage
                    case "sondage":
                    case "sondage affiche":
                    case "sondage reponses":
                    case "sondage clear":
                    case "sondage create":
                    case "sondage resultat":
                    case "sondage vote":
                        event.getTextChannel().sendMessage(messageHelpSondage(help)).queue();
                        break;
                    // commande raid
                    case "affiche":
                    case "cancel":
                    case "present":
                        event.getTextChannel().sendMessage(messageHelpRaid(help)).queue();
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes  (sur un salon textuel ou en message privé).").queue();
                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }
            } else {
                if (findAdmin(event, event.getMember())) {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageListeCommandeAdmin()).queue();
                } else if (findModo(event, event.getMember())) {
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageListeCommandeModo()).queue();
                }

                event.getTextChannel().sendMessage(messageListeCommande(false, false)).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void helpPrivate(PrivateMessageReceivedEvent event) {
        if (event.getMessage().getContentDisplay().contains(" ")) {
            String help = recupString(event.getMessage().getContentDisplay().toLowerCase());

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
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpSon(help)).queue();
                    break;
                // commande troll
                case "pute":
                case "rip":
                case "segpa":
                case "tg":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpTroll(help)).queue();
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
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpGeneral(help)).queue();
                    break;
                // commande riven
                case "riven":
                case "riven calcul":
                case "riven defi":
                case "riven influence":
                case "riven info":
                case "riven nom":
                case "riven stat":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpRiven(help)).queue();
                    break;
                // commande sondage
                case "sondage":
                case "sondage affiche":
                case "sondage reponses":
                case "sondage clear":
                case "sondage create":
                case "sondage resultat":
                case "sondage vote":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpSondage(help)).queue();
                    break;
                // commande raid
                case "affiche":
                case "cancel":
                case "present":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpRaid(help)).queue();
                    break;
                // commande admin / modo
                case "aubucher":
                case "deafen":
                case "kick":
                case "mute":
                case "tenno":
                case "undeafen":
                case "unmute":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpAdmin(help)).queue();
                    break;
                case "addclan":
                case "ban":
                case "removeclan":
                case "setgame":
                case "unban":
                    event.getAuthor().openPrivateChannel().complete().sendMessage(messageHelpModo(help)).queue();
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    message.append("Commande inconnue. !help pour lister les commandes.");
                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getAuthor().openPrivateChannel().complete().sendMessage(message.build()).queue();
                    break;
            }
        }
        else
            event.getAuthor().openPrivateChannel().complete().sendMessage(messageListeCommande(findAdminPrive(event, event.getAuthor()), findModoPrive(event, event.getAuthor()))).queue();
    }
}
