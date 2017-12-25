package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.commands.BasedCommand.information;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.*;

public class AdminCommand extends SimpleCommand {

    @Command(name="tenno", subCommand=false)
    public static void addUserToTenno(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getMessage().toString().contains("@")) {
                    String configRoles = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
                    JSONObject configRolesJson = new JSONObject(configRoles);
                    String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole");
                    User newTenno = event.getMessage().getMentionedUsers().get(0);

                    event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(newTenno.getId()), event.getGuild().getRoleById(roleID)).complete();
                    information(event);

                    event.getTextChannel().sendMessage("nouveau Tenno, " + newTenno.getName()).queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="aubucher", subCommand=false)
    public static void auBucher(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String configRoles = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
                String configVoiceChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
                JSONObject configRolesJson = new JSONObject(configRoles);
                JSONObject configvoiceChannelJson = new JSONObject(configVoiceChannel);
                String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("bucher").getString("idRole");
                String voiceChannelID = configvoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject("bucher").getString("idVoiceChannel");
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (findUserVC(event) != null) {
                        event.getGuild().getController().moveVoiceMember(event.getGuild().getMemberById(id), event.getJDA().getVoiceChannelById(voiceChannelID)).queue();
                        event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(id), event.getGuild().getRoleById(roleID)).queue();
                        event.getTextChannel().sendMessage("Hérétique ,au bucher !").queue();
                    }
                    else
                        event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été envoyer au bucher.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="ban", subCommand=false)
    public static void ban(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                int dayBan = 7;

                if (event.getMessage().toString().contains("@")) {
                    if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser()))
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                    else {
                        event.getGuild().getController().ban(recupID(event.getMessage().getMentionedUsers().toString()), dayBan).submit();
                        event.getTextChannel().sendMessage("client banni").queue();
                    }
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="deafen", subCommand=false)
    public static void deafen(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (!event.getGuild().getMemberById(id).isOwner()) {
                        if (findUserVC(event) != null) {
                            event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), true).submit();
                            event.getTextChannel().sendMessage("client assourdi").queue();
                        }
                        else
                            event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été assourdi.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                }
                else
                    event.getTextChannel().sendMessage("Pas de personne mentionnée.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="kick", subCommand=false)
    public static void kick(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

                if (event.getMessage().toString().contains("@")) {
                    if (!event.getGuild().getMemberById(id).isOwner()) {
                        if (findUserVC(event) != null) {
                            event.getGuild().getController().kick(recupID(event.getMessage().getMentionedUsers().toString())).submit();
                            event.getTextChannel().sendMessage("client kické").queue();
                        } else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                            event.getTextChannel().sendMessage("Impossible !").queue();
                        } else
                            event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été kick.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="mute", subCommand=false)
    public static void mute(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

                if (event.getMessage().toString().contains("@")) {
                    if (!event.getGuild().getMemberById(id).isOwner()) {
                        if (findUserVC(event) != null) {
                            event.getGuild().getController().setMute(event.getGuild().getMemberById(id), true).submit();
                            event.getTextChannel().sendMessage("client muté").queue();
                        } else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                            event.getTextChannel().sendMessage("Impossible !").queue();
                        } else
                            event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été muté.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="ping", subCommand=false)
    public static void ping(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                EmbedBuilder ping = new EmbedBuilder();

                ping.setTitle("Pong", null);
                ping.setDescription("responive time");
                ping.addField(event.getJDA().getPing() + " ms", "", true);
                ping.setColor(new Color(255, 255, 255));
                ping.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getTextChannel().sendMessage(ping.build()).queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="setgame", subCommand=false)
    public static void setGame(MessageReceivedEvent event) {
        try {
            if (findAdminSupreme(event.getAuthor().getId())) {
                JSONObject botjson = new JSONObject();
                String commande = event.getMessage().getContentDisplay();

                botjson.put("botToken", "MjkwODgzMDkwMTQwNzU4MDE3.C_NBKQ.8i9vV1lkESKaYs0IeXU7zZcpRrU");
                botjson.put("botVersion", "1.0");
                botjson.put("clientID", "290883090140758017");

                if (commande.contains(" ") && !recupString(commande).equals("null")) {
                    FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "res" + File.separator + "config" + File.separator + "bot.json");

                    botjson.put("game", recupString(commande));
                    file.write(botjson.toString(3));
                    file.flush();
                    file.close();

                    event.getJDA().getPresence().setGame(Game.of(Game.GameType.STREAMING, recupString(commande), "https://trello.com/b/JEEkreCv/bot-discord-alliance"));
                    event.getTextChannel().sendMessage("jeu changé.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de jeu saisi.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="unban", subCommand=false)
    public static void unBan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContentDisplay().toLowerCase();
                String user = recupPseudo(event.getMessage().getContentDisplay().toLowerCase());

                if (commande.contains(" ")) {
                    event.getGuild().getController().unban(user).submit();
                    event.getTextChannel().sendMessage("client débanni").queue();
                }
                else
                    event.getTextChannel().sendMessage("Ce client n'est pas banni ou n'existe pas.").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="undeafen", subCommand=false)
    public static void unDeafen(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (!event.getGuild().getMemberById(id).isOwner()) {

                        event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), false).submit();
                        event.getTextChannel().sendMessage("client désourdi").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="unmute", subCommand=false)
    public static void unMute(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (!event.getGuild().getMemberById(id).isOwner()) {
                        event.getGuild().getController().setMute(event.getGuild().getMemberById(id), false).submit();
                        event.getTextChannel().sendMessage("client démuté").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("Impossible, c'est le proprio des lieux.").queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
