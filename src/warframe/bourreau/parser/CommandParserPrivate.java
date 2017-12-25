package warframe.bourreau.parser;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class CommandParserPrivate {

    public CommandContainerPrivate parsePrivate(String rw, PrivateMessageReceivedEvent event){
        ArrayList<String> split = new ArrayList<>();
        String beheaded = rw.replaceFirst("!","");
        String[] splitBeheaded = beheaded.split(" ");
        Collections.addAll(split, splitBeheaded);
        String invoke = split.get(0);
        String[] args = new String[split.size() - 1];
        split.subList(1,split.size()).toArray(args);


        return new CommandContainerPrivate(rw, beheaded, splitBeheaded, invoke, args, event);
    }

    public class CommandContainerPrivate {

        final String raw;
        final String beheaded;
        final String[] splitBeheaded;
        public final String invoke;
        final String[] args;
        public final PrivateMessageReceivedEvent event;

        CommandContainerPrivate(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, PrivateMessageReceivedEvent event){
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }
    }
}
