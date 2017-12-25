package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedModoHelp {

    public static MessageEmbed messageHelpModo(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "aubucher":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            envoye au bucher l'utilisateur <pseudo>", false);
                break;
            case "deafen":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " @<pseudo>" + "\n**condition :**   l'utilisateur <pseudo> doit connecté dans un salon vocal" +
                        "\n**effet :**            retire le droit d'entendre à l'utilisateur <pseudo>", false);
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
}
