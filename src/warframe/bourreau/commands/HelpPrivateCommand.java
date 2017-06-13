package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import static warframe.bourreau.InitID.raidsID;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class HelpPrivateCommand extends Command {

    public static void HelpPrivate(PrivateMessageReceivedEvent event) {
        if (event.getMessage().getContent().contains(" ")) {
            String help = recupString(event.getMessage().getContent().toLowerCase());

            switch (help) {
                // command son
                case "ah":
                case "bucher":
                case "gg":
                case "gogole":
                case "nah":
                case "pigeon":
                case "souffrir":
                case "trump":
                    HelpSonPrivate(event, help);
                    break;
                // commande troll
                case "pute":
                case "RIP":
                case "rip":
                case "segpa":
                case "tg":
                    HelpTrollPrivate(event, help);
                    break;
                // commandes que tout le monde peut faire
                case "alliance":
                case "clan":
                case "discordwf":
                case "idée":
                case "info":
                case "invite":
                case "lead":
                case "progres":
                case "raid":
                case "site":
                case "steam":
                case "ts3":
                case "up":
                case "updates":
                    HelpGeneralPrivate(event, help);
                    break;
                // commande riven
                case "riven":
                case "riven influence":
                case "riven nom":
                    HelpRivenPrivate(event, help);
                    break;
                // commande sondage
                case "sondage":
                case "sondage affiche":
                case "sondage reponses":
                case "sondage clear":
                case "sondage create":
                case "sondage resultat":
                case "sondage vote":
                    HelpSondagePrivate(event, help);
                    break;
                // commande raid
                case "affiche":
                case "cancel":
                case "present":
                    HelpRaidPrivate(event, help);
                    break;
                // commande admin / modo
                case "aubucher":
                case "deafen":
                case "getbans":
                case "kick":
                case "mute":
                case "tenno":
                case "undeafen":
                case "unmute":
                    HelpAdminPrivate(event, help);
                    HelpModoPrivate(event, help);
                    break;
                case "addclan":
                case "ban":
                case "removeclan":
                case "setgame":
                case "traiteriven":
                case "unban":
                    HelpAdminPrivate(event, help);
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    event.getAuthor().openPrivateChannel().complete();

                    event.getAuthor().getPrivateChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getAuthor().getPrivateChannel().sendMessage(message.build()).queue();
                    event.getAuthor().getPrivateChannel().close();
                    break;
            }
        }
        else {
            if (FindAdminPrive(event, event.getAuthor())) {
                MessageBuilder adminCommade = new MessageBuilder();

                adminCommade.append("**Commandes admin :**" +
                        "\n     !aubucher" +
                        "\n     !ban" +
                        "\n     !deafen" +
                        "\n     !getbans" +
                        "\n     !kick" +
                        "\n     !mute" +
                        "\n     !setgame" +
                        "\n     !tenno" +
                        "\n     !traiteriven" +
                        "\n     !unban" +
                        "\n     !undeafen" +
                        "\n     !unmute" +
                        "\n\n");
                adminCommade.append("**!help <nom_de_la_commande>** pour plus d'information sur la commande.");

                event.getAuthor().openPrivateChannel().complete();
                event.getAuthor().getPrivateChannel().sendMessage(adminCommade.build()).queue();
                event.getAuthor().getPrivateChannel().close();
            } else if (FindModoPrive(event, event.getAuthor())) {
                MessageBuilder modoCommade = new MessageBuilder();

                modoCommade.append("**Commandes modo :**" +
                        "\n     !aubucher" +
                        "\n     !deafen" +
                        "\n     !getbans" +
                        "\n     !kick" +
                        "\n     !mute" +
                        "\n     !tenno" +
                        "\n     !undeafen" +
                        "\n     !unmute" +
                        "\n\n");
                modoCommade.append("**!help <nom_de_la_commande>** pour plus d'information sur la commande.");

                event.getAuthor().openPrivateChannel().complete();
                event.getAuthor().getPrivateChannel().sendMessage(modoCommade.build()).queue();
                event.getAuthor().getPrivateChannel().close();
            }

            MessageBuilder help = new MessageBuilder();

            help.append("**Commandes informative :**" +
                    "\n     !alliance" +
                    "\n     !clan" +
                    "\n     !discordDE" +
                    "\n     !idée ou !idee" +
                    "\n     !info" +
                    "\n     !invite" +
                    "\n     !lead" +
                    "\n     !progres" +
                    "\n     !raid" +
                    "\n     !steam" +
                    "\n     !ts3" +
                    "\n     !up" +
                    "\n     !updates" +
                    "\n\n");
            help.append("**Commandes pour les rivens :**" +
                    "\n     !riven influence" +
                    "\n     !riven nom" +
                    "\n\n");
            help.append("**Commandes pour les sondages :**" +
                    "\n     !sondage create" +
                    "\n     !sondage choix" +
                    "\n     !sondage vote" +
                    "\n     !sondage affiche" +
                    "\n     !sondage resultat" +
                    "\n     !sondage clear" +
                    "\n\n");
            help.append("**Commandes pour les sons :**" +
                    "\n     !ah" +
                    "\n     !bucher" +
                    "\n     !gg" +
                    "\n     !gogole" +
                    "\n     !nah" +
                    "\n     !pigeon" +
                    "\n     !souffrir" +
                    "\n     !trump" +
                    "\n\n");
            help.append("**!help <nom_de_la_commande>** pour plus d'information sur la commande.");

            event.getAuthor().openPrivateChannel().complete();
            event.getAuthor().getPrivateChannel().sendMessage(help.build()).queue();
            event.getAuthor().getPrivateChannel().close();
        }
    }

    private static void HelpSonPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "ah":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "bucher":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "gg":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "gogole":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "nah":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "pigeon":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "souffrir":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
            case "trump":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   être connecté sur un salon vocal")
                        .append("\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();
    }

    private static void HelpTrollPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "pute":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         à découvrir");
                break;
            case "RIP":
            case "rip":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         à découvrir");
                break;
            case "segpa":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         à découvrir");
                break;
            case "tg":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         à découvrir");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();
    }

    private static void HelpGeneralPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "alliance":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         affiche des informations à propos de l'Alliance");
                break;
            case "clan":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         affiche les clans faisant paarti de l'alliance");
                break;
            case "discordwf":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien vers le discord warframe officiel");
                break;
            case "idée":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien vers un google doc, pour proposer une idée de commande");
                break;
            case "info":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien vers site, qui affiche")
                        .append("\n                    Alerts").append("\n                    Invasions").append("\n                    News")
                        .append("\n                    Darvo Daily Deals").append("\n                    Void Traders")
                        .append("\n                    Sorties(WIP)").append("\n                    Void Fissures(WIP)")
                        .append("\n                    Market Items").append("\n                    Earth Day/Night Cycle");
                break;
            case "invite":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien d'invitation à rejoindre ce serveur");
                break;
            case "lead":
                helpMessage.append("\n**syntaxe 1 :**   !").append(help).append(" <nom du clan>").append("\n**effet :**            affiche les leaders du clan <nom du clan>");
                helpMessage.append("\n\n**syntaxe 2 :**   !").append(help).append("\n**effet :**            affiche tous les clans, avec leurs leaders");
                break;
            case "progres":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien vers le tableau Trello, montrant ma progression");
                break;
            case "raid":
                helpMessage.append("\n**syntaxe 1 :**   !").append(help).append("\n**effet :**            donne des stats de raid globales de l'auteur");
                helpMessage.append("\n\n**syntaxe 2 :**   !").append(help).append(" <pseudo>").append("\n**effet :**            donne des stats de raid globales de <pseudo>");
                helpMessage.append("\n\n**syntaxe 3 :**   !").append(help).append(" detail").append("\n**effet :**            donne des stats de raid détaillées de l'auteur");
                helpMessage.append("\n\n**syntaxe 4 :**   !").append(help).append(" detail <pseudo>").append("\n**effet :**            donne des stats de raid détaillés de <pseudo>");
                break;
            case "site":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne le lien du site de l'alliance");
                break;
            case "steam":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne le lien du groupe steam de l'alliance");
                break;
            case "ts3":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne le lien du serveur teamspeak de l'alliance");
                break;
            case "up":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne un lien vers le wiki des futurs ajouts");
                break;
            case "updates":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         donne des liens vers la dernière update et le dernier hotfix");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();
    }

    private static void HelpSondagePrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        helpMessage.append("\n\nles réponses peuvent être des phrases " +
                "\nil faut bien respecter la syntaxe" +
                "\nc'est-à-dire mettre ' / ' pour séparer chaque réponse\n");

        switch (help) {
            case "sondage":
                helpMessage.append("\nPour créer un sondage :" +
                        "\n- **!sondage create <question>**, pour créer le sondage," +
                        "\n- **!sondage reponses <réponse1> / <réponse2> / ... / <réponseN>**, pour déclarer les réponses au " +
                        "\n   sondage");
                helpMessage.append("\n\naprès avoir fait ses deux commandes, le sondage est correctement déclaré" +
                        "\nil sera épinglé dans le salon textuel dans lequel la commande était faite");
                helpMessage.append("\n\npour voter :" +
                        "\n**!sondage vote <réponse>**");
                helpMessage.append("\n\npour le supprimer :" +
                        "\n**!sondage clear**");
                helpMessage.append("\n\npour l'afficher :" +
                        "\n**!sondage affiche**");
                helpMessage.append("\n\npour voir les résultat :" +
                        "\n**!sondage resultat**");
                helpMessage.append("\n\nles réponses peuvent être des phrases " +
                        "\nil faut bien respecter la syntaxe" +
                        "\nc'est-à-dire mettre ' / ' pour séparer chaque réponse");
                helpMessage.append("\n\n**!help sondage <nom_de_la_commande>** pour plus d'information sur la commande.");
                break;
            case "sondage affiche":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   avoir un sondage en cours")
                        .append("\n**effet :**            affiche le sondage en cours, question + réponses possibles");
                break;
            case "sondage reponses":
                helpMessage.append("\n**syntaxe 1 :**      !").append(help).append(" <réponse1> / <réponse2>").append("\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande")
                        .append("\n**effet :**               ajoute <réponse1> et <réponse2>, commme réponses possibles");
                helpMessage.append("\n\n**syntaxe 2 :**      !").append(help).append(" <réponse1> / <réponse2> / ... / <réponsNe>")
                        .append("\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande")
                        .append("\n**effet :**               ajoute <réponse1>, <réponse2>, ... et <réponseN>, commme réponses possibles");
                break;
            case "sondage clear":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   avoir un sondage en cours")
                        .append("\n**effet :**            supprime le sondage en cours");
                break;
            case "sondage create":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" <question>").append("\n**condition :**   ne pas avoir un sondage en cours")
                        .append("\n**effet :**            créer le sondage, avec la question <question>");
                break;
            case "sondage resultat":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   avoir un sondage en cours")
                        .append("\n**effet :**            affiche les résultats du sondage en cours");
                break;
            case "sondage vote":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" <réponse>").append("\n**condition :**   avoir un sondage en cours")
                        .append("\n**effet :**            permet de voter");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();

    }

    private static void HelpRaidPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "affiche":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   faisable uniquement dans ");
                helpMessage.append(event.getJDA().getTextChannelById(raidsID));
                helpMessage.append("\n**effet :**            affiche les personnes ayant confirmée leur présence pour le(s) raid(s)");
                break;
            case "cancel":
                helpMessage.append("\n**syntaxe :**        !").append(help).append("\n**conditions :**   avoir fait la commande !present plutôt dans la journée")
                        .append("\n                         faisable uniquement dans ");
                helpMessage.append(event.getJDA().getTextChannelById(raidsID));
                helpMessage.append("\n**effet :**              décomptabilise la présence de l'auteur, pour le(s) raid(s) du jour");
                break;
            case "present":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   faisable uniquement dans ");
                helpMessage.append(event.getJDA().getTextChannelById(raidsID));
                helpMessage.append("\n**effet :**            comptabilise la présence de l'auteur, pour le(s) raid(s) du jour");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();
    }

    private static void HelpRivenPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "riven":

                break;
            case "riven influence":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" <nom de l'objet>").append("\n**effet :**         donne l'influence de l'objet <nom de l'objet>");
                break;
            case "riven nom":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" <nom de riven>").append("\n**effet :**         donne les statistiques par rapport au nom du riven <nom de riven>");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();
    }

    private static void HelpAdminPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "addclan":
                helpMessage.append("\n**syntaxe 1 :**   !").append(help).append(" <nom du clan> / <nom du leader>")
                        .append("\n**condition :**   le clan <nom du clan> ne doit pas être dans alliance.json")
                        .append("\n**effet :**            ajoute le clan <nom du clan> a alliance.json");
                helpMessage.append("\n\n**syntaxe 2 :**   !").append(help).append(" <nom du clan> <leader1> / <leader2> / ... / <leaderN>")
                        .append("\n**condition :**   le clan <nom du clan> ne doit pas être dans alliance.json")
                        .append("\n**effet :**            ajoute le clan <nom du clan> a alliance.json");
                break;
            case "aubucher":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            envoye au bucher l'utilisateur <pseudo>");
                break;
            case "ban":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" @<pseudo>").append("\n**effet :**         ban du serveur l'utilisateur <pseudo>");
                break;
            case "deafen":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            retire le droit d'entendre à l'utilisateur <pseudo>");
                break;
            case "getbans":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         affiche les utilisateurs bannis");
                break;
            case "kick":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" @<pseudo>").append("\n**effet :**         expulse du serveur l'utilisateur <pseudo>");
                break;
            case "mute":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            retire le droit de parler à l'utilisateur <pseudo>");
                break;
            case "removeclan":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" <nom du clan>").append("\n**condition :**   le clan <nom du clan> doit être dans le fichier alliance.json")
                        .append("\n**effet :**            supprime le clan <nom du clan> de alliance.json.");
                break;
            case "setgame":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" <nouveau jeu>").append("\n**effet :**         change le jeu auquel joue le bot");
                break;
            case "tenno":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit être nouveau sur le serveur, ou aucun rôle, en particulier le rôle tenno")
                        .append("\n**effet :**            contourne la présentation, pour l'accès au reste du discord");
                break;
            case "traiteriven":
                helpMessage.append("\n**syntaxe :**      !").append(help).append("\n**condition :**   avoir upload le fichier RivenMods.txt")
                        .append("\n**effet :**            traite le fichier RivenMods.txt, pour obtenir les influences des rivens");
                break;
            case "unban":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" <pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit être banni")
                        .append("\n**effet :**            deban du serveur l'utilisateur <pseudo> ");
                break;
            case "undeafen" :
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> est assourdi soit par un admin/modo, ou soit par commnande **!deafen**")
                        .append("\n**effet :**            redonne le droit d'entendre à l'utilisateur <pseudo>");
                break;
            case "unmute":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> est muté soit par un admin/modo, ou soit par commnande **!mute**")
                        .append("\n**effet :**            redonne le droit de parler à l'utilisateur <pseudo>");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();

    }

    private static void HelpModoPrivate(PrivateMessageReceivedEvent event, String help) {
        MessageBuilder helpMessage = new MessageBuilder();

        helpMessage.append("__**Aide pour :**__   ").append(help).append("\n");

        switch (help) {
            case "aubucher":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            envoye au bucher l'utilisateur <pseudo>");
                break;
            case "deafen":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            retire le droit d'entendre à l'utilisateur <pseudo>");
                break;
            case "getbans":
                helpMessage.append("\n**syntaxe :**   !").append(help).append("\n**effet :**         affiche les utilisateurs bannis");
                break;
            case "kick":
                helpMessage.append("\n**syntaxe :**   !").append(help).append(" @<pseudo>").append("\n**effet :**         expulse du serveur l'utilisateur <pseudo>");
                break;
            case "mute":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal")
                        .append("\n**effet :**            retire le droit de parler à l'utilisateur <pseudo>");
                break;
            case "tenno":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> doit être nouveau sur le serveur, ou aucun rôle, en particulier le rôle tenno")
                        .append("\n**effet :**            contourne la présentation, pour l'accès au reste du discord");
                break;
            case "undeafen" :
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> est assourdi soit par un admin/modo, ou soit par commnande **!deafen**")
                        .append("\n**effet :**            redonne le droit d'entendre à l'utilisateur <pseudo>");
                break;
            case "unmute":
                helpMessage.append("\n**syntaxe :**      !").append(help).append(" @<pseudo>").append("\n**condition :**   l'utilisateur <pseudo> est muté soit par un admin/modo, ou soit par commnande **!mute**")
                        .append("\n**effet :**            redonne le droit de parler à l'utilisateur <pseudo>");
                break;
        }

        event.getAuthor().openPrivateChannel().complete();
        event.getAuthor().getPrivateChannel().sendMessage(helpMessage.build()).queue();
        event.getAuthor().getPrivateChannel().close();

    }
}
