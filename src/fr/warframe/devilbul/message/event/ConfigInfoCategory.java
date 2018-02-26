package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoCategory {

    public static MessageEmbed messageInfoCategory() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste categories utiles au bot :");
        message.setDescription("toutes les categories dont le bot a besoin");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Liste :",
                "- clan\n    categorie dans laquelle sera ajouté les salons par les commandes.",
                false);
        message.addField("Pour ajouter", "", false);

        return message.build();
    }
}
