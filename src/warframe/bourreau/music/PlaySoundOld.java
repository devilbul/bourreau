package warframe.bourreau.music;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import net.dv8tion.jda.player.source.AudioSource;
import net.dv8tion.jda.player.source.LocalSource;

import java.io.File;

import static warframe.bourreau.InitID.player;
import static warframe.bourreau.InitID.audioManager;
import static warframe.bourreau.util.Find.FindUserMention;
import static warframe.bourreau.util.Find.FindUserVC;

public class PlaySoundOld {

    public static void playSound(MessageReceivedEvent event, String file, float volume) {
        String adresse = System.getProperty("user.dir") + File.separator + "music" + File.separator + file;
        VoiceChannel channel = FindUserVC(event);

        player.setVolume(volume);

        if (channel == null)
            event.getTextChannel().sendMessage("Client non conneté à un salon vocal.").queue();
        else {
            if (!audioManager.isConnected())
                audioManager.openAudioConnection(channel);

            AudioSource source = new LocalSource(new File(adresse));
            player.getAudioQueue().add(source);

            Thread thread = new Thread(player::play);
            thread.start();
        }
    }

    public static void playSoundMention(MessageReceivedEvent event) {
        String adresse = System.getProperty("user.dir") + File.separator + "music" + File.separator + "bucher.mp4";
        VoiceChannel channel = FindUserMention(event);
        AudioManager manager = event.getGuild().getAudioManager();
        MusicPlayerOld player;

        if (manager.getSendingHandler() == null) {
            player = new MusicPlayerOld();
            player.setVolume(warframe.bourreau.Main.DEFAULT_VOLUME);
            manager.setSendingHandler(player);
        }
        else
            player = (MusicPlayerOld) manager.getSendingHandler();

        if (channel == null)
            event.getTextChannel().sendMessage("Client non conneté à un salon vocal.").queue();
        else {
            if (!manager.isConnected())
                manager.openAudioConnection(channel);

            AudioSource source = new LocalSource(new File(adresse));
            player.getAudioQueue().add(source);

            Thread thread = new Thread(player::play);
            thread.start();
        }
    }
}
