package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoTextChannel {

    public static MessageEmbed messageInfoTextChannel() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste salons textuels utiles au bot :");
        message.setDescription("toutes les salons textuels dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Liste :",
                "- accueil\n    salon textuel principal, où il y aura des annonces " +
                        "\n    importantes" +
                        "\n- botSpam\n    salon dédié aux commandes de bot" +
                        "\n- raids\n    salon de regroupement pour les raids",
                false);

        return message.build();
    }
}
