package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedGeneralHelp {

     public static MessageEmbed MessageHelpGeneral(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "alerts":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :    !" + help + "\n**effet :**         affiche toutes les alertes en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :    !" + help + " interest" +  "\n**effet :**         affiche toutes les alertes en cours avec des récompenses intéressante", false);
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
            case "goals":

                break;
            case "idee":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers un google doc, pour proposer une idée de commande", false);
                break;
            case "info":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien vers site, qui affiche\n                    Alerts" +
                        "\n                    News\n                    Invasions\n                    Darvo Daily Deals\n                    Void Traders" +
                        "\n                    Sorties(WIP)\n                    Void Fissures(WIP)\n                    Market Items\n                    Earth Day/Night Cycle", false);
                break;
            case "invasions":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :    !" + help + "\n**effet :**         affiche toutes les invasions en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :    !" + help + " interest" +  "\n**effet :**         affiche toutes les invasions en cours avec des récompenses intéressante", false);
                break;
            case "invite":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**         donne un lien d'invitation à rejoindre ce serveur", false);
                break;
            case "lead":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :   !" + help + " <nom du clan>" + "\n**effet :**            affiche les leaders du clan <nom du clan>", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :   !" + help + "\n**effet :**            affiche tous les clans, avec leurs leaders", false);
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
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :   !" + help + "\n**effet :**            donne des stats de raid globales de l'auteur", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :   !" + help + " <pseudo>" + "\n**effet :**            donne des stats de raid globales de <pseudo>", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 3** :   !" + help + " detail" + "\n**effet :**            donne des stats de raid détaillées de l'auteur", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 4** :   !" + help + " detail <pseudo>" + "\n**effet :**            donne des stats de raid détaillés de <pseudo>", false);
                break;
            case "regle":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :   !" + help + "\n**effet :**            affiche le reglement du serveur", false);
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
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :   !" + help + "\n**effet :**         affiche les missions syndicales actuelles", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :   !" + help + " <syndicat>" + "\n**parmi :**       SteelMeridian" +
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
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 1** :    !" + help + "\n**effet :**         affiche toutes les missions fissure en cours", false);
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe 2** :    !" + help + " <tiers>" +  "\n**effet :**         affiche toutes les missions fissures du tiers <tiers> en cours", false);
                break;
        }

        helpMessage.setTitle("Bourreau : Aide Commande", null);
        helpMessage.setDescription("information sur la commande");
        helpMessage.setThumbnail("http://icons.iconarchive.com/icons/zakar/shining-z/128/Aide-SZ-icon.png");
        helpMessage.setColor(new Color(70, 70, 255));
        helpMessage.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

        return helpMessage.build();
    }
}
