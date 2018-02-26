package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoRole {

    public static MessageEmbed messageInfoRole() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste rôles utiles au bot :");
        message.setDescription("toutes les rôles dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Liste :",
                "- leader clan\n    rôle pour la recrutement " +
                        "\n- tenno\n    rôle minimal pour utiliser le bot" +
                        "\n- admin\n    rôle qui administrer le serveur" +
                        "\n- modo\n    rôle qui permet d'avoir accès à une partie des " +
                        "\n    commandes admins, afin de pouvoir modérer" +
                        "\n- bucher\n    rôle ajouté, par la commande !aubucher",
                false);

        return message.build();
    }
}
