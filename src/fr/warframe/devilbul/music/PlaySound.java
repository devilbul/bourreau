package fr.warframe.devilbul.music;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;

import static fr.warframe.devilbul.Init.audioManagers;
import static fr.warframe.devilbul.Init.managers;
import static fr.warframe.devilbul.utils.Find.findUserMention;
import static fr.warframe.devilbul.utils.Find.findUserVC;

public class PlaySound {

    private static void play(MessageReceivedEvent event, String adresse, VoiceChannel channel) {
        try {
            if (channel == null)
                event.getTextChannel().sendMessage("Client non conneté à un salon vocal.").queue();
            else {
                if (!audioManagers.get(event.getGuild().getId()).isConnected())
                    audioManagers.get(event.getGuild().getId()).openAudioConnection(channel);

                managers.get(event.getGuild().getId()).loadTrack(event.getTextChannel(), adresse);
            }
        }
        catch(Exception e) {
            System.out.print("");
        }
    }

    public static void playSound(MessageReceivedEvent event, String file) {
        String adresse = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "music" + File.separator + file;
        VoiceChannel channel = findUserVC(event);
        play(event, adresse, channel);
    }

    public static void playSoundMention(MessageReceivedEvent event, String file) {
        String adresse = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "music" + File.separator + file;
        VoiceChannel channel = findUserMention(event);
        play(event, adresse, channel);
    }
}
