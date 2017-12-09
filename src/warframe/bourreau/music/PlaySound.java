package warframe.bourreau.music;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;

import static warframe.bourreau.Init.*;
import static warframe.bourreau.util.Find.FindUserMention;
import static warframe.bourreau.util.Find.FindUserVC;

public class PlaySound {

    private static void play(MessageReceivedEvent event, String adresse, VoiceChannel channel) {
        try {
            if (channel == null)
                event.getTextChannel().sendMessage("Client non conneté à un salon vocal.").queue();
            else {
                if (!audioManagers.get(event.getGuild().getId()).isConnected())
                    audioManagers.get(event.getGuild().getId()).openAudioConnection(channel);

                System.out.println(adresse);

                managers.get(event.getGuild().getId()).loadTrack(event.getTextChannel(), adresse);
            }
        }
        catch(Exception e) {
            System.out.print("");
        }
    }

    public static void playSound(MessageReceivedEvent event, String file) {
        String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "music" + File.separator + file;
        VoiceChannel channel = FindUserVC(event);

        play(event, adresse, channel);
    }

    public static void playSoundMention(MessageReceivedEvent event) {
        String file = "bucher.mp4";
        String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "music" + File.separator + file;
        VoiceChannel channel = FindUserMention(event);

        play(event, adresse, channel);
    }
}
