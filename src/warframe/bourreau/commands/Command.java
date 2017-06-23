package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.api.warframeAPI.*;
import static warframe.bourreau.help.EmbedMessageHelp.MessageListeCommande;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Find.FindModo;
import static warframe.bourreau.util.MessageOnEvent.MessageNoThing;
import static warframe.bourreau.util.Recup.recupString;

public class Command {

    public static boolean called() { return true; }

    public static void AfficheUpdateBot(MessageReceivedEvent event) {
        if (event.getMember().equals(event.getGuild().getOwner())) {
            EmbedBuilder news = new EmbedBuilder();
            EmbedBuilder news2 = new EmbedBuilder();
            String botnews;
            String botnews2;

            botnews = "- ajout de la commande !sortie" +
                    "\n     qui affiche la sortie en cours" +
                    "\n\n- ajout de la commande !baro" +
                    "\n     qui affiche le temps restant, pour l'arrivé de Baro Ki'Teer" +
                    "\n     et le relais où il sera" +
                    "\n\n- ajout de la commande !pvp" +
                    "\n     qui affiche les défis quotidien du conclave" +
                    "\n\n- ajout de la commande !pvp hebdo" +
                    "\n     qui affiche le temps restant pour les défis hebdomadaire" +
                    "\n\n- ajout de la commande !syndicat" +
                    "\n     qui affiche les missions syndicales du jour";
            botnews2 ="- ajout de la commande !void" +
                    "\n     qui affiche toutes les missions fissures" +
                    "\n\n- ajout de la commande !void <tiers>" +
                    "\n     parmi : axi/neo/meso/lith" +
                    "\n     qui affiche que les fissures du tiers saisi" +
                    "\n\n- ajoute de la commande !invasions" +
                    "\n     qui affiche toutes les invasiosn en cours" +
                    "\n\n- ajoute de la commande !invasions interest" +
                    "\n     qui affiche toutes les invasiosn en cours, ayant une récompense intéressante" +
                    "\n\n- ajoute de la commande !alerts" +
                    "\n     qui affiche toutes les alertes en cours" +
                   "\n\n- ajoute de la commande !alerts interest" +
                    "\n     qui affiche toutes les alertes en cours, ayant une récompense intéressante" +

                    "\n\n- mise à jour de la commande help";

            news.setTitle("Nouveauté du bot :", null);
            news.setDescription("ajout avec la dernière mise à jour");
            news.setThumbnail("http://i.imgur.com/gXZfo5H.png");
            news.addField("news :", botnews, true);
            news.setColor(new Color(17, 204, 17));
            news.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            news2.setTitle("Nouveauté du bot :", null);
            news2.setDescription("ajout avec la dernière mise à jour");
            news2.setThumbnail("http://i.imgur.com/gXZfo5H.png");
            news2.addField("news :", botnews2, true);
            news2.setColor(new Color(17, 204, 17));
            news2.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

            event.getJDA().getTextChannelById(botSpamID).sendMessage(news.build()).complete().pin().complete();
            event.getJDA().getTextChannelById(botSpamID).sendMessage(news2.build()).complete().pin().complete();
        }
        else
            MessageNoThing(event);
    }

    static void Information(MessageReceivedEvent event) {
        User destinataire = null;
        MessageBuilder messageMP = new MessageBuilder();

        if (event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getAuthor();
        else if (!event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getMessage().getMentionedUsers().get(0);

        if (destinataire != null) {
            messageMP.append("Salut à toi, ");
            messageMP.append(destinataire);
            messageMP.append(", Tenno\n\nTu as maintenant accès au reste du discord, notament aux commandes du bot ");
            messageMP.append(event.getJDA().getSelfUser());
            messageMP.append(",\nque tu peux voir avec la commande **!help**");

            messageMP.append("\n\nAu plaisir de te revoir sur le discord.");

            messageMP.append("\n\nCordialement, ");
            messageMP.append(event.getJDA().getSelfUser());

            destinataire.openPrivateChannel().complete();
            destinataire.getPrivateChannel().sendMessage(messageMP.build()).queue();
            destinataire.getPrivateChannel().close();
        }
    }

    public static void Presentation(MessageReceivedEvent event) {
        if (event.getMessage().getContent().contains("présentation") || event.getMessage().getContent().contains("presentation")
                || event.getMessage().getContent().contains("présente") || event.getMessage().getContent().contains("presente")) {

            if (event.getMessage().getContent().toLowerCase().equals("présente") || event.getMessage().getContent().toLowerCase().equals("présentation")
                    || event.getMessage().getContent().toLowerCase().equals("présentation") || event.getMessage().getContent().toLowerCase().equals("presentation")
                    || event.getMessage().getContent().length() < 30) {
                User destinataire = null;
                MessageBuilder messageMP = new MessageBuilder();

                if (event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getAuthor();
                else if (!event.getMessage().getMentionedUsers().isEmpty()) destinataire = event.getMessage().getMentionedUsers().get(0);

                if (destinataire != null) {
                    messageMP.append(destinataire);
                    messageMP.append(", votre présentation n'est pas correctement.");
                    messageMP.append("\nVeuillez refaire votre présentation dans le salon textuel ");
                    messageMP.append(event.getJDA().getTextChannelById(accueilID));
                    messageMP.append("\nA bientôt, peut-être.\n");
                    messageMP.append(event.getJDA().getSelfUser());

                    destinataire.openPrivateChannel().complete();
                    destinataire.getPrivateChannel().sendMessage(messageMP.build()).queue();
                    destinataire.getPrivateChannel().close();
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

    public static void Test(MessageReceivedEvent event) {

        //Events(event);

    }
}
