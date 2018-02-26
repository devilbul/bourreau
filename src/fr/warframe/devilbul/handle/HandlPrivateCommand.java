package fr.warframe.devilbul.handle;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.parser.CommandParserPrivate;
import fr.warframe.devilbul.utils.annotations.command.Command;
import net.dv8tion.jda.core.MessageBuilder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static fr.warframe.devilbul.utils.recognition.Levenshtein.compareCommande;

public class HandlPrivateCommand {

    public static void handlePrivateCommand(CommandParserPrivate.CommandContainerPrivate cmd) {
        try {
            MessageBuilder message = new MessageBuilder();
            List<String> list = new ArrayList<>();
            List<Object> commandList = new ArrayList<>();
            commandList.add(new SimpleCommand());

            for (Object object : commandList)
                for (Method method : object.getClass().getDeclaredMethods())
                    if (method.isAnnotationPresent(Command.class)) {
                        Command command = method.getAnnotation(Command.class);

                        if (command.isPrivate()) {
                            list.add(command.name());

                            if (command.name().equals(cmd.invoke)) {
                                method.invoke(null, cmd.event);
                                return;
                            }
                        }
                    }

            if (!list.isEmpty())
                cmd.event.getAuthor().openPrivateChannel().complete().sendMessage(compareCommande(cmd.invoke, list.toArray())).queue();

            cmd.event.getAuthor().openPrivateChannel().complete().sendMessage("Commande inconnue. !help pour lister les commandes (sur un salon textuel ou en message privé).").queue();
            message.append("You know nothing, ");
            message.append(cmd.event.getAuthor());

            cmd.event.getAuthor().openPrivateChannel().complete().sendMessage(message.build()).queue();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
