package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Bourreau.botVersion;
import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class About {

    public static MessageEmbed messageAbout() {
        EmbedBuilder about = new EmbedBuilder();

        about.setTitle("A propos de Bourreau :", null);
        about.setThumbnail("https://i.imgur.com/gXZfo5H.png");
        about.addField("Version actuel du bot :", botVersion, false);
        about.addField("Créateur :", "devilbul", false);
        about.addField("Contact pour tout problème :", "devilbul\nSkiLLoF", false);
        about.addField("Alliance : French Connection", "https://wfraid.teamfr.net/", false);
        about.setColor(new Color(70, 70, 255));
        about.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        return about.build();
    }
}
