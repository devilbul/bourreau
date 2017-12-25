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
import static warframe.bourreau.util.Find.findIndexStringArray;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.messsage.MessageOnEvent.messageNoThing;

public class HandleCommand {

    public static void handleCommand(CommandParser.CommandContainer cmd) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONArray configCommandson = new JSONObject(configCommand).getJSONObject("commandes").getJSONObject(cmd.event.getGuild().getId()).getJSONArray("commandes");

            if (configCommandson.length() > 0 || cmd.invoke.equals("config")) {
                boolean safe = SimpleCommand.called();

                if (findIndexStringArray(configCommandson, cmd.invoke) != -1 || cmd.invoke.equals("config")) {
                        if (safe) {
                        switch (cmd.invoke) {
                            // liste des commandes
                            case "help":
                                HelpCommand.help(cmd.event);
                                break;

                            // commandes sons
                            case "acdc":
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
                                TrollCommand.pute(cmd.event);
                                break;
                            case "rip":
                                TrollCommand.rip(cmd.event);
                                break;
                            case "segpa":
                                TrollCommand.segpta(cmd.event);
                                break;
                            case "tg":
                                TrollCommand.tg(cmd.event);
                                break;

                            // commande info
                            case "alerts":
                                InfoCommand.alerts(cmd.event);
                                break;
                            case "alliance":
                                InfoCommand.alliance(cmd.event);
                                break;
                            case "baro":
                                InfoCommand.voidTraders(cmd.event);
                                break;
                            case "clan":
                                InfoCommand.listClan(cmd.event);
                                break;
                            case "discordwf":
                                InfoCommand.discordWarframe(cmd.event);
                                break;
                            case "goals":
                                InfoCommand.goals(cmd.event);
                                break;
                            case "idee":
                                InfoCommand.idee(cmd.event);
                                break;
                            case "info":
                                InfoCommand.info(cmd.event);
                                break;
                            case "invasions":
                                InfoCommand.invasions(cmd.event);
                                break;
                            case "invite":
                                InfoCommand.invitationServeur(cmd.event);
                                break;
                            case "lead":
                                InfoCommand.listLeader(cmd.event);
                                break;
                            case "progres":
                                InfoCommand.progression(cmd.event);
                                break;
                            case "pvp":
                                InfoCommand.pvpChallenges(cmd.event);
                                break;
                            case "raid":
                                InfoCommand.raid(cmd.event);
                                break;
                            case "regle":
                                RegleCommand.reglement(cmd.event);
                                break;
                            case "site":
                                InfoCommand.site(cmd.event);
                                break;
                            case "sortie":
                                InfoCommand.sorties(cmd.event);
                                break;
                            case "steam":
                                InfoCommand.steam(cmd.event);
                                break;
                            case "syndicat":
                                InfoCommand.syndicats(cmd.event);
                                break;
                            case "ts":
                                InfoCommand.ts(cmd.event);
                                break;
                            case "up":
                                InfoCommand.upcoming(cmd.event);
                                break;
                            case "updates":
                                InfoCommand.updateHotfix(cmd.event);
                                break;
                            case "void":
                                InfoCommand.voiD(cmd.event);
                                break;

                            // commande riven
                            case "riven":
                                RivenCommand.riven(cmd.event);
                                break;

                            // commande sondage
                            case "sondage":
                                SondageCommand.sondage(cmd.event);
                                break;

                            // commande raid
                            case "affiche":
                                RaidCommand.affichePresent(cmd.event);
                                break;
                            case "cancel":
                                RaidCommand.cancel(cmd.event);
                                break;
                            case "present":
                                RaidCommand.present(cmd.event);
                                break;

                            // commandes admin / modo
                            case "addclan":
                                GestionCommand.addClan(cmd.event);
                                break;
                            case "addurl":
                                GestionCommand.addLogoUrl(cmd.event);
                                break;
                            case "aubucher":
                                new ThreadAuBucher(cmd.event).start();
                                break;
                            case "ban":
                                AdminCommand.ban(cmd.event);
                                break;
                            case "createsalonclan":
                                SalonCommand.createSalonClan(cmd.event);
                                break;
                            case "deafen":
                                AdminCommand.deafen(cmd.event);
                                break;
                            case "deletesalonclan":
                                SalonCommand.deleteSalonClan(cmd.event);
                                break;
                            case "kick":
                                AdminCommand.kick(cmd.event);
                                break;
                            case "mute":
                                AdminCommand.mute(cmd.event);
                                break;
                            case "ping":
                                AdminCommand.ping(cmd.event);
                                break;
                            case "setgame":
                                AdminCommand.setGame(cmd.event);
                                break;
                            case "removeclan":
                                GestionCommand.removeClan(cmd.event);
                                break;
                            case "tenno":
                                AdminCommand.addUserToTenno(cmd.event);
                                break;
                            case "undeafen":
                                AdminCommand.unDeafen(cmd.event);
                                break;
                            case "unban":
                                AdminCommand.unBan(cmd.event);
                                break;
                            case "unmute":
                                AdminCommand.unMute(cmd.event);
                                break;

                            // commande config
                            case "config":
                                ConfigCommand.configurationBotServer(cmd.event);
                                break;

                            // commande erreur
                            case "erreur":
                                ErreurCommand.erreur(cmd.event);
                                break;

                            // test
                            case "test":
                                BasedCommand.test(cmd.event);
                                break;

                            // commande  bot
                            case "about":
                                BasedCommand.aboutBot(cmd.event);
                                break;
                            case "botnews":
                                BasedCommand.afficheUpdateBot(cmd.event);
                                break;

                            // commande arrêt
                            case "shutdown":
                                ShutdownCommand.shutdown(cmd.event);
                                break;
                            //---------------------------------------------------------------//
                            default:
                                cmd.event.getTextChannel().sendMessage("Fuck").queue();
                                break;
                        }
                    }
                }
                else {
                    if (!commands.isEmpty() && !(configCommandson.length() == 0))
                        cmd.event.getTextChannel().sendMessage(compareCommande(cmd.invoke, configCommandson.toList().toArray())).queue();

                    messageNoThing(cmd.event);
                }
            }
            else
                cmd.event.getTextChannel().sendMessage("Le bot n'a pas encore été configuré pour ce serveur !").queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

