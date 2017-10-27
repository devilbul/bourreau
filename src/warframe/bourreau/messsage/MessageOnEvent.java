package warframe.bourreau.messsage;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import static warframe.bourreau.InitID.*;

public class MessageOnEvent {

    public static void MessageDeDeconnection(MessageReceivedEvent event) {
        MessageBuilder shutdown = new MessageBuilder();

        shutdown.append("Je vais aller faire un som, à plus en LAN.\n");
        shutdown.append(event.getJDA().getEmoteById(bourreauID));

        event.getJDA().getTextChannelById(accueilID).sendMessage(shutdown.build()).queue();
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
        messageMP.append("\n\nSi tu joues à Warframe, et que souhaites rejoindre notre alliance, fais ta demande en message privé au bot");
        messageMP.append(", \navec la commande **__!canditate__** <message>, un admin ou un chef de clan va te répondre.");
        messageMP.append("\n\nIl faut respecter certaines règles qui se situent dans la commande **__!regle__** ");
        messageMP.append(", dont je t'invite à lire fortement.");
        messageMP.append("\n\nPour toute question, rapport de problème, réclamation, veuillez l'envoyer au bot, en message privé, avec la commande **__!claim__** <message>");
        messageMP.append(".\nPas de spam dans ce salon, merci de faire attention.");
        messageMP.append("\n\nAu plaisir de te revoir sur le discord.");
        messageMP.append("\n\nCordialement, ");
        messageMP.append(event.getJDA().getSelfUser());
        messageMP.append("\n\nPS : pour tous problèmes lié au bot, veuillez contacter ");
        messageMP.append(event.getGuild().getOwner().getUser());

        event.getJDA().getTextChannelById(accueilID).sendMessage(message.build()).queue();
        event.getMember().getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
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

    public static void MessageReglement(MessageReceivedEvent event) {
        MessageBuilder reglement = new MessageBuilder();

        reglement.append("***=============================================================***\n");
        reglement.append("Salut et bienvenue sur le serveur Discord de l'Alliance **");
        reglement.append(event.getJDA().getEmoteById(allianceID));
        reglement.append("French Connection");
        reglement.append(event.getJDA().getEmoteById(allianceID));
        reglement.append(" **\n\n***=>***   __Quelques règles de bonne conduite :__\n\n `(1) Soyez respectueux.`\n");
        reglement.append(" `(2) Ne soyez pas hors-sujet et prennez en compte ce que les gens disent avant de débarquer avec un hors-sujet total.`\n");
        reglement.append(" `(3) Ne soyez pas haineux. On est ici pour passer un bon moment. Twitter est un meilleur endroit pour raconter comment vous détestez tout le monde.`\n");
        reglement.append(" `(4) Le piratage c'est pas très bien. Donc n'en parlez pas. ;)`\n\n");
        reglement.append("Bien sur, les autres **__règles de bienséance__** s'appliquent aussi, et tout ce qui est valable sur les forums ou en jeu l'est ici aussi, donc pas d'insultes, \n");
        reglement.append("devbashing, etc.\n\n\n");
        reglement.append("***=>***   __Quelques information pratique :__\n\n");
        reglement.append(" `(1) Si vous rencontrez un problème, vous pouvez mentionner les `");
        reglement.append(event.getJDA().getRoleById(adminID));
        reglement.append("`et les `");
        reglement.append(event.getJDA().getRoleById(modoID));
        reglement.append("`, ou sinon vous pouvez aussi l'envoyer au bot, en message privé, avec la commande !claim.`\n `(2) Pour toutes les commandes du bot `");
        reglement.append(event.getJDA().getUsersByName("Genesis", true).get(0));
        reglement.append("`, faite-les dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(botSpamID));
        reglement.append("`.`\n `(3) Si vous ne souhaitez pas avoir de notification d'un salon textuel en particulier, fait un clic sur celui-ci, puis clicquez sur la checkbox.`\n\n");
        reglement.append("***=>***   __Pour ");
        reglement.append(event.getJDA().getSelfUser());
        reglement.append(" :__\n\n `(1) La plupart des commandes se font dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(botSpamID));
        reglement.append("`.`\n `(2) Les commandes relative aux raids, se font dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(raidsID));
        reglement.append("`.`\n `(3) Donc évitez tout spam de commande, dans les salons textuels non-dédié pour cela, sous peine de sanction.`\n");
        reglement.append("\n***=============================================================***");

        event.getTextChannel().sendMessage(reglement.build()).queue();
    }

    public static void MessageReglementPrive(PrivateMessageReceivedEvent event) {
        MessageBuilder reglement = new MessageBuilder();

        reglement.append("***=============================================================***\n");
        reglement.append("Salut et bienvenue sur le serveur Discord de l'Alliance **");
        reglement.append(event.getJDA().getEmoteById(allianceID));
        reglement.append("French Connection");
        reglement.append(event.getJDA().getEmoteById(allianceID));
        reglement.append(" **\n\n***=>***   __Quelques règles de bonne conduite :__\n\n `(1) Soyez respectueux.`\n");
        reglement.append(" `(2) Ne soyez pas hors-sujet et prennez en compte ce que les gens disent avant de débarquer avec un hors-sujet total.`\n");
        reglement.append(" `(3) Ne soyez pas haineux. On est ici pour passer un bon moment. Twitter est un meilleur endroit pour raconter comment vous détestez tout le monde.`\n");
        reglement.append(" `(4) Le piratage c'est pas très bien. Donc n'en parlez pas. ;)`\n\n");
        reglement.append("Bien sur, les autres **__règles de bienséance__** s'appliquent aussi, et tout ce qui est valable sur les forums ou en jeu l'est ici aussi, donc pas d'insultes, \n");
        reglement.append("devbashing, etc.\n\n\n");
        reglement.append("***=>***   __Quelques information pratique :__\n\n");
        reglement.append(" `(1) Si vous rencontrez un problème, vous pouvez mentionner les `");
        reglement.append(event.getJDA().getRoleById(adminID));
        reglement.append("`et les `");
        reglement.append(event.getJDA().getRoleById(modoID));
        reglement.append("`, ou sinon vous pouvez aussi l'envoyer au bot, en message privé, avec la commande !claim.`\n `(2) Pour toutes les commandes du bot `");
        reglement.append(event.getJDA().getUsersByName("Genesis", true).get(0));
        reglement.append("`, faite-les dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(botSpamID));
        reglement.append("`.`\n `(3) Si vous ne souhaitez pas avoir de notification d'un salon textuel en particulier, fait un clic sur celui-ci, puis clicquez sur la checkbox.`\n\n");
        reglement.append("***=>***   __Pour ");
        reglement.append(event.getJDA().getSelfUser());
        reglement.append(" :__\n\n `(1) La plupart des commandes se font dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(botSpamID));
        reglement.append("`.`\n `(2) Les commandes relative aux raids, se font dans le salon textuel `");
        reglement.append(event.getJDA().getTextChannelById(raidsID));
        reglement.append("`.`\n `(3) Donc évitez tout spam de commande, dans les salons textuels non-dédié pour cela, sous peine de sanction.`\n");
        reglement.append("\n***=============================================================***");

        event.getAuthor().openPrivateChannel().complete().sendMessage(reglement.build()).queue();
    }
}
