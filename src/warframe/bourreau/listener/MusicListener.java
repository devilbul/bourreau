package warframe.bourreau.listener;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;

import static warframe.bourreau.thread.ThreadSon.setPlayed;
import static warframe.bourreau.thread.ThreadAuBucher.setPlayedBucher;

public class MusicListener implements AudioEventListener {

    @Override
    public void onEvent(AudioEvent event) {
        if (event.getClass().getSimpleName().equals("TrackEndEvent")) {
            setPlayed(false);
            setPlayedBucher(false);
        }
    }
}
