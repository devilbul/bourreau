package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.concurrent.ThreadLocalRandom;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.util.Find.FindUserVC;

public class TrollCommand extends Command {

    public static void FDP(MessageReceivedEvent event) {

    }

    public static void Pute(MessageReceivedEvent event) {
        MessageBuilder pute = new MessageBuilder();
        int choix =  ThreadLocalRandom.current().nextInt(0, 2);

        switch (choix) {
            case 0:
                pute.append("'Yual': :3\n");
                pute.append(event.getJDA().getEmoteById(soonID));
                pute.append(event.getJDA().getEmoteById(soonID));
                pute.append(event.getJDA().getEmoteById(soonID));
                break;
            case 1:
                pute.append("lukinu_u': :3\n");
                pute.append(event.getJDA().getEmoteById(bombydouID));
                pute.append(event.getJDA().getEmoteById(bombydouID));
                pute.append(event.getJDA().getEmoteById(bombydouID));
                break;
            default:
                break;
        }

        event.getTextChannel().sendMessage(pute.build()).queue();
    }

    public static void Rekt(MessageReceivedEvent event) {}

    public static void RIP(MessageReceivedEvent event) { event.getTextChannel().sendMessage ("#JeSuisBourreau !").queue(); }

    public static void Segpta(MessageReceivedEvent event) {
        MessageBuilder segpa = new MessageBuilder();
        int choix =  ThreadLocalRandom.current().nextInt(0, 3);

        switch (choix) {
            case 0:
                segpa.append("'SkiLLoF' : Segpa\n");
                segpa.append(event.getJDA().getEmoteById(dogeID));
                segpa.append(event.getJDA().getEmoteById(dogeID));
                segpa.append(event.getJDA().getEmoteById(dogeID));
                break;
            case 1:
                segpa.append("'Moumous' : Segpa\n");
                segpa.append(event.getJDA().getEmoteById(trollfaceID));
                segpa.append(event.getJDA().getEmoteById(trollfaceID));
                segpa.append(event.getJDA().getEmoteById(trollfaceID));
                break;
            case 2:
                segpa.append("'Clarkkeint' : Segpa\n");
                segpa.append(event.getJDA().getEmoteById(FDPID));
                segpa.append(event.getJDA().getEmoteById(FDPID));
                segpa.append(event.getJDA().getEmoteById(FDPID));
                break;
            default:
                break;
        }

        event.getTextChannel().sendMessage(segpa.build()).queue();
    }

    public static void Tg(MessageReceivedEvent event) {
        MessageBuilder message = new MessageBuilder();

        message.append("TG toi même, ");
        message.append(event.getAuthor());

        event.getTextChannel().sendMessage(message.build()).queue();

        if (FindUserVC(event) != null && !event.getAuthor().equals(event.getGuild().getOwner().getUser()))
            event.getGuild().getController().setMute(event.getMember(), true).submit();
    }
}
