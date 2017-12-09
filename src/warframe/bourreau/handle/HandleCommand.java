package warframe.bourreau.handle;

import org.json.JSONArray;
import org.json.JSONObject;
import warframe.bourreau.commands.*;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadAuBucher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.handle.HandleSonCommand.handleSonCommand;
import static warframe.bourreau.util.Find.FindCommand;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThing;

public class HandleCommand {

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(cmd.event.getGuild().getId()).getJSONArray("commandes");

            if (commands.containsKey(cmd.invoke)) {
                boolean safe = SimpleCommand.called();

                if (safe && (FindCommand(cmd.event, cmd.invoke) || cmd.invoke.equals("config"))) {
                    switch (cmd.invoke) {
                        // liste des commandes
                        case "help":
                            HelpCommand.Help(cmd.event);
                            break;

                        // commandes sons
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
                        case "ts":
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
                            new ThreadAuBucher(cmd.event).start();
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

                        // commande config
                        case "config":
                            ConfigCommand.ConfigurationBotServer(cmd.event);
                            break;

                        // commande erreur
                        case "erreur":
                            ErreurCommand.Erreur(cmd.event);
                            break;

                        // test
                        case "test":
                            BasedCommand.Test(cmd.event);
                            break;

                        // commande  bot
                        case "about":
                            BasedCommand.AboutBot(cmd.event);
                            break;
                        case "botnews":
                            BasedCommand.AfficheUpdateBot(cmd.event);
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
                } else {
                    if (!commands.isEmpty() && !(configCommandson.length() == 0))
                        cmd.event.getTextChannel().sendMessage(CompareCommande(cmd.invoke, configCommandson.toList().toArray())).queue();

                    MessageNoThing(cmd.event);
                }
            } else {
                if (!commands.isEmpty() && !(configCommandson.length() == 0))
                    cmd.event.getTextChannel().sendMessage(CompareCommande(cmd.invoke, configCommandson.toList().toArray())).queue();

                MessageNoThing(cmd.event);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

