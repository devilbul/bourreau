package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.manager;
import static warframe.bourreau.Main.DEFAULT_VOLUME;
import static warframe.bourreau.music.PlaySound.playSound;

public class ThreadSon extends Thread {
    private CommandParser.CommandContainer cmd;
    private String sound;
    private static boolean isPlayed = true;

    public void run() {
        setPlayed(true);
        playSound(cmd.event, sound, DEFAULT_VOLUME);
        Tempo.Temporisation(4);
        while (isPlayed) { System.out.print(""); }
        manager.getPlayer(cmd.event.getGuild()).getAudioPlayer().stopTrack();
        SonCommand.Leave(cmd.event);
    }

    public ThreadSon(CommandParser.CommandContainer cmd, String sound) { this.cmd = cmd; this.sound = sound; }

    public static void setPlayed(boolean played) { isPlayed = played; }
}