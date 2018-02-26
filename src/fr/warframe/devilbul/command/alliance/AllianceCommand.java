package fr.warframe.devilbul.command.alliance;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class AllianceCommand extends SimpleCommand {

    @Command(name = "alliance")
    @Help(field = "**syntaxe** :      !alliance\n**effet :**         affiche des informations à propos de l'Alliance", categorie = Categorie.Alliance)
    public static void alliance(MessageReceivedEvent event) {
        try {
            String info = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(info);
            EmbedBuilder alliance = new EmbedBuilder();
            String nomAlliance = allianceJson.getJSONObject("infos").getString("nomAlliance");
            int nbClan = allianceJson.getJSONObject("infos").getInt("nbClan");

            alliance.setTitle("**Alliance :** " + nomAlliance, "http://wfraid.teamfr.net/");
            alliance.setThumbnail(logoUrlAlliance);
            alliance.setDescription("regroupant " + nbClan + " clans");
            alliance.addField("contact : **admin**, **conseil alliance**", "tapez **__!clans__**, pour afficher tous les clans\n" +
                    "tapez **__!leads__**, pour afficher tous les leaders de clans\n" +
                    "tapez **__!lead <nom de clan>__**, pour afficher tous les leaders du clan", false);
            alliance.setColor(new Color(0, 0, 0));
            alliance.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

            event.getTextChannel().sendMessage(alliance.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
