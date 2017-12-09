package warframe.bourreau.handle;

import warframe.bourreau.commands.SimpleCommand;
import warframe.bourreau.commands.SonCommand;
import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Tempo;
import warframe.bourreau.util.WaitingSound;

import static warframe.bourreau.Init.queueSon;
import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.thread.ThreadSon.isPlayed;
import static warframe.bourreau.util.Find.FindAdmin;

class HandleSonCommand {

    static void handleSonCommand(CommandParser.CommandContainer cmd){

        if (commands.containsKey(cmd.invoke)) {
            boolean safe = SimpleCommand.called();

            if (!isPlayed())
                    queueSon.add(new WaitingSound(cmd.event, "first"));

            if (safe) {
                switch (cmd.invoke) {
                    case "ah":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"ah.mp3").start();
                        else queueSon.add(new WaitingSound(cmd.event, "ah.mp3"));
                        break;
                    case "bucher":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"bucher.mp4").start();
                        else queueSon.add(new WaitingSound(cmd.event,"bucher.mp4"));
                        break;
                    case "gg":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"gg.mp3").start();
                        else queueSon.add(new WaitingSound(cmd.event,"gg.mp3"));
                        break;
                    case "gogole":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"gogole.mp3").start();
                        else queueSon.add(new WaitingSound(cmd.event,"gogole.mp3"));
                        break;
                    case "nah":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"nah.ogg").start();
                        else queueSon.add(new WaitingSound(cmd.event,"nah.ogg"));
                        break;
                    case "leave":
                        SonCommand.Leave(cmd.event);
                        break;
                    case "pigeon":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"pigeon.wav").start();
                        else queueSon.add(new WaitingSound(cmd.event,"pigeon.wav"));
                        break;
                    case "son":
                        Tempo.Temporisation(4);
                        SonCommand.Son(cmd.event);
                        break;
                    case "souffrir":
                        Tempo.Temporisation(4);
                        if (!isPlayed()) new ThreadSon(cmd.event,"souffrir.wav").start();
                        else queueSon.add(new WaitingSound(cmd.event,"souffrir.wav"));
                        break;
                    case "trump":
                        Tempo.Temporisation(4);
                        SonCommand.Trump(cmd.event);
                        break;
                    case "trumpcomp":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd.event,"trump.mp3").start();
                            else queueSon.add(new WaitingSound(cmd.event,"trump.mp3"));
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp2":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd.event,"trump2.mp3").start();
                            else queueSon.add(new WaitingSound(cmd.event,"trump2.mp3"));
                        }
                        else cmd.event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
                        break;
                    case "trumpcomp3":
                        if (FindAdmin(cmd.event, cmd.event.getMember())) {
                            Tempo.Temporisation(4);
                            if (!isPlayed()) new ThreadSon(cmd.event,"trump3.mp3").start();
                            else queueSon.add(new WaitingSound(cmd.event,"trump3.mp3"));
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
