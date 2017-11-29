package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import warframe.bourreau.util.Command;

import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.Main.botVersion;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.messsage.Message.MessageAbout;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThing;
import static warframe.bourreau.util.Find.FindUserVC;

public class BasedCommand extends SimpleCommand {

    @Command(name="about")
    public static void AboutBot(MessageReceivedEvent event) { event.getTextChannel().sendMessage(MessageAbout()).queue(); }

    @Command(name="about")
    public static void AboutBotPrivate(PrivateMessageReceivedEvent event) { event.getAuthor().openPrivateChannel().complete().sendMessage(MessageAbout()).queue(); }

    @Command(name="botnews")
    public static void AfficheUpdateBot(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                EmbedBuilder news = new EmbedBuilder();

                news.addField("=> modification des commandes de son", "   - refonte du système, fixant tous les bugs\n- maintenant, si un son est en cours de lecture et " +
                        "qu'une nouvelle commande son est exécutée, la première commande est interrompu, laissant place à la nouvlle.", false);
                news.addField("=> ajout commande about", "   affiche quelques informations du bot.", false);

                news.addField("=> mise à jour de la commande help", "", false);

                news.setTitle("Patch Note " + botVersion + " :", null);
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
                messageMP.append("\n\nPS : tu peux participer à l'évolution du bot, avec le commande **!idee**, en écrivant ton idée sur le google docs\n");
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
                }
                else {
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

    @Command(name="test")
    public static void Test(MessageReceivedEvent event) {
        try {
            String file = "bucher.mp4";
            String adresse = System.getProperty("user.dir") + File.separator + "music" + File.separator + file;
            VoiceChannel channel = FindUserVC(event);

            if (channel == null)
                event.getTextChannel().sendMessage("Client non conneté à un salon vocal.").queue();
            else {
                if (!audioManager.isConnected())
                    audioManager.openAudioConnection(channel);

                manager.loadTrack(event.getTextChannel(), adresse);
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
