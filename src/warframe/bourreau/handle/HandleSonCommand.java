package warframe.bourreau.handle;

import warframe.bourreau.commands.Command;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.player;
import static warframe.bourreau.Main.commands;
import static warframe.bourreau.util.Find.FindAdmin;

class HandleSonCommand {

    static void handleSonCommand(CommandParser.CommandContainer cmd){

        if (commands.containsKey(cmd.invoke)) {
            boolean safe = Command.called();

            if (player.isPlaying()) player.stop();

            if (safe) {
                switch (cmd.invoke) {
                    case "ah":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"ah.mp3").start();
                        break;
                    case "bucher":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"bucher.mp4").start();
                        break;
                    case "gg":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"gg.mp3").start();
                        break;
                    case "gogole":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"gogole.mp3").start();
                        break;
                    case "nah":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"nah.ogg").start();
                        break;
                    case "leave":
                        SonCommand.Leave(cmd.event);
                        break;
                    case "pigeon":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"pigeon.wav").start();
                        break;
                    case "son":
                        //player.stop();
                        Tempo.Temporisation(4);
                        SonCommand.Son(cmd.event);
                        break;
                    case "souffrir":
                        //player.stop();
                        Tempo.Temporisation(4);
                        new ThreadSon(cmd,"souffrir.wav").start();
                        break;
                    case "trump":
                        //player.stop();
                        Tempo.Temporisation(4);
                        SonCommand.Trump(cmd);
                        break;
                    case "trumpcomp":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            //player.stop();
                            Tempo.Temporisation(4);
                            new ThreadSon(cmd,"trump.mp3").start();
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp2":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            //player.stop();
                            Tempo.Temporisation(4);
                            new ThreadSon(cmd,"trump2.mp3").start();
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp3":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            //player.stop();
                            Tempo.Temporisation(4);
                            new ThreadSon(cmd,"trump3.mp3").start();
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    default:
                        cmd.event.getTextChannel().sendMessage("NOPE").queue();
                        break;
                }
            }
        }
    }
}
