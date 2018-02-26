package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedAdminList {

    public static MessageEmbed messageListeCommandeAdmin() {
        EmbedBuilder help = new EmbedBuilder();

        help.addField("**Admin :**",
                "•  !aubucher" +
                        "\n•  !ban" +
                        "\n•  !deafen" +
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
}
