package warframe.bourreau.parser;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class CommandParser {

    public CommandContainer parse(String rw, MessageReceivedEvent event){
        ArrayList<String> split = new ArrayList<>();
        String beheaded = rw.replaceFirst("!","");
        String[] splitBeheaded = beheaded.split(" ");
        Collections.addAll(split, splitBeheaded);
        String invoke = split.get(0);
        String[] args = new String[split.size() - 1];
        split.subList(1,split.size()).toArray(args);


        return new CommandContainer(rw, beheaded, splitBeheaded, invoke, args, event);
    }

    public class CommandContainer {

        final String raw;
        final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        final String[] args;
        public final MessageReceivedEvent event;

        public CommandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent event){
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.event = event;
        }
    }
}
