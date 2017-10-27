package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedSondageHelp {

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

}
