package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.Channel;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static net.dv8tion.jda.core.Permission.*;
import static warframe.bourreau.InitID.*;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.MessageOnEvent.MessageNoThing;

public class Command {

    public static boolean called() { return true; }

    public static void AfficheUpdateBot(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                EmbedBuilder news = new EmbedBuilder();

                news.addField("- ajout commande reglement", " affiche le reglement du serveur", false);
                news.addField("- suppression salon reclamation", " ce fait maintenant en message privé", false);

                news.addField("- mise à jour de la commande help", "", false);

                news.setTitle("Nouveauté du bot :", null);
                news.setDescription("ajout avec la dernière mise à jour");
                news.setThumbnail("http://i.imgur.com/gXZfo5H.png");
                news.setColor(new Color(17, 204, 17));
                news.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getJDA().getTextChannelById(botSpamID).sendMessage(news.build()).complete().pin().complete();
            } else
                MessageNoThing(event);
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    static void Information(MessageReceivedEvent event) {
        try {
            User destinataire = null;
            MessageBuilder messageMP = new MessageBuilder();

            if (event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getAuthor();
            else if (!event.getMessage().getMentionedUsers().isEmpty())
                destinataire = event.getMessage().getMentionedUsers().get(0);

            if (destinataire != null) {
                messageMP.append("Salut à toi, ");
                messageMP.append(destinataire);
                messageMP.append(", Tenno\n\nTu as maintenant accès au reste du discord, notament aux commandes du bot ");
                messageMP.append(event.getJDA().getSelfUser());
                messageMP.append(",\nque tu peux voir avec la commande **!help**");
                messageMP.append("\n\nAu plaisir de te revoir sur le discord.");
                messageMP.append("\n\nCordialement, ");
                messageMP.append(event.getJDA().getSelfUser());
                messageMP.append("\n\nPS : tu peux participer à l'évolution du bot, avec le commande **!idée**, en écrivant ton idée sur le google docs\n");
                messageMP.append(event.getGuild().getOwner().getUser());

                destinataire.openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Presentation(MessageReceivedEvent event) {
        try {
            if (event.getMessage().getContent().toLowerCase().contains("présentation") || event.getMessage().getContent().toLowerCase().contains("presentation")
                    || event.getMessage().getContent().toLowerCase().contains("présente") || event.getMessage().getContent().toLowerCase().contains("presente")) {

                if (event.getMessage().getContent().toLowerCase().equals("présente") || event.getMessage().getContent().toLowerCase().equals("présentation")
                        || event.getMessage().getContent().toLowerCase().equals("présentation") || event.getMessage().getContent().toLowerCase().equals("presentation")
                        || event.getMessage().getContent().length() < 30) {
                    User destinataire = null;
                    MessageBuilder messageMP = new MessageBuilder();

                    if (event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getAuthor();
                    else if (!event.getMessage().getMentionedUsers().isEmpty())
                        destinataire = event.getMessage().getMentionedUsers().get(0);

                    if (destinataire != null) {
                        messageMP.append(destinataire);
                        messageMP.append(", votre présentation n'est pas correctement.");
                        messageMP.append("\nVeuillez refaire votre présentation dans le salon textuel ");
                        messageMP.append(event.getJDA().getTextChannelById(accueilID));
                        messageMP.append("\nA bientôt, peut-être.\n");
                        messageMP.append(event.getJDA().getSelfUser());
                        destinataire.openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
                    }
                } else {
                    Role tenno = event.getGuild().getRoleById(tennoID);

                    event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(bourreauID)).queue();
                    event.getGuild().getController().addRolesToMember(event.getMember(), tenno).queue();
                    //event.getTextChannel().sendMessage("Merci de ta présentation, et Bienvenue sur le discord de l'Alliance **French Connection** !").queue();
                    Information(event);
                }
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Test(MessageReceivedEvent event) {
        if (FindAdmin(event, event.getMember())) {
            String clan = "xX Dragon Jedi Order Xx";
            Channel newTC;
            Channel newVC;
            Role newRole;
            Role every = event.getGuild().getPublicRole();

            if (event.getGuild().getRolesByName(clan, true).size() == 0) {
                System.out.println("entrer");
                newRole = event.getGuild().getController().createRole().setName(clan).setPermissions(NICKNAME_CHANGE, VIEW_CHANNEL, MESSAGE_WRITE, MESSAGE_TTS,
                        MESSAGE_ATTACH_FILES, MESSAGE_EMBED_LINKS, MESSAGE_HISTORY, MESSAGE_MENTION_EVERYONE, MESSAGE_EXT_EMOJI, MESSAGE_ADD_REACTION,
                        VOICE_CONNECT, VOICE_SPEAK, VOICE_USE_VAD).complete();
            } else
                newRole = event.getGuild().getRolesByName(clan, true).get(0);

            if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() == 0) {
                newTC = event.getGuild().getController().createTextChannel(clan.toLowerCase().replace(" ", "_")).complete();
                newTC.getManager().setParent(event.getGuild().getCategoryById(clanID)).queue();
            } else
                newTC = event.getGuild().getTextChannelsByName(clan.replace(" ", "_"), true).get(0);

            if (event.getGuild().getVoiceChannelsByName(clan, true).size() == 0) {
                newVC = event.getGuild().getController().createVoiceChannel(clan).complete();
                newVC.getManager().setParent(event.getGuild().getCategoryById(clanID)).queue();
            } else
                newVC = event.getGuild().getVoiceChannelsByName(clan, true).get(0);

            if (event.getGuild().getRolesByName(clan, true).size() >= 1) {
                if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() >= 1) {
                    newTC.createPermissionOverride(newRole).setAllow(MESSAGE_READ).complete();
                    newTC.getPermissionOverride(every).getManager().deny(MESSAGE_READ).complete();
                }

                if (event.getGuild().getVoiceChannelsByName(clan, true).size() >= 1) {
                    newVC.createPermissionOverride(newRole).setAllow(VOICE_CONNECT, VOICE_SPEAK).complete();
                    newVC.getPermissionOverride(every).getManager().deny(VOICE_CONNECT, VOICE_SPEAK).complete();
                }
            }
        }
    }
}
