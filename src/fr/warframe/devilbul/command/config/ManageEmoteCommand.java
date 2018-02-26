package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findEmoteGuildList;
import static fr.warframe.devilbul.utils.Find.findGuildJDAList;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageEmoteCommand extends SimpleCommand {

    @SubCommand(name = "emote", commande = "config")
    @Help(field = "", categorie = Categorie.Supreme)
    public static void manageEmote(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String configEmote = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
                JSONObject configEmoteJson = new JSONObject(configEmote);
                FileWriter fileEmote = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configEmote.json");
                Guild guild;
                Emote emote;
                User user;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            if (findGuildJDAList(event.getJDA().getGuilds(), commande.split(" ")[1])) {
                                guild = event.getJDA().getGuildById(commande.split(" ")[1]);

                                if (findEmoteGuildList(guild.getEmotes(), commande.split(" ")[2])) {
                                    emote = guild.getEmoteById(commande.split(" ")[2]);

                                    if (event.getMessage().getMentionedUsers().size() > 0) {
                                        user = event.getMessage().getMentionedUsers().get(0);

                                        if (!findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                            configEmoteJson.getJSONObject("emotes").put(user.getId(), new JSONObject().put("idEmote", emote.getId()).put("nameEmote", emote.getName()).put("nameUser", user.getName()).put("idServer", guild.getId()).put("nameServer", guild.getName()));
                                            event.getTextChannel().sendMessage("Emote : **" + emote.getName() + " " + emote.getAsMention() + "** ajoutée pour l'utilisateur : **" + user.getAsMention() + "**.").queue();
                                        } else
                                            event.getTextChannel().sendMessage("Il y a déjà une emote associé à cet utilisateur").queue();
                                    } else
                                        event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Le serveur " + guild.getName() + " n'a pas cet emote (" + commande.split(" ")[2] + ").").queue();
                            } else
                                event.getTextChannel().sendMessage("Le bot n'est pas sur ce serveur (" + commande.split(" ")[1] + ").").queue();
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune emote saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun serveur saisie.").queue();

                        break;
                    case "remove":
                        if (event.getMessage().getMentionedUsers().size() > 0) {
                            user = event.getMessage().getMentionedUsers().get(0);

                            if (findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                configEmoteJson.getJSONObject("emotes").remove(user.getId());
                                event.getTextChannel().sendMessage("L'utilisateur : " + user.getAsMention() + "n'a plus d'emote associé.").queue();
                            } else
                                event.getTextChannel().sendMessage("Il n'y a déjà plus emote associé à cet utilisateur").queue();
                        } else
                            event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            if (findGuildJDAList(event.getJDA().getGuilds(), commande.split(" ")[1])) {
                                guild = event.getJDA().getGuildById(commande.split(" ")[1]);

                                if (findEmoteGuildList(guild.getEmotes(), commande.split(" ")[2])) {
                                    emote = guild.getEmoteById(commande.split(" ")[2]);

                                    if (event.getMessage().getMentionedUsers().size() > 0) {
                                        user = event.getMessage().getMentionedUsers().get(0);

                                        if (findValueObjectList(configEmoteJson.getJSONObject("emotes").names().toList(), user.getId())) {
                                            configEmoteJson.getJSONObject("emotes").getJSONObject(user.getId()).put("nameEmote", emote.getName()).put("nameServer", guild.getName()).put("idServer", guild.getId()).put("idEmote", emote.getId());
                                            event.getTextChannel().sendMessage("Emote : **" + emote.getName() + " " + emote.getAsMention() + "** ajoutée pour l'utilisateur : **" + user.getAsMention() + "**.").queue();
                                        } else
                                            event.getTextChannel().sendMessage("Il y a déjà une emote associé à cet utilisateur").queue();
                                    } else
                                        event.getTextChannel().sendMessage("Aucun utilisateur mentionné.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Le serveur " + guild.getName() + " n'a pas cet emote (" + commande.split(" ")[2] + ").").queue();
                            } else
                                event.getTextChannel().sendMessage("Le bot n'est pas sur ce serveur (" + commande.split(" ")[1] + ").").queue();
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucune emote saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun serveur saisie.").queue();

                        break;

                    case "getall":
                        if (commande.contains(" "))
                            guild = event.getJDA().getGuildById(commande.split(" ")[1]);
                        else
                            guild = event.getGuild();

                        for (Emote emote1 : guild.getEmotes())
                            event.getTextChannel().sendMessage(emote1.getName() + " :\n    id : " + emote1.getId() + "\n    emote : " + emote1.getAsMention()).queue();

                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "modify", "getall"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                fileEmote.write(configEmoteJson.toString(3));
                fileEmote.flush();
                fileEmote.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
