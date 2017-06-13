package warframe.bourreau.commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import static warframe.bourreau.Main.DEFAULT_VOLUME;
import static warframe.bourreau.music.PlaySound.playSound;

public class SonCommand extends Command {

    public static void Ah(MessageReceivedEvent event) { playSound(event, "ah.mp3", DEFAULT_VOLUME); }

    public static void Bucher(MessageReceivedEvent event) { playSound(event, "bucher.mp4", DEFAULT_VOLUME); }

    public static void Gg(MessageReceivedEvent event){ playSound(event, "gg.mp3", DEFAULT_VOLUME); }

    public static void Gogole(MessageReceivedEvent event){ playSound(event, "gogole.mp3", DEFAULT_VOLUME); }

    public static void Nah(MessageReceivedEvent event) { playSound(event, "nah.ogg", DEFAULT_VOLUME); }

    public static void Pigeon(MessageReceivedEvent event) { playSound(event, "pigeon.wav", DEFAULT_VOLUME); }

    public static void Stop(MessageReceivedEvent event) {
        AudioManager manager = event.getGuild().getAudioManager();
        manager.closeAudioConnection();
    }

    public static void Souffrir(MessageReceivedEvent event) { playSound(event, "souffrir.wav", DEFAULT_VOLUME); }

    public static int Trump(MessageReceivedEvent event) {
        String fichier;
        int choix;
        choix = ThreadLocalRandom.current().nextInt(0, 39);

        fichier = "alea" + File.separator + choix + ".wav";

        playSound(event, fichier, 0.05f);

        return choix;
    }

    public static void TrumpComp(MessageReceivedEvent event) { playSound(event, "trump.mp3", 0.05f); }

    public static void TrumpComp2(MessageReceivedEvent event) { playSound(event, "trump2.mp3", 0.05f); }

    public static void TrumpComp3( MessageReceivedEvent event) { playSound(event, "trump3.mp3", 0.05f); }
}
