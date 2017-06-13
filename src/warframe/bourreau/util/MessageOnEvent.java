package warframe.bourreau.util;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static warframe.bourreau.InitID.*;

public class MessageOnEvent {

    public static void MessageDeDeconnection(MessageReceivedEvent event) {
        MessageBuilder shutdown = new MessageBuilder();

        shutdown.append("Je vais aller faire un som, à plus en LAN.\n");
        shutdown.append(event.getJDA().getEmoteById(bourreauID));

        event.getJDA().getTextChannelById(accueilID).sendMessage(shutdown.build());
    }

    public static void MessageDeBienvenue(GuildMemberJoinEvent event) {
        MessageBuilder message = new MessageBuilder();
        MessageBuilder messageMP = new MessageBuilder();

        //message serveur
        message.append("Bienvenue à ");
        message.append(event.getMember());

        message.append("\n\nJe te souhaite la bienvenue sur le discord de l'Alliance **").append(event.getGuild().getName()).append("**");

        message.append("\n\nPrésente toi rapidement, en utilisant les mots **__présentation__** ou **__présente__**, pour **avoir accès** au reste du discord.");

        //message privé
        messageMP.append("Salut à toi, ");
        messageMP.append(event.getMember());

        messageMP.append("\n\nJe te souhaite la bienvenue sur le discord de l'Alliance **").append(event.getGuild().getName()).append("**");

        messageMP.append("\n\nPrésente toi rapidement dans le salon ");
        messageMP.append(event.getJDA().getTextChannelById(accueilID));
        messageMP.append(", en utilisant les mots **__présentation__** ou **__présente__**, pour **avoir accès** au reste du discord.");

        messageMP.append("\n\nSi tu joues à Warframe, et que souhaites rejoindre notre alliance, fais ta demande dans le salon ");
        messageMP.append(event.getJDA().getTextChannelById(candidatureID));
        messageMP.append(", un admin ou un chef de clan va te répondre.");

        messageMP.append("\n\nIl faut respecter certaines règles qui se situent dans le salon ");
        messageMP.append(event.getJDA().getTextChannelById(reglementID));
        messageMP.append(", dont je t'invite à lire fortement.");

        messageMP.append("\n\nPour toute question, rapport de problème, réclamation, un salon textuel est là pour ca, ");
        messageMP.append(event.getJDA().getTextChannelById(reclamationID));
        messageMP.append(".\nPas de spam dans ce salon, merci de faire attention.");

        messageMP.append("\n\nAu plaisir de te revoir sur le discord.");

        messageMP.append("\n\nCordialement, ");
        messageMP.append(event.getJDA().getSelfUser());

        event.getJDA().getTextChannelById(accueilID).sendMessage(message.build()).queue();
        event.getMember().getUser().openPrivateChannel().complete();
        event.getMember().getUser().getPrivateChannel().sendMessage(messageMP.build()).queue();
        event.getMember().getUser().getPrivateChannel().close();
    }

    public static void MessageNoThingRaid(MessageReceivedEvent event) {
        MessageBuilder message = new MessageBuilder();

        message.append("mauvais salon textuel, cette commande se fait dans le salon ");
        message.append(event.getJDA().getTextChannelById(raidsID));
        message.append("\nYou know nothing, ");
        message.append(event.getAuthor());

        event.getTextChannel().sendMessage(message.build()).queue();
    }

    public static void MessageNoThing(MessageReceivedEvent event) {
        MessageBuilder message = new MessageBuilder();

        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes (sur un salon textuel ou en message privé). \nPS : apprends à écrire.").queue();

        message.append("You know nothing, ");
        message.append(event.getAuthor());

        event.getTextChannel().sendMessage(message.build()).queue();
    }
}
