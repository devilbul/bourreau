package warframe.bourreau.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;

public class MusicPlayer {

    private final AudioPlayer audioPlayer;
    private final AudioListener listener;
    private final Guild guild;

    MusicPlayer(AudioPlayer audioPlayer, Guild guild) {
        this.audioPlayer = audioPlayer;
        this.guild = guild;
        listener = new AudioListener(this);
        audioPlayer.addListener(listener);
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public Guild getGuild() {
        return guild;
    }

    public AudioListener getListener() {
        return listener;
    }

    AudioHandler getAudioHandler() {
        return new AudioHandler(audioPlayer);
    }

    synchronized void playTrack(AudioTrack track){
        listener.queue(track);
    }

    public synchronized void skipTrack() {
        listener.nextTrack();
    }
}
