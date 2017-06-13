package warframe.bourreau.commands;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.riven.TxtToJson.Traitement;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Find.FindModo;
import static warframe.bourreau.util.Find.FindUserVC;
import static warframe.bourreau.util.Recup.*;

public class AdminCommand extends Command {

    public static void AddUserToTenno(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
            if (event.getMessage().toString().contains("@")) {
                User newTenno = event.getMessage().getMentionedUsers().get(0);

                event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(newTenno.getId()), event.getGuild().getRoleById(tennoID)).complete();
                Information(event);

                event.getTextChannel().sendMessage("nouvaeu Tenno, " + newTenno.getName()).queue();
            }
            else
                event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    public static void AuBucher(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
            String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

            if (event.getMessage().toString().contains("@")) {
                if (FindUserVC(event) != null) {
                    event.getGuild().getController().moveVoiceMember(event.getGuild().getMemberById(id), event.getJDA().getVoiceChannelById(bucherID)).queue();
                    event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(id), event.getGuild().getRoleById(heretiqueID)).queue();
                    event.getTextChannel().sendMessage("Hérétique ,au bucher !");
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

    public static void Ban(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember())) {
            int dayBan = 7;

            if(event.getMessage().toString().contains("@")) {
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

    public static void Deafen(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
            String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

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

    public static void GetBans(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
            List<User> bannis = event.getGuild().getController().getBans().complete();

            if (bannis.isEmpty())
                event.getTextChannel().sendMessage("Aucun client banni.").queue();

            for (int i = 0; i < bannis.size(); i++) {
                event.getTextChannel().sendMessage("Banni " + (i + 1) + " :" +
                        "\n   Pseudo : " + bannis.get(i).getName() +
                        "\n   Id : " + bannis.get(i).getId()).queue();
            }
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    public static void Kick(MessageReceivedEvent event) {
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

    public static void Mute(MessageReceivedEvent event) {
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
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void TraitementRiven(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember())) {
            if (Traitement())
                event.getTextChannel().sendMessage("Traitement effectué.").queue();
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    public static void UnBan(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember())) {
            String commande = event.getMessage().getContent().toLowerCase();
            String user = recupPseudo(event.getMessage().getContent().toLowerCase());
            List<User> bannis = event.getGuild().getController().getBans().complete();
            User banni = recupUser(bannis, user);

            if (banni != null) {
                if (commande.contains(" ")) {
                    event.getGuild().getController().unban(banni).submit();
                    event.getTextChannel().sendMessage("client débanni").queue();
                }
            }
            else
                event.getTextChannel().sendMessage("Ce client n'est pas banni ou n'existe pas.").queue();
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    public static void UnDeafen(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember()) || FindModo(event, event.getMember())) {
            String id = recupID(event.getMessage().getMentionedUsers().toString()) ;

            if (event.getMessage().toString().contains("@")) {
                event.getGuild().getController().setDeafen(event.getGuild().getMemberById(id), false).submit();
                event.getTextChannel().sendMessage("client désourdi").queue();
            }
            else
                event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
        }
        else
            event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
    }

    public static void UnMute(MessageReceivedEvent event) {
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
}
