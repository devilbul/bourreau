package warframe.bourreau.messsage;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class MessageOnEvent {

    public static MessageEmbed messageInfoConfiguration() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Information configuration du bot :");
        message.setDescription("Liste des syntaxes des commandes de configuration, et des options de chacune");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Category :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **category add** <typeCategory> <newCategory>" +
                        "\n    - !config **category remove** <typeCategory>" +
                        "\n    - !config **category modify** <typeCategory>" +
                        "\n       <newCategory>" +
                        "\n    - !config **category info**",
                false);
        message.addField("Command :",
                "- options : add / remove / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **command add** <cmd1> ... <cmdN>" +
                        "\n    - !config **command remove** <cmd1> ... <cmdN>" +
                        "\n    - !config **command info**",
                false);
        message.addField("Functionality :",
                "- options : add / remove / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **functionality add** <func1> ... <funcN>" +
                        "\n    - !config **functionality remove** <func1> ... <funcN>" +
                        "\n    - !config **functionality info**",
                false);
        message.addField("Role :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **role add** <typeRole> <newRole>" +
                        "\n    - !config **role remove** <typeRole>" +
                        "\n    - !config **role modify** <typeRole> <newRole>" +
                        "\n    - !config **role info**",
                false);
        message.addField("TextChannel :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **textchannel add** <typeTextChannel>" +
                        "\n       <newTextChannel>" +
                        "\n    - !config **textchannel remove** <typeTextChannel>" +
                        "\n    - !config **textchannel modify** <typeTextChannel>" +
                        "\n       <newTextChannel>" +
                        "\n    - !config **textchannel info**",
                false);
        message.addField("VoiceChannel :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **voicechannel add** <typeVoiceChannel>" +
                        "\n       <newVoiceChannel>" +
                        "\n    - !config **voicechannel remove** <typeVoiceChannel>" +
                        "\n    - !config **voicechannel modify** <typeVoiceChannel>" +
                        "\n       <newVoiceChannel>" +
                        "\n    - !config **voicechannel info**",
                false);
        message.addField("Les <typeXxxxx>, sont listées avec la commande :",
                "!config xxxx info",
                false);

        return message.build();
    }

    public static MessageEmbed messageInfoCategory() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste categories utiles au bot :");
        message.setDescription("toutes les categories dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Liste :",
                "- clan\n    categorie dans laquelle sera ajouté les salons par les commandes.",
                false);
        message.addField("Pour ajouter", "", false);

        return message.build();
    }

    public static MessageEmbed messageInfoCommand() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste commandes du bot :");
        message.setDescription("toutes les commandes acceptées par le bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Pour ajouter ou supprimer des commandes :", "!config command add <commande1> <commande2> ... <commandeN>\n!config command remove <commande1> <commande2> ... <commandeN>\"", false);
        message.addField("**Troll :**" ,
                "•  pute" +
                        "\n•  rip" +
                        "\n•  segpa" +
                        "\n•  tg" ,
                false);
        message.addField("**Son :**" ,
                "•  acdc" +
                        "\n•  ah" +
                        "\n•  bucher" +
                        "\n•  gg" +
                        "\n•  gogole" +
                        "\n•  leave" +
                        "\n•  nah" +
                        "\n•  pigeon" +
                        "\n*•  son*" +
                        "\n•  souffrir" +
                        "\n•  trump" +
                        "\n•  trumpcomp" +
                        "\n•  trumpcomp2" +
                        "\n•  trumpcomp3",
                false);
        message.addField("**Information :**",
                "•  about" +
                        "\n•  alerts" +
                        "\n•  alliance" +
                        "\n•  baro" +
                        "\n•  clan" +
                        "\n•  discordDE" +
                        "\n•  goals" +
                        "\n•  idee" +
                        "\n•  info" +
                        "\n•  invasions" +
                        "\n•  invite" +
                        "\n•  lead" +
                        "\n•  progres" +
                        "\n•  pvp" +
                        "\n•  pvp hebdo" +
                        "\n•  raid" +
                        "\n•  regle" +
                        "\n•  site" +
                        "\n•  steam" +
                        "\n•  sortie" +
                        "\n•  syndicat" +
                        "\n•  ts3" +
                        "\n•  up" +
                        "\n•  updates" +
                        "\n•  void",
                false);
        message.addField("**Sondage :**",
                "*•  sondage*",
                false);
        message.addField("**Riven :**",
                "*•  riven*",
                false);
        message.addField("**Admin :**",
                "•  aubucher" +
                        "\n•  ban" +
                        "\n•  botnews" +
                        "\n•  __createsalonclan__" +
                        "\n•  deafen" +
                        "\n•  __deletesalonclan__" +
                        "\n•  kick" +
                        "\n•  mute" +
                        "\n•  ping" +
                        "\n•  __tenno__" +
                        "\n•  unban" +
                        "\n•  undeafen" +
                        "\n•  unmute",
                false);
        message.addField("Les commandes soulignées ont besoin de configurer d'autres choses :", "categories, rôles, salon textuel, salon vocal.", false);

        return message.build();
    }

    public static MessageEmbed messageInfoFunctionality() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste fonctionnalités du bot :");
        message.setDescription("toutes les fonctionnalités du bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Pour ajouter ou supprimer des commandes :", "!config command add <fonctionnalite1> <fonctionnalite2> ... <fonctionnaliteN>\n!config command remove <commande1> <commande2> ... <commandeN>\"", false);
        message.addField("Liste :",
                "- __presentation__\n    demande une présentation aux nouveaux arrivant, pour " +
                        "\n    avoir l'accès au reste du serveur, grâce au rôle tenno",
                false);
        message.addField("Les fonctionnalités soulignées ont besoin de configurer d'autres choses :", "categories, rôles, salon textuel, salon vocal.", false);

        return message.build();
    }

    public static MessageEmbed messageInfoRole() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste rôles utiles au bot :");
        message.setDescription("toutes les rôles dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Liste :",
                "- leader clan\n    rôle pour la recrutement " +
                "\n- tenno\n    rôle minimal pour utiliser le bot" +
                "\n- admin\n    rôle qui administrer le serveur" +
                "\n- modo\n    rôle qui permet d'avoir accès à une partie des " +
                        "\n    commandes admins, afin de pouvoir modérer" +
                "\n- bucher\n    rôle ajouté, par la commande !aubucher" ,
                false);

        return message.build();
    }

    public static MessageEmbed messageInfoTextChannel() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste salons textuels utiles au bot :");
        message.setDescription("toutes les salons textuels dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Liste :",
                "- accueil\n    salon textuel principal, où il y aura des annonces " +
                        "\n    importantes" +
                        "\n- botSpam\n    salon dédié aux commandes de bot" +
                        "\n- raids\n    salon de regroupement pour les raids",
                false);

        return message.build();
    }

    public static MessageEmbed messageInfoVoiceChannel() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste salons vocaux du bot :");
        message.setDescription("toutes les salons vocaux dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        message.addField("Liste :",
                "- bucher\n    salon vers lequelle, sera envoyer l'utilisateur, avec la " +
                        "\n    commande aubucher",
                false);

        return message.build();
    }

    public static MessageEmbed messagePremierConnection() {
        EmbedBuilder message = new EmbedBuilder();

        message.addField("Merci d'avoir ajouter mon bot à votre serveur.", "Pour le configurer, tapez ***!config info*** dans un salon sur votre serveur, pour avoir toutes les informations nécessaires.", false);
        message.setTitle("Information Importante :", null);
        message.setDescription("Configuration du bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return message.build();
    }

    public static void messageDeDeconnection(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
            String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");
            MessageBuilder shutdown = new MessageBuilder();

            shutdown.append("Je vais aller faire un som, à plus en LAN.\n");
            shutdown.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));

            event.getJDA().getTextChannelById(textChannelID).sendMessage(shutdown.build()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void messageDeBienvenue(GuildMemberJoinEvent event) {
        try {
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("accueil").getString("idTextChannel");
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
            messageMP.append(event.getGuild().getTextChannelById(textChannelID));
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

            event.getGuild().getTextChannelById(textChannelID).sendMessage(message.build()).queue();
            event.getMember().getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void messageNoThingRaid(MessageReceivedEvent event) {
        try {
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String textChannelID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("raids").getString("idTextChannel");
            MessageBuilder message = new MessageBuilder();

            message.append("mauvais salon textuel, cette commande se fait dans le salon ");
            message.append(event.getJDA().getTextChannelById(textChannelID));
            message.append("\nYou know nothing, ");
            message.append(event.getAuthor());

            event.getTextChannel().sendMessage(message.build()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void messageNoThing(MessageReceivedEvent event) {
        MessageBuilder message = new MessageBuilder();

        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes (sur un salon textuel ou en message privé).").queue();

        message.append("You know nothing, ");
        message.append(event.getAuthor());

        event.getTextChannel().sendMessage(message.build()).queue();
    }

    public static void messageReglement(MessageReceivedEvent event) {
        try {
            String configEmotes = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configEmote.json")));
            String configRoles = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configRole.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            JSONObject configRolesJson = new JSONObject(configRoles);
            JSONObject configTextChannelJson = new JSONObject(configTextChannel);
            String roleAdminID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("admin").getString("idRole");
            String roleModoID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("modo").getString("idRole");
            String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("180419578554220545").getString("idEmote");
            String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("180419578554220545").getString("idServer");
            String textChannelRaidsID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("raids").getString("idTextChannel");
            String textChannelBotSpamID = configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject("botSpam").getString("idTextChannel");
            MessageBuilder reglement = new MessageBuilder();

            reglement.append("***=============================================================***\n");
            reglement.append("Salut et bienvenue sur le serveur Discord de l'Alliance **");
            reglement.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
            reglement.append("French Connection");
            reglement.append(event.getJDA().getGuildById(serverID).getEmoteById(emoteID));
            reglement.append(" **\n\n***=>***   __Quelques règles de bonne conduite :__\n\n `(1) Soyez respectueux.`\n");
            reglement.append(" `(2) Ne soyez pas hors-sujet et prennez en compte ce que les gens disent avant de débarquer avec un hors-sujet total.`\n");
            reglement.append(" `(3) Ne soyez pas haineux. On est ici pour passer un bon moment. Twitter est un meilleur endroit pour raconter comment vous détestez tout le monde.`\n");
            reglement.append(" `(4) Le piratage c'est pas très bien. Donc n'en parlez pas. ;)`\n\n");
            reglement.append("Bien sur, les autres **__règles de bienséance__** s'appliquent aussi, et tout ce qui est valable sur les forums ou en jeu l'est ici aussi, donc pas d'insultes, \n");
            reglement.append("devbashing, etc.\n\n\n");
            reglement.append("***=>***   __Quelques information pratique :__\n\n");
            reglement.append(" `(1) Si vous rencontrez un problème, vous pouvez mentionner les `");
            reglement.append(event.getJDA().getRoleById(roleAdminID));
            reglement.append("`et les `");
            reglement.append(event.getJDA().getRoleById(roleModoID));
            reglement.append("`, ou sinon vous pouvez aussi l'envoyer au bot, en message privé, avec la commande !claim.`\n `(2) Pour toutes les commandes de bot");
            reglement.append(", faite-les dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelBotSpamID));
            reglement.append("`.`\n `(3) Si vous ne souhaitez pas avoir de notification d'un salon textuel en particulier, fait un clic droit sur celui-ci, puis clicquez sur la checkbox.`\n\n");
            reglement.append("***=>***   __Pour ");
            reglement.append(event.getJDA().getSelfUser());
            reglement.append(" :__\n\n `(1) La plupart des commandes se font dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelBotSpamID));
            reglement.append("`.`\n `(2) Les commandes relative aux raids, se font dans le salon textuel `");
            reglement.append(event.getJDA().getTextChannelById(textChannelRaidsID));
            reglement.append("`.`\n `(3) Donc évitez tout spam de commande, dans les salons textuels non-dédié pour cela, sous peine de sanction.`\n");
            reglement.append("\n***=============================================================***");

            event.getTextChannel().sendMessage(reglement.build()).queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
