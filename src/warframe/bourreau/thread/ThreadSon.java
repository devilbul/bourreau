package warframe.bourreau.thread;

import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.player;
import static warframe.bourreau.Main.DEFAULT_VOLUME;
import static warframe.bourreau.music.PlaySoundOld.playSound;

public class ThreadSon extends Thread {
        private CommandParser.CommandContainer cmd;
        private String sound;

        public void run() {
            playSound(cmd.event, sound, DEFAULT_VOLUME);
            Tempo.Temporisation(4);
            while (!player.isStopped()) { System.out.print(""); }
            player.stop();
            SonCommand.Leave(cmd.event);
        }

        public ThreadSon(CommandParser.CommandContainer cmd, String sound) { this.cmd = cmd; this.sound = sound; }
}

