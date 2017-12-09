package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.WaitingSound;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

import static warframe.bourreau.Bourreau.getJDA;
import static warframe.bourreau.Init.*;
import static warframe.bourreau.Bourreau.botVersion;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.messsage.Message.MessageAbout;
import static warframe.bourreau.messsage.MessageOnEvent.MessagePremierConnection;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.messsage.MessageOnEvent.MessageNoThing;
import static warframe.bourreau.util.Find.FindCommand;

public class BasedCommand extends SimpleCommand {

    @Command(name="about")
    public static void AboutBot(MessageReceivedEvent event) { event.getTextChannel().sendMessage(MessageAbout()).queue(); }

    @Command(name="about")
    public static void AboutBotPrivate(PrivateMessageReceivedEvent event) { event.getAuthor().openPrivateChannel().complete().sendMessage(MessageAbout()).queue(); }

    @Command(name="botnews")
    public static void AfficheUpdateBot(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
                JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("botSpam").getString("idTextChannel");
                EmbedBuilder news = new EmbedBuilder();

                news.addField("=> amélioration mise à jour influence riven", "._.", false);

                news.addField("=> mise à jour de la commande help", "._.", false);

                news.setTitle("Patch Note " + botVersion + " :", null);
                news.setDescription("ajout avec la dernière mise à jour");
                news.setThumbnail("http://i.imgur.com/gXZfo5H.png");
                news.setColor(new Color(17, 204, 17));
                news.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                event.getJDA().getTextChannelById(textChannelID).sendMessage(news.build()).complete().pin().complete();
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
                        String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
                        JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                        String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");

                        messageMP.append(destinataire);
                        messageMP.append(", votre présentation n'est pas correctement.");
                        messageMP.append("\nVeuillez refaire votre présentation dans le salon textuel ");
                        messageMP.append(event.getJDA().getTextChannelById(textChannelID));
                        messageMP.append("\nA bientôt, peut-être.\n");
                        messageMP.append(event.getJDA().getSelfUser());
                        destinataire.openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
                    }
                }
                else {
                    String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
                    String configRoles = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
                    JSONObject configEmotesJson = new JSONObject(configEmotes);
                    JSONObject configRolesJson = new JSONObject(configRoles);
                    String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole");
                    String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
                    String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");
                    Role tenno = event.getGuild().getRoleById(roleID);

                    event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getGuildById(serverID).getEmoteById(emoteID)).queue();
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
            /*for (Guild guild : getJDA().getGuilds()) {
                System.out.println("-----------------------------------------------------");
                System.out.println(guild.getName());
                System.out.println(guild.getId());
            }
            System.out.println("-----------------------------------------------------");

            for (WaitingSound aQueueSon : queueSon) {
                System.out.println(aQueueSon.getEvent().getGuild().getId());
                System.out.println(aQueueSon.getCommandeSon());
                System.out.println(aQueueSon.getIdDemandeur());
            }

            System.out.println("-----------------------------------------------------");*/
            //event.getTextChannel().sendMessage(MessagePremierConnection()).queue();

        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
