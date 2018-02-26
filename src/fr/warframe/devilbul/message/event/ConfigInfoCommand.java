package fr.warframe.devilbul.message.event;

import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Bourreau.helpList;
import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfoCommand {

    public static MessageEmbed messageInfoCommand() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Liste commandes du bot :");
        message.setDescription("toutes les commandes acceptées par le bot");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Pour ajouter ou supprimer des commandes :", "!config command add <commande1> <commande2> ... <commandeN>\n!config command remove <commande1> <commande2> ... <commandeN>\"", false);

        for (String key : helpList.keySet()) {
            if (!helpList.get(key).isEmpty() && !key.equals(Categorie.Supreme.toString())) {
                StringBuilder list = new StringBuilder();

                for (String value : helpList.get(key))
                    if (!value.contains(" "))
                        list.append("\n•  !").append(value);

                message.addField("**" + key + " :**", list.toString(), true);
            }
        }

        message.addField("Les commandes soulignées ont besoin de configurer d'autres choses :", "categories, rôles, salon textuel, salon vocal.", false);

        return message.build();
    }
}
