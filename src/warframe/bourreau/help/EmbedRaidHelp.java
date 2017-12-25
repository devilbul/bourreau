package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedRaidHelp {

    public static MessageEmbed messageHelpRaid(String help) {
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
}
