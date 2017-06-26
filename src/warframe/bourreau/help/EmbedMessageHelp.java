package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedMessageHelp {

    public static MessageEmbed MessageHelpSon(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        /*switch (help) {
            case "ah":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "bucher":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "gg":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "gogole":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "nah":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "pigeon":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "souffrir":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
            case "trump":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);
                break;
        }*/

        helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   être connecté sur un salon vocal\n**effet :**            le bot rejoint le salon vocal où est l'auteur, et joue le son", false);

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpTroll(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        /*switch (help) {
            case "pute":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         à découvrir", false);
                break;
            case "RIP":
            case "rip":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         à découvrir", false);
                break;
            case "segpa":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         à découvrir", false);
                break;
            case "tg":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         à découvrir", false);
                break;
        }*/

        helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         à découvrir", false);

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpGeneral(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "alerts":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 ** :    !" + help + "\n**effet :**         affiche toutes les alertes en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 ** :    !" + help + " interest" +  "\n**effet :**         affiche toutes les alertes en cours avec des récompenses intéressante", false);
                break;
            case "alliance":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche des informations à propos de l'Alliance", false);
                break;
            case "baro":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche le temps restant avant l'arrivé de Baro Ki'Teer, en plus du relais sur lequel il sera", false);
                break;
            case "clan":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche les clans faisant paarti de l'alliance", false);
                break;
            case "discordwf":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers le discord warframe officiel", false);
                break;
            case "idée":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers un google doc, pour proposer une idée de commande", false);
                break;
            case "info":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers site, qui affiche\n                    Alerts" +
                        "\n                    News\n                    Invasions\n                    Darvo Daily Deals\n                    Void Traders" +
                        "\n                    Sorties(WIP)\n                    Void Fissures(WIP)\n                    Market Items\n                    Earth Day/Night Cycle", false);
                break;
            case "invasions":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 ** :    !" + help + "\n**effet :**         affiche toutes les invasions en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 ** :    !" + help + " interest" +  "\n**effet :**         affiche toutes les invasions en cours avec des récompenses intéressante", false);
                break;
            case "invite":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien d'invitation à rejoindre ce serveur", false);
                break;
            case "lead":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 :**   !" + help + " <nom du clan>" + "\n**effet :**            affiche les leaders du clan <nom du clan>", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 :**   !" + help + "\n**effet :**            affiche tous les clans, avec leurs leaders", false);
                break;
            case "progres":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers le tableau Trello, montrant ma progression", false);
                break;
            case "pvp":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche les défis conclave quotidien", false);
                break;
            case "pvp hebdo":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche le temps restant pour les défis hebdomadaire", false);
                break;
            case "raid":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 :**   !" + help + "\n**effet :**            donne des stats de raid globales de l'auteur", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 :**   !" + help + " <pseudo>" + "\n**effet :**            donne des stats de raid globales de <pseudo>", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 3 :**   !" + help + " detail" + "\n**effet :**            donne des stats de raid détaillées de l'auteur", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 4 :**   !" + help + " detail <pseudo>" + "\n**effet :**            donne des stats de raid détaillés de <pseudo>", false);
                break;
            case "site":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne le lien du site de l'alliance", false);
                break;
            case "steam":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne le lien du groupe steam de l'alliance", false);
                break;
            case "sortie":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche la sortie actuelle", false);
                break;
            case "syndicat":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 :**   !" + help + "\n**effet :**         affiche les missions syndicales actuelles", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 :**   !" + help + " <syndicat>" + "\n**parmi :**       SteelMeridian" +
                        "\n                    RedVeil\n                    Perrin\n                    CephalonSuda\n                    NewLoka\n                    Arbiters", false);
                break;
            case "ts3":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne le lien du serveur teamspeak de l'alliance", false);
                break;
            case "up":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers le wiki des futurs ajouts", false);
                break;
            case "updates":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne des liens vers la dernière update et le dernier hotfix", false);
                break;
            case "void":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 ** :    !" + help + "\n**effet :**         affiche toutes les missions fissure en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 ** :    !" + help + " <tiers>" +  "\n**effet :**         affiche toutes les missions fissures du tiers <tiers> en cours", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpSondage(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        helpMessage.addField("__Aide pour__ :   " + help, "\n\nles réponses peuvent être des phrases \nil faut bien respecter la syntaxe" +
                "\nc'est-à-dire mettre ' / ' pour séparer chaque réponse\n", false);

        switch (help) {
            case "sondage":
                helpMessage.addField("__Aide pour__ :   " + help, "\nPour créer un sondage :\n- **!sondage create <question>**, pour créer le sondage," +
                        "\n- **!sondage reponses <réponse1> / <réponse2> / ... / <réponseN>**, pour déclarer les réponses au \n   sondage" +
                        "\n\naprès avoir fait ses deux commandes, le sondage est correctement déclaré\nil sera épinglé dans le salon textuel dans lequel la commande était faite" +
                        "\n\npour voter :\n**!sondage vote <réponse>**\n\npour le supprimer :\n**!sondage clear**\n\npour l'afficher :\n**!sondage affiche**" +
                        "\n\npour voir les résultat :\n**!sondage resultat**\n\nles réponses peuvent être des phrases \nil faut bien respecter la syntaxe" +
                        "\nc'est-à-dire mettre ' / ' pour séparer chaque réponse\n\n**!help sondage <nom_de_la_commande>** pour plus d'information sur la commande.", false);
                break;
            case "sondage affiche":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   avoir un sondage en cours" +
                        "\n**effet :**            affiche le sondage en cours, question + réponses possibles", false);
                break;
            case "sondage reponses":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 :**      !" + help + " <réponse1> / <réponse2>" + "\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande" +
                        "\n**effet :**               ajoute <réponse1> et <réponse2>, commme réponses possibles", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 :**      !" + help + " <réponse1> / <réponse2> / ... / <réponsNe>" +
                        "\n**condition :**      avoir un sondage en cours et ne pas déjà avoir fait cette commande\n**effet :**               ajoute <réponse1>, <réponse2>, ... et <réponseN>, commme réponses possibles", false);
                break;
            case "sondage clear":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   avoir un sondage en cours\n**effet :**            supprime le sondage en cours", false);
                break;
            case "sondage create":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <question>" + "\n**condition :**   ne pas avoir un sondage en cours" +
                        "\n**effet :**            créer le sondage, avec la question <question>", false);
                break;
            case "sondage resultat":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   avoir un sondage en cours\n**effet :**            affiche les résultats du sondage en cours", false);
                break;
            case "sondage vote":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <réponse>" + "\n**condition :**   avoir un sondage en cours\n**effet :**            permet de voter", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpRaid(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "affiche":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   faisable uniquement dans #raids" +
                        "\n**effet :**            affiche les personnes ayant confirmée leur présence pour le(s) raid(s)", false);
                break;
            case "cancel":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**conditions :**   avoir fait la commande !present plutôt dans la journée" +
                        "\n                         faisable uniquement dans #raids\n**effet :**              décomptabilise la présence de l'auteur, pour le(s) raid(s) du jour", false);
                break;
            case "present":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**condition :**   faisable uniquement dans #raids" +
                        "\n**effet :**            comptabilise la présence de l'auteur, pour le(s) raid(s) du jour", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpRiven(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "riven":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**Coming Soon**", false);

                break;
            case "riven calcul":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**Coming Soon**", false);
                break;
            case "riven defi":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche tous les défis possibles", false);
                break;
            case "riven influence":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nom de l'objet>" + "\n**effet :**         donne l'influence de l'objet <nom de l'objet>", false);
                break;
            case "riven nom":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nom du riven>" + "\n**effet :**         donne les statistiques par rapport au nom du riven <nom de riven>", false);
                break;
            case "riven stat":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <catégorie>" + "\n**effet :**         donne toutes les statistique pour la catégorie <catégorie>", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpAdmin(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "addclan":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1 :**   !" + help + " <nom du clan> / <nom du leader>" + "\n**condition :**   le clan <nom du clan> ne doit pas être dans alliance.json" +
                        "\n**effet :**            ajoute le clan <nom du clan> a alliance.json", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2 :**   !" + help + " <nom du clan> <leader1> / <leader2> / ... / <leaderN>" + "\n**condition :**   le clan <nom du clan> ne doit pas être dans alliance.json" +
                        "\n**effet :**            ajoute le clan <nom du clan> a alliance.json", false);
                break;
            case "aubucher":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            envoye au bucher l'utilisateur <pseudo>", false);
                break;
            case "ban":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**effet :**         ban du serveur l'utilisateur <pseudo>", false);
                break;
            case "deafen":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            retire le droit d'entendre à l'utilisateur <pseudo>", false);
                break;
            case "getbans":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche les utilisateurs bannis", false);
                break;
            case "kick":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**effet :**         expulse du serveur l'utilisateur <pseudo>", false);
                break;
            case "mute":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            retire le droit de parler à l'utilisateur <pseudo>", false);
                break;
            case "removeclan":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nom du clan>" +  "\n**effet :**            supprime le clan <nom du clan> de alliance.json." +
                        "\n**effet :**            supprime le clan <nom du clan> de alliance.json.", false);
                break;
            case "setgame":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nouveau jeu>" +  "\n**effet :**         change le jeu auquel joue le bot", false);
                break;
            case "tenno":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> doit être nouveau sur le serveur, ou aucun rôle, en particulier le rôle tenno" +
                        "\n**effet :**            contourne la présentation, pour l'accès au reste du discord", false);
                break;
            case "unban":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> doit être banni" +
                        "\n**effet :**            deban du serveur l'utilisateur <pseudo> ", false);
                break;
            case "undeafen" :
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" +  "\n**condition :**   l'utilisateur <pseudo> est assourdi soit par un admin/modo, ou soit par commnande **!deafen**" +
                        "\n**effet :**            redonne le droit d'entendre à l'utilisateur <pseudo>", false);
                break;
            case "unmute":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> est muté soit par un admin/modo, ou soit par commnande **!mute**" +
                        "\n**effet :**            redonne le droit de parler à l'utilisateur <pseudo>", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageHelpModo(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "aubucher":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe :**      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            envoye au bucher l'utilisateur <pseudo>", false);
                break;
            case "deafen":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            retire le droit d'entendre à l'utilisateur <pseudo>", false);
                break;
            case "getbans":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         affiche les utilisateurs bannis", false);
                break;
            case "kick":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**effet :**         expulse du serveur l'utilisateur <pseudo>", false);
                break;
            case "mute":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            retire le droit de parler à l'utilisateur <pseudo>", false);
                break;
            case "tenno":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit être nouveau sur le serveur, ou aucun rôle, en particulier le rôle tenno" +
                        "\n**effet :**            contourne la présentation, pour l'accès au reste du discord", false);
                break;
            case "undeafen" :
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> est assourdi soit par un admin/modo, ou soit par commnande **!deafen**" +
                        "\n**effet :**            redonne le droit d'entendre à l'utilisateur <pseudo>", false);
                break;
            case "unmute":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> est muté soit par un admin/modo, ou soit par commnande **!mute**" +
                        "\n**effet :**            redonne le droit de parler à l'utilisateur <pseudo>", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }

    public static MessageEmbed MessageListeCommande(boolean admin, boolean modo) {
        EmbedBuilder help = new EmbedBuilder();

        help.addField("**Son :**" ,
                "•  !ah" +
                        "\n•  !bucher" +
                        "\n•  !gg" +
                        "\n•  !gogole" +
                        "\n•  !nah" +
                        "\n•  !pigeon" +
                        "\n•  !souffrir" +
                        "\n•  !trump",
                true);
        help.addField("**Sondage :**",
                "*•  !sondage*" +
                        "\n•  !sondage create" +
                        "\n•  !sondage choix" +
                        "\n•  !sondage vote" +
                        "\n•  !sondage affiche" +
                        "\n•  !sondage resultat" +
                        "\n•  !sondage clear",
                true);
        help.addField("**Information :**",
                "•  !alliance" +
                        "\n•  !baro" +
                        "\n•  !clan" +
                        "\n•  !discordDE" +
                        "\n•  !idée ou !idee" +
                        "\n•  !info" +
                        "\n•  !invite" +
                        "\n•  !lead" +
                        "\n•  !progres" +
                        "\n•  !pvp" +
                        "\n•  !pvp hebdo" +
                        "\n•  !raid" +
                        "\n•  !steam" +
                        "\n•  !sortie" +
                        "\n•  !syndicat" +
                        "\n•  !ts3" +
                        "\n•  !up" +
                        "\n•  !updates",
                true);
        help.addField("**Riven :**",
                "*•  !riven*" +
                        "\n•  !riven defi" +
                        "\n•  !riven influence" +
                        "\n•  !riven nom" +
                        "\n•  !riven stat",
                true);

        if (admin) {
            help.addField("**Admin :**",
                    "•  !aubucher" +
                        "\n•  !ban" +
                        "\n•  !deafen" +
                        "\n•  !getbans" +
                        "\n•  !kick" +
                        "\n•  !mute" +
                        "\n•  !setgame" +
                        "\n•  !tenno" +
                        "\n•  !unban" +
                        "\n•  !undeafen" +
                        "\n•  !unmute",
                    true);
        }

        if (modo) {
            help.addField("**Modo :**",
                    "•  !aubucher" +
                        "\n•  !deafen" +
                        "\n•  !getbans" +
                        "\n•  !kick" +
                        "\n•  !mute" +
                        "\n•  !tenno" +
                        "\n•  !undeafen" +
                        "\n•  !unmute",
                    true);
        }

        help.setTitle("Bourreau : Aide Commande", null);
        help.setDescription("liste des commandes\n`!help <nom_de_la_commande>`  pour plus d'information");
        help.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        help.setColor(new Color(70, 70, 255));
        help.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return help.build();
    }

    public static MessageEmbed MessageListeCommandeAdmin() {
        EmbedBuilder help = new EmbedBuilder();

        help.addField("**Admin :**",
                    "•  !aubucher" +
                            "\n•  !ban" +
                            "\n•  !deafen" +
                            "\n•  !getbans" +
                            "\n•  !kick" +
                            "\n•  !mute" +
                            "\n•  !setgame" +
                            "\n•  !tenno" +
                            "\n•  !unban" +
                            "\n•  !undeafen" +
                            "\n•  !unmute",
                    true);

        help.setTitle("Bourreau : Aide Commande", null);
        help.setDescription("liste des commandes\n`!help <nom_de_la_commande>`  pour plus d'information");
        help.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        help.setColor(new Color(70, 70, 255));
        help.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return help.build();
    }

    public static MessageEmbed MessageListeCommandeModo() {
        EmbedBuilder help = new EmbedBuilder();

        help.addField("**Modo :**",
                "•  !aubucher" +
                        "\n•  !deafen" +
                        "\n•  !getbans" +
                        "\n•  !kick" +
                        "\n•  !mute" +
                        "\n•  !tenno" +
                        "\n•  !undeafen" +
                        "\n•  !unmute",
                true);

        help.setTitle("Bourreau : Aide Commande", null);
        help.setDescription("liste des commandes\n`!help <nom_de_la_commande>`  pour plus d'information");
        help.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        help.setColor(new Color(70, 70, 255));
        help.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return help.build();
    }
}
