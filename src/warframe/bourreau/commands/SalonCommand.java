package warframe.bourreau.commands;

import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static net.dv8tion.jda.core.Permission.*;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.util.Find.findAdmin;
import static warframe.bourreau.util.Recup.recupString;

public class SalonCommand extends SimpleCommand {

    @Command(name="createsalonclan", subCommand=false)
    public static void createSalonClan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String configCategory = new String(Files.readAllBytes(Paths.get("res" + File.separator + "config" + File.separator + "configCategory.json")));
                JSONObject configCategoryJson = new JSONObject(configCategory);
                String categoryID = configCategoryJson.getJSONObject("categories").getJSONObject(event.getGuild().getId()).getJSONObject("categories").getJSONObject("clan").getString("idCategory");

                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ")) {
                    String clan = recupString(commande);

                    if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() > 0 && event.getGuild().getVoiceChannelsByName(clan, true).size() > 0)
                        event.getTextChannel().sendMessage("Le clan " + clan + " a déjà un salon vocal et un salon textuel.").queue();
                    else if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() == 0 && event.getGuild().getVoiceChannelsByName(clan, true).size() > 0)
                        event.getTextChannel().sendMessage("Le clan " + clan + " a déjà un salon vocal.").queue();
                    else if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() > 0 && event.getGuild().getVoiceChannelsByName(clan, true).size() == 0)
                        event.getTextChannel().sendMessage("Le clan " + clan + " a déjà un salon textuel.").queue();
                    else {
                        Channel newTC;
                        Channel newVC;
                        Role newRole;
                        Role every = event.getGuild().getPublicRole();

                        if (event.getGuild().getRolesByName(clan, true).size() == 0) {
                            newRole = event.getGuild().getController().createRole().setName(clan).setPermissions(NICKNAME_CHANGE, MESSAGE_WRITE, VIEW_CHANNEL, MESSAGE_TTS,
                                    MESSAGE_ATTACH_FILES, MESSAGE_EMBED_LINKS, MESSAGE_HISTORY, MESSAGE_MENTION_EVERYONE, MESSAGE_EXT_EMOJI, MESSAGE_ADD_REACTION,
                                    VOICE_CONNECT, VOICE_SPEAK, VOICE_USE_VAD).complete();
                        } else
                            newRole = event.getGuild().getRolesByName(clan, true).get(0);

                        if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() == 0) {
                            newTC = event.getGuild().getController().createTextChannel(clan.toLowerCase().replace(" ", "_")).complete();
                            newTC.getManager().setParent(event.getGuild().getCategoryById(categoryID)).queue();
                        } else
                            newTC = event.getGuild().getTextChannelsByName(clan.replace(" ", "_"), true).get(0);

                        if (event.getGuild().getVoiceChannelsByName(clan, true).size() == 0) {
                            newVC = event.getGuild().getController().createVoiceChannel(clan).complete();
                            newVC.getManager().setParent(event.getGuild().getCategoryById(categoryID)).queue();
                        } else
                            newVC = event.getGuild().getVoiceChannelsByName(clan, true).get(0);

                        if (event.getGuild().getRolesByName(clan, true).size() >= 1) {
                            if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() >= 1) {
                                newTC.createPermissionOverride(newRole).setAllow(MESSAGE_READ).complete();
                                newTC.createPermissionOverride(every).setDeny(MESSAGE_READ).complete();
                            }

                            if (event.getGuild().getVoiceChannelsByName(clan, true).size() >= 1) {
                                newVC.createPermissionOverride(newRole).setAllow(VOICE_CONNECT, VOICE_SPEAK, VIEW_CHANNEL).complete();
                                newVC.createPermissionOverride(every).setDeny(VOICE_CONNECT, VOICE_SPEAK, VIEW_CHANNEL).complete();
                            }
                        }

                        event.getTextChannel().sendMessage("Salons créés.").queue();
                    }
                } else
                    event.getTextChannel().sendMessage("Aucun clan saisi.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="deletesalonclan", subCommand=false)
    public static void deleteSalonClan(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                String commande = event.getMessage().getContentDisplay();

                if (commande.contains(" ")) {
                    String clan = recupString(commande);

                    if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() == 0 && event.getGuild().getVoiceChannelsByName(clan, true).size() == 0)
                        event.getTextChannel().sendMessage("Le clan " + clan + " n'a pas de salon vocal et ni de salon textuel.").queue();
                    else {
                        if (event.getGuild().getRolesByName(clan, true).size() == 0)
                            event.getGuild().getRolesByName(clan, true).get(0).delete().complete();

                        if (event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).size() >= 1)
                            event.getGuild().getTextChannelsByName(clan.toLowerCase().replace(" ", "_"), true).get(0).delete().complete();

                        if (event.getGuild().getVoiceChannelsByName(clan, true).size() >= 1)
                            event.getGuild().getVoiceChannelsByName(clan, true).get(0).delete().complete();

                        event.getTextChannel().sendMessage("Salons suppriméés.").queue();
                    }
                } else
                    event.getTextChannel().sendMessage("Aucun clan saisi.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
