package warframe.bourreau.handle;

import warframe.bourreau.commands.SimpleCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Tempo;

import static warframe.bourreau.InitID.queueSon;
import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.thread.ThreadSon.isPlayed;
import static warframe.bourreau.util.Find.FindAdmin;

class HandleSonCommand {

    static void handleSonCommand(CommandParser.CommandContainer cmd){

        if (commands.containsKey(cmd.invoke)) {
            boolean safe = SimpleCommand.called();

            if(!isPlayed()) queueSon.add("first");

            if (safe) {
                switch (cmd.invoke) {
                    case "ah":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"ah.mp3").start();
                        else queueSon.add("ah.mp3");
                        break;
                    case "bucher":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"bucher.mp4").start();
                        else queueSon.add("bucher.mp4");
                        break;
                    case "gg":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"gg.mp3").start();
                        else queueSon.add("gg.mp3");
                        break;
                    case "gogole":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"gogole.mp3").start();
                        else queueSon.add("gogole.mp3");
                        break;
                    case "nah":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"nah.ogg").start();
                        else queueSon.add("nah.ogg");
                        break;
                    case "leave":
                        SonCommand.Leave(cmd.event);
                        break;
                    case "pigeon":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"pigeon.wav").start();
                        else queueSon.add("pigeon.wav");
                        break;
                    case "son":
                        Tempo.Temporisation(4);
                        SonCommand.Son(cmd.event);
                        break;
                    case "souffrir":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd,"souffrir.wav").start();
                        else queueSon.add("souffrir.wav");
                        break;
                    case "trump":
                        Tempo.Temporisation(4);
                        SonCommand.Trump(cmd);
                        break;
                    case "trumpcomp":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd,"trump.mp3").start();
                            else queueSon.add("trump.mp3");
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp2":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd,"trump2.mp3").start();
                            else queueSon.add("trump2.mp3");
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp3":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd,"trump3.mp3").start();
                            else queueSon.add("trump3.mp3");
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
