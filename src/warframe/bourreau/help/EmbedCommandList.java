package warframe.bourreau.help;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class EmbedCommandList {

    public static MessageEmbed MessageListeCommande(boolean admin, boolean modo) {
        EmbedBuilder help = new EmbedBuilder();

        /*help.addField("**Son :**" ,
                "•  !ah" +
                        "\n•  !bucher" +
                        "\n•  !gg" +
                        "\n•  !gogole" +
                        "\n•  !nah" +
                        "\n•  !pigeon" +
                        "\n•  !souffrir" +
                        "\n•  !trump",
                true);*/
        help.addField("**Information :**",
                "•  !alerts" +
                        "\n•  !alliance" +
                        "\n•  !baro" +
                        "\n•  !clan" +
                        "\n•  !discordDE" +
                        "\n•  !goals" +
                        "\n•  !idee" +
                        "\n•  !info" +
                        "\n•  !invasions" +
                        "\n•  !invite" +
                        "\n•  !lead" +
                        "\n•  !progres" +
                        "\n•  !pvp" +
                        "\n•  !pvp hebdo" +
                        "\n•  !raid" +
                        "\n•  !regle" +
                        "\n•  !site" +
                        "\n•  !steam" +
                        "\n•  !sortie" +
                        "\n•  !syndicat" +
                        "\n•  !ts3" +
                        "\n•  !up" +
                        "\n•  !updates" +
                        "\n•  !void",
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
        help.addField("**Riven :**",
                "*•  !riven*" +
                        "\n•  !riven calcul" +
                        "\n•  !riven defi" +
                        "\n•  !riven influence" +
                        "\n•  !riven info" +
                        "\n•  !riven nom" +
                        "\n•  !riven stat",
                true);

        if (admin) {
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
        }

        if (modo) {
            help.addField("**Modo :**",
                    "•  !aubucher" +
                        "\n•  !deafen" +
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
}
