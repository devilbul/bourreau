package fr.warframe.devilbul.command.config;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.annotations.sub.command.SubCommand;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.message.event.ConfigInfoVoiceChannel.messageInfoVoiceChannel;
import static fr.warframe.devilbul.utils.Find.findValueObjectList;
import static fr.warframe.devilbul.utils.Find.findVoiceChannel;
import static fr.warframe.devilbul.utils.Recup.recupString;
import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class ManageVoiceChannelCommand extends SimpleCommand {

    @SubCommand(name = "voicechannel", commande = "config")
    @Help(field = "", categorie = Categorie.Admin)
    public static void manageVoiceChannel(MessageReceivedEvent event) {
        try {
            if (recupString(event.getMessage().getContentDisplay()).contains(" ")) {
                String commande = recupString(recupString(event.getMessage().getContentDisplay()));
                String voiceChannelJson;
                String configVoiceChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configVoiceChannel.json")));
                JSONObject configVoiceChannelJson = new JSONObject(configVoiceChannel);
                FileWriter fileVoiceChannel = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "config" + File.separator + "configVoiceChannel.json");
                VoiceChannel voiceChannel;

                switch (commande.split(" ")[0]) {
                    case "add":
                        if (commande.split(" ").length > 2) {
                            voiceChannelJson = commande.split(" ")[1];
                            voiceChannel = findVoiceChannel(event.getGuild(), commande.split(" : ")[1]);

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson) && voiceChannel != null) {
                                System.out.println(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).toString());
                                if (configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).getString("idVoiceChannel").isEmpty()) {
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", voiceChannel.getName());
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", voiceChannel.getId());
                                    event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** ajouté (avec " + voiceChannel.getName() + ").").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon vocal, **" + voiceChannelJson + "**, est déjà configuré.").queue();
                            } else
                                event.getTextChannel().sendMessage("**" + voiceChannelJson + "** n'est pas un salon vocal utile.").queue();
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon vocal saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "remove":
                        if (commande.split(" ").length > 1) {
                            voiceChannelJson = commande.split(" ")[1];

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson)) {
                                if (!configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).getString("idVoiceChannel").isEmpty()) {
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", "");
                                    configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", "");
                                    event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** supprimé.").queue();
                                } else
                                    event.getTextChannel().sendMessage("Ce salon vocal, **" + voiceChannelJson + "**, est vide.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "modify":
                        if (commande.split(" ").length > 2) {
                            voiceChannelJson = commande.split(" ")[1];
                            voiceChannel = findVoiceChannel(event.getGuild(), commande.split(" : ")[1]);

                            if (findValueObjectList(configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").names().toList(), voiceChannelJson) && voiceChannel != null) {
                                configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("nameVoiceChannel", voiceChannel.getName());
                                configVoiceChannelJson.getJSONObject("voiceChannels").getJSONObject(event.getGuild().getId()).getJSONObject("voiceChannels").getJSONObject(voiceChannelJson).put("idVoiceChannel", voiceChannel.getId());
                                event.getTextChannel().sendMessage("Salon vocal **" + voiceChannelJson + "** modifiée (avec " + voiceChannel.getName() + ").").queue();
                            }
                        } else if (commande.split(" ").length == 2)
                            event.getTextChannel().sendMessage("Aucun salon vocal saisie.").queue();
                        else
                            event.getTextChannel().sendMessage("Aucun type de salon vocal saisie.").queue();

                        break;
                    case "info":
                        event.getTextChannel().sendMessage(messageInfoVoiceChannel()).queue();
                        break;
                    default:
                        MessageBuilder message = new MessageBuilder();

                        event.getTextChannel().sendMessage(compareCommande(commande, new String[]{"add", "remove", "info"})).queue();
                        event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                        message.append("You know nothing, ");
                        message.append(event.getAuthor());

                        event.getTextChannel().sendMessage(message.build()).queue();
                        break;
                }

                fileVoiceChannel.write(configVoiceChannelJson.toString(3));
                fileVoiceChannel.flush();
                fileVoiceChannel.close();
            } else
                event.getTextChannel().sendMessage("Aucune action saisie.").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
