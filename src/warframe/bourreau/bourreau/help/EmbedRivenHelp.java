package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedRivenHelp {

    public static MessageEmbed messageHelpRiven(String help) {
        EmbedBuilder helpMessage = new EmbedBuilder();

        switch (help) {
            case "riven":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**Coming Soon**", false);

                break;
            case "riven calcul":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**Coming Soon**", false);
                break;
            case "riven defi":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + "\n**effet :**           affiche tous les défis possibles", false);
                break;
            case "riven info":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <catégorie>" + "\n**effet :**           donne toutes les informations sur la catégorie <catégorie>", false);
                break;
            case "riven influence":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nom de l'objet>" + "\n**effet :**           donne l'influence de l'objet <nom de l'objet>", false);
                break;
            case "riven nom":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <nom du riven>" + "\n**effet :**           donne les statistiques par rapport au nom du riven <nom de riven>", false);
                break;
            case "riven stat":
                helpMessage.addField("__Aide pour__ :   " + help, "**syntaxe** :      !" + help + " <catégorie>" + "\n**effet :**           donne toutes les statistique pour la catégorie <catégorie>", false);
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
