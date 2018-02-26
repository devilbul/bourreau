package fr.warframe.devilbul.message.event;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;

public class ConfigInfo {

    public static MessageEmbed messageInfoConfiguration() {
        EmbedBuilder message = new EmbedBuilder();

        message.setTitle("Information configuration du bot :");
        message.setDescription("Liste des syntaxes des commandes de configuration, et des options de chacune");
        message.setThumbnail("http://i.imgur.com/gXZfo5H.png");
        message.setColor(new Color(17, 204, 17));
        message.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

        message.addField("Category :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **category add** <typeCategory> <newCategory>" +
                        "\n    - !config **category remove** <typeCategory>" +
                        "\n    - !config **category modify** <typeCategory>" +
                        "\n       <newCategory>" +
                        "\n    - !config **category info**",
                false);
        message.addField("Command :",
                "- options : add / remove / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **command add** <cmd1> ... <cmdN>" +
                        "\n    - !config **command remove** <cmd1> ... <cmdN>" +
                        "\n    - !config **command info**",
                false);
        message.addField("Functionality :",
                "- options : add / remove / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **functionality add** <func1> ... <funcN>" +
                        "\n    - !config **functionality remove** <func1> ... <funcN>" +
                        "\n    - !config **functionality info**",
                false);
        message.addField("Role :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **role add** <typeRole> <newRole>" +
                        "\n    - !config **role remove** <typeRole>" +
                        "\n    - !config **role modify** <typeRole> <newRole>" +
                        "\n    - !config **role info**",
                false);
        message.addField("TextChannel :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **textchannel add** <typeTextChannel>" +
                        "\n       <newTextChannel>" +
                        "\n    - !config **textchannel remove** <typeTextChannel>" +
                        "\n    - !config **textchannel modify** <typeTextChannel>" +
                        "\n       <newTextChannel>" +
                        "\n    - !config **textchannel info**",
                false);
        message.addField("VoiceChannel :",
                "- options : add / remove / modify / info" +
                        "\n- syntaxes :" +
                        "\n    - !config **voicechannel add** <typeVoiceChannel>" +
                        "\n       <newVoiceChannel>" +
                        "\n    - !config **voicechannel remove** <typeVoiceChannel>" +
                        "\n    - !config **voicechannel modify** <typeVoiceChannel>" +
                        "\n       <newVoiceChannel>" +
                        "\n    - !config **voicechannel info**",
                false);
        message.addField("Les <typeXxxxx>, sont listées avec la commande :",
                "!config xxxx info",
                false);

        return message.build();
    }
}
