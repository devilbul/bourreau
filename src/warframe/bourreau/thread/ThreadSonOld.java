package warframe.bourreau.thread;/*package warframe.bourreau.thread;

import warframe.bourreau.commands.AdminCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.player;
import static warframe.bourreau.music.PlaySound.playSoundMention;
import static warframe.bourreau.util.Find.FindAdmin;

public class ThreadSonOld extends Thread {
    public static class ThreadAh extends Thread {
        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Ah(cmd.event);
            Tempo.Temporisation(4);
            while (!player.isStopped()) { System.out.print(""); }
            player.stop();
            SonCommand.Stop(cmd.event);
        }

        public ThreadAh(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
    }

    public static class ThreadAuBucher extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            if (FindAdmin(cmd.event, cmd.event.getMember())) {
                playSoundMention(cmd.event);
                Tempo.Temporisation(7000);
                AdminCommand.AuBucher(cmd.event);
                SonCommand.Stop(cmd.event);
            }
        }

        public ThreadAuBucher(CommandParser.CommandContainer cmd) {
            this.cmd = cmd;
        }
    }

    public static class ThreadBucher extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Bucher(cmd.event);
            Tempo.Temporisation(8000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadBucher(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadGg extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Gg(cmd.event);
            Tempo.Temporisation(6000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadGg(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadGogole extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Gogole(cmd.event);
            Tempo.Temporisation(12000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadGogole(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadNah extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Nah(cmd.event);
            Tempo.Temporisation(4000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadNah(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadPigeon extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Pigeon(cmd.event);
            Tempo.Temporisation(12000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadPigeon(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadSouffrir extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.Souffrir(cmd.event);
            Tempo.Temporisation(7000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadSouffrir(CommandParser.CommandContainer cmd){ this.cmd = cmd; }
    }

    public static class ThreadTrump extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.TrumpComp(cmd.event);
            Tempo.Temporisation(177000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadTrump(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
    }

    public static class ThreadTrump2 extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.TrumpComp2(cmd.event);
            Tempo.Temporisation(187000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadTrump2(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
    }

    public static class ThreadTrump3 extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            SonCommand.TrumpComp3(cmd.event);
            Tempo.Temporisation(178000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadTrump3(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
    }

    public static class ThreadTrumpAlea extends Thread {

        CommandParser.CommandContainer cmd;

        public void run() {
            int[] listDuree = {6, 14, 7, 9, 6, 7, 6, 16, 6, 10, 6, 6, 5, 6, 7, 5, 10, 4, 16, 7, 14, 14, 14, 13, 19, 12, 9, 9, 4, 8, 7, 28, 16, 1, 17, 3, 10, 10, 10};
            int choix;
            choix = SonCommand.Trump(cmd.event);
            Tempo.Temporisation((listDuree[choix-1] + 5) * 1000 + 4000);
            SonCommand.Stop(cmd.event);
        }

        public ThreadTrumpAlea(CommandParser.CommandContainer cmd) { this.cmd = cmd; }
    }
}*/

