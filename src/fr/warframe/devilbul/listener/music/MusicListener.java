package fr.warframe.devilbul.listener.music;

import com.sedmelluq.discord.lavaplayer.player.event.AudioEvent;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventListener;

import static fr.warframe.devilbul.thread.ThreadSon.setPlayed;

public class MusicListener implements AudioEventListener {

    @Override
    public void onEvent(AudioEvent event) {
        if (event.getClass().getSimpleName().equals("TrackEndEvent")) {
            setPlayed(false);
        }
    }
}
