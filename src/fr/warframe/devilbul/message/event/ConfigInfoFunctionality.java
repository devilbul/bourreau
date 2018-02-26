package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Bourreau.functionalities;
import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoFunctionality {

    public static MessageEmbed messageInfoFunctionality() {
        EmbedBuilder message = new EmbedBuilder();
        StringBuilder field = new StringBuilder();

        message.setTitle("Liste fonctionnalités du bot :");
        message.setDescription("toutes les fonctionnalités du bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        for (String key : functionalities.keySet())
            field.append("- __").append(key).append("__\n    ").append(functionalities.get(key));

        message.addField("Pour ajouter ou supprimer des commandes :", "!config functionality add <fonctionnalite1> <fonctionnalite2> ... <fonctionnaliteN>\n!config functionality remove <fonctionnalite1> <fonctionnalite2> ... <fonctionnaliteN>\"", false);
        message.addField("Liste :", field.toString(), false);
        message.addField("Les fonctionnalités soulignées ont besoin de configurer d'autres choses :", "categories, rôles, salon textuel, salon vocal.", false);

        return message.build();
    }
}
