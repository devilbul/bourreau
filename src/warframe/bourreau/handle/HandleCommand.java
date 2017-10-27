package warframe.bourreau.handle;

import warframe.bourreau.commands.*;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadSon;

import static warframe.bourreau.Main.commands;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Levenshtein.distance;
import static warframe.bourreau.util.MessageOnEvent.MessageNoThing;

public class HandleCommand {

public static void handleCommand(CommandParser.CommandContainer cmd) {
        if (commands.containsKey(cmd.invoke)) {
            boolean safe = Command.called();

            if (safe) {
                Thread ah = new ThreadSon.ThreadAh(cmd);
                Thread aubucher = new ThreadSon.ThreadAuBucher(cmd);
                Thread bucher = new ThreadSon.ThreadBucher(cmd);
                Thread gg = new ThreadSon.ThreadGg(cmd);
                Thread gogole = new ThreadSon.ThreadGogole(cmd);
                Thread nah = new ThreadSon.ThreadNah(cmd);
                Thread pigeon = new ThreadSon.ThreadPigeon(cmd);
                Thread souffrir = new ThreadSon.ThreadSouffrir(cmd);
                Thread trumpalea = new ThreadSon.ThreadTrumpAlea(cmd);
                Thread trump = new ThreadSon.ThreadTrump(cmd);
                Thread trump2 = new ThreadSon.ThreadTrump2(cmd);
                Thread trump3 = new ThreadSon.ThreadTrump3(cmd);

                switch (cmd.invoke) {
                    // liste des commandes
                    case "help":
                        HelpCommand.Help(cmd.event);
                        break;

                    // commande son
                    case "ah":
                        ah.start();
                        break;
                    case "bucher":
                        bucher.start();
                        break;
                    case "gg":
                        gg.start();
                        break;
                    case "gogole":
                        gogole.start();
                        break;
                    case "nah":
                        nah.start();
                        break;
                    case "pigeon":
                        pigeon.start();
                        break;
                    case "souffrir":
                        souffrir.start();
                        break;
                    case "stop":
                        SonCommand.Stop(cmd.event);
                        break;
                    case "trump":
                        trumpalea.start();
                        break;
                    case "trumpcomp":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) trump.start();
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp2":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) trump2.start();
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp3":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) trump3.start();
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
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
                    case "deafen":
                        AdminCommand.Deafen(cmd.event);
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

                    // commande affiche update bot
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

