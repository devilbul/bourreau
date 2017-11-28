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
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.commands.BasedCommand.Information;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Find.FindModo;
import static warframe.bourreau.util.Find.FindUserVC;
import static warframe.bourreau.util.Recup.*;

public class AdminCommand extends SimpleCommand {

    @Command(name="tenno")
    public static void AddUserToTenno(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                if (event.getMessage().toString().contains("@")) {
                    User newTenno = event.getMessage().getMentionedUsers().get(0);

                    event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(newTenno.getId()), event.getGuild().getRoleById(tennoID)).complete();
                    Information(event);

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

    @Command(name="aubucher")
    public static void AuBucher(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (FindUserVC(event) != null) {
                        event.getGuild().getController().moveVoiceMember(event.getGuild().getMemberById(id), event.getJDA().getVoiceChannelById(bucherID)).queue();
                        event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(id), event.getGuild().getRoleById(heretiqueID)).queue();
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

    @Command(name="ban")
    public static void Ban(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                int dayBan = 7;

                if (event.getMessage().toString().contains("@")) {
                    if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser()))
                        event.getTextChannel().sendMessage("Impossible !").queue();
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

    @Command(name="deafen")
    public static void Deafen(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    if (FindUserVC(event) != null) {
                        event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), true).submit();
                        event.getTextChannel().sendMessage("client assourdi").queue();
                    }
                    else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                        event.getTextChannel().sendMessage("Impossible !").queue();
                    }
                    else
                        event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été assourdi.").queue();
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

    @Command(name="kick")
    public static void Kick(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

                if (event.getMessage().toString().contains("@")) {
                    if (FindUserVC(event) != null) {
                        event.getGuild().getController().kick(recupID(event.getMessage().getMentionedUsers().toString())).submit();
                        event.getTextChannel().sendMessage("client kické").queue();
                    }
                    else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                        event.getTextChannel().sendMessage("Impossible !").queue();
                    }
                    else
                        event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été kick.").queue();
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

    @Command(name="mute")
    public static void Mute(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

                if (event.getMessage().toString().contains("@")) {
                    if (FindUserVC(event) != null) {
                        event.getGuild().getController().setMute(event.getGuild().getMemberById(id), true).submit();
                        event.getTextChannel().sendMessage("client muté").queue();
                    }
                    else if (event.getMessage().getMentionedUsers().get(0).equals(event.getGuild().getOwner().getUser())) {
                        event.getTextChannel().sendMessage("Impossible !").queue();
                    }
                    else
                        event.getTextChannel().sendMessage(event.getJDA().getUserById(id).getName() + " n'est pas dans un salon vocal.\nil n'a pas été muté.").queue();
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

    @Command(name="ping")
    public static void Ping(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
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

    @Command(name="setgame")
    public static void SetGame(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                JSONObject botjson = new JSONObject();
                String commande = event.getMessage().getContent();

                botjson.put("botToken", "MjkwODgzMDkwMTQwNzU4MDE3.C_NBKQ.8i9vV1lkESKaYs0IeXU7zZcpRrU");
                botjson.put("botVersion", "1.0");
                botjson.put("clientID", "290883090140758017");

                if (commande.contains(" ") && !recupString(commande).equals("null")) {
                    FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "config" + File.separator + "bot.json");

                    botjson.put("game", recupString(commande));
                    file.write(botjson.toString());
                    file.flush();
                    file.close();

                    event.getJDA().getPresence().setGame(Game.of(recupString(commande)));
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

    @Command(name="unban")
    public static void UnBan(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContent().toLowerCase();
                String user = recupPseudo(event.getMessage().getContent().toLowerCase());

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

    @Command(name="undeafen")
    public static void UnDeafen(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), false).submit();
                    event.getTextChannel().sendMessage("client désourdi").queue();
                } else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="unmute")
    public static void UnMute(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
                String id = recupID(event.getMessage().getMentionedUsers().toString());

                if (event.getMessage().toString().contains("@")) {
                    event.getGuild().getController().setMute(event.getGuild().getMemberById(id), false).submit();
                    event.getTextChannel().sendMessage("client démuté").queue();
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
