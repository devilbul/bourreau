package fr.warframe.devilbul.command.admin;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;

public class PingCommand extends SimpleCommand {

    @Command(name="ping")
    @Help(field = "**syntaxe** :      !ping\n**effet :**         affiche le temps de réponse du bot", categorie = Categorie.Admin)
    public static void ping(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                EmbedBuilder ping = new EmbedBuilder();

                ping.setTitle("Pong", null);
                ping.setDescription("responsive time");
                ping.addField(event.getJDA().getPing() + " ms", "", true);
                ping.setColor(new Color(255, 255, 255));
                ping.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                event.getTextChannel().sendMessage(ping.build()).queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
