package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.ConfigInfoTextChannel.messageInfoTextChannel;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageTextChannelCommand extends SimpleCommand {

    @SubCommand(name = "textchannel", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageTextChannel(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String textChannelJson;
                String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
                JSONObject configTextChannelJson = new JSONObject(configTextChannel);
                FileWriter fileTextChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configtextChannel.json");
                TextChannel textChannel;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            textChannelJson = commande.split(" ")[1];
                            textChannel = event.getMessage().getMentionedChannels().get(0);

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson) && textChannel != null) {
                                if (configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).getString("idTextChannel").isEmpty()) {
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", textChannel.getName());
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", textChannel.getId());
                                    event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** ajouté (avec " + textChannel.getAsMention() + ").").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon textuel, **" + textChannelJson + "**, est déjà configuré.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + textChannelJson + "** n'est pas un salon textuel utile.").queue();
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon textuel saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            textChannelJson = commande.split(" ")[1];

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson)) {
                                if (!configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).getString("idTextChannel").isEmpty()) {
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", "");
                                    configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", "");
                                    event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** supprimé.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon textuel, **" + textChannelJson + "**, est vide.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            textChannelJson = commande.split(" ")[1];
                            textChannel = event.getMessage().getMentionedChannels().get(0);

                            if (findValueObjectList(configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").names().toList(), textChannelJson) && textChannel != null) {
                                configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("nameTextChannel", textChannel.getName());
                                configTextChannelJson.getJSONObject("textChannels").getJSONObject(event.getGuild().getId()).getJSONObject("textChannels").getJSONObject(textChannelJson).put("idTextChannel", textChannel.getId());
                                event.getTextChannel().sendMessage("Salon textuel **" + textChannelJson + "** modifiée (avec " + textChannel.getAsMention() + ").").queue();
                            }
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon textuel saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon textuel saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoTextChannel()).queue();
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "modify", "info"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                fileTextChannel.write(configTextChannelJson.toString(3));
                fileTextChannel.flush();
                fileTextChannel.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
