package warframe.bourreau.handle;

import warframe.bourreau.commands.*;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadAuBucher;

import static warframe.bourreau.Main.commands;
import static warframe.bourreau.handle.HandleSonCommand.handleSonCommand;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThing;

public class HandleCommand {

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = Command.called();

            if (safe) {
                Thread aubucher = new ThreadAuBucher(cmd);

                switch (cmd.invoke) {
                    // liste des commandes
                    case "help":
                        HelpCommand.Help(cmd.event);
                        break;

                    case "ah":
                    case "bucher":
                    case "gg":
                    case "gogole":
                    case "nah":
                    case "pigeon":
                    case "son":
                    case "souffrir":
                    case "leave":
                    case "trump":
                    case "trumpcomp":
                    case "trumpcomp2":
                    case "trumpcomp3":
                        handleSonCommand(cmd);
                        break;

                    // commandes troll
                    case "pute":
                        TrollCommand.Pute(cmd.event);
                        break;
                    case "RIP":
                    case "rip":
                        TrollCommand.RIP(cmd.event);
                        break;
                    case "segpa":
                        TrollCommand.Segpta(cmd.event);
                        break;
                    case "tg":
                        TrollCommand.Tg(cmd.event);
                        break;

                    // commande info
                    case "alerts":
                        InfoCommand.Alerts(cmd.event);
                        break;
                    case "alliance":
                        InfoCommand.Alliance(cmd.event);
                        break;
                    case "baro":
                        InfoCommand.VoidTraders(cmd.event);
                        break;
                    case "clan":
                        InfoCommand.ListClan(cmd.event);
                        break;
                    case "discordwf":
                        InfoCommand.DiscordWarframe(cmd.event);
                        break;
                    case "goals":
                        InfoCommand.Goals(cmd.event);
                        break;
                    case "idée":
                    case "idee":
                        InfoCommand.Idee(cmd.event);
                        break;
                    case "info":
                        InfoCommand.Info(cmd.event);
                        break;
                    case "invasions":
                        InfoCommand.Invasions(cmd.event);
                        break;
                    case "invite":
                        InfoCommand.InvitationServeur(cmd.event);
                        break;
                    case "lead":
                        InfoCommand.ListLeader(cmd.event);
                        break;
                    case "progres":
                        InfoCommand.Progression(cmd.event);
                        break;
                    case "pvp":
                        InfoCommand.PvpChallenge(cmd.event);
                        break;
                    case "raid":
                        InfoCommand.Raid(cmd.event);
                        break;
                    case "regle":
                        RegleCommand.Reglement(cmd.event);
                        break;
                    case "site":
                        InfoCommand.Site(cmd.event);
                        break;
                    case "sortie":
                        InfoCommand.Sorties(cmd.event);
                        break;
                    case "steam":
                        InfoCommand.Steam(cmd.event);
                        break;
                    case "syndicat":
                        InfoCommand.Syndicats(cmd.event);
                        break;
                    case "ts3":
                        InfoCommand.Ts(cmd.event);
                        break;
                    case "up":
                        InfoCommand.Upcoming(cmd.event);
                        break;
                    case "updates":
                        InfoCommand.UpdateHotfix(cmd.event);
                        break;
                    case "void":
                        InfoCommand.Void(cmd.event);
                        break;

                    // commande riven
                    case "riven":
                        RivenCommand.Riven(cmd.event);
                        break;

                    // commande sondage
                    case "sondage":
                        SondageCommand.Sondage(cmd.event);
                        break;

                    // commande raid
                    case "affiche":
                        RaidCommand.AffichePresent(cmd.event);
                        break;
                    case "cancel":
                        RaidCommand.Cancel(cmd.event);
                        break;
                    case "present":
                        RaidCommand.Present(cmd.event);
                        break;

                    // commandes admin / modo
                    case "addclan":
                        GestionCommand.AddClan(cmd.event);
                        break;
                    case "addurl":
                        GestionCommand.AddLogoUrl(cmd.event);
                        break;
                    case "aubucher":
                        aubucher.start();
                        break;
                    case "ban":
                        AdminCommand.Ban(cmd.event);
                        break;
                    case "createsalonclan":
                        SalonCommand.CreateSalonClan(cmd.event);
                        break;
                    case "deafen":
                        AdminCommand.Deafen(cmd.event);
                        break;
                    case "deletesalonclan":
                        SalonCommand.DeleteSalonClan(cmd.event);
                        break;
                    case "kick":
                        AdminCommand.Kick(cmd.event);
                        break;
                    case "mute":
                        AdminCommand.Mute(cmd.event);
                        break;
                    case "ping":
                        AdminCommand.Ping(cmd.event);
                        break;
                    case "setgame":
                        AdminCommand.SetGame(cmd.event);
                        break;
                    case "removeclan":
                        GestionCommand.RemoveClan(cmd.event);
                        break;
                    case "tenno":
                        AdminCommand.AddUserToTenno(cmd.event);
                        break;
                    case "undeafen":
                        AdminCommand.UnDeafen(cmd.event);
                        break;
                    case "unban":
                        AdminCommand.UnBan(cmd.event);
                        break;
                    case "unmute":
                        AdminCommand.UnMute(cmd.event);
                        break;

                    // commande erreur
                    case "erreur":
                        ErreurCommand.Erreur(cmd.event);
                        break;

                    // test
                    case "test":
                        Command.Test(cmd.event);
                        break;

                    // commande  bot
                    case "about":
                        Command.AboutBot(cmd.event);
                        break;
                    case "botnews":
                        Command.AfficheUpdateBot(cmd.event);
                        break;

                    // commande arrêt
                    case "shutdown":
                        ShutdownCommand.Shutdown(cmd.event);
                        break;
                    //---------------------------------------------------------------//
                    default:
                        cmd.event.getTextChannel().sendMessage("Fuck").queue();
                        break;
                }
            }
        } else {
            cmd.event.getTextChannel().sendMessage(CompareCommande(cmd.invoke, commands.keySet().toArray())).queue();
            MessageNoThing(cmd.event);
        }
    }
}

