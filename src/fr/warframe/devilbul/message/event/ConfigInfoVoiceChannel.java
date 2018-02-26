package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoVoiceChannel {

    public static MessageEmbed messageInfoVoiceChannel() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste salons vocaux du bot :");
        message.setDescription("toutes les salons vocaux dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Liste :",
                "- bucher\n    salon vers lequelle, sera envoyer l'utilisateur, avec la " +
                        "\n    commande aubucher",
                false);

        return message.build();
    }
}
