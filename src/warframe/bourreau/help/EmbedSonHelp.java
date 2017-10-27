package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedSonHelp {

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
}
