package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class FirstConnection {

    public static MessageEmbed messagePremierConnection() {
        EmbedBuilder message = new EmbedBuilder();

        message.addField("Merci d'avoir ajouter mon bot à votre serveur.", "Pour le configurer, tapez ***!config info*** dans un salon sur votre serveur, pour avoir toutes les informations nécessaires.", false);
        message.setTitle("Information Importante :", null);
        message.setDescription("Configuration du bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        return message.build();
    }
}
