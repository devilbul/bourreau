package warframe.bourreau.util;

import warframe.bourreau.commands.SimpleCommand;

import java.lang.reflect.Method;

import static warframe.bourreau.Bourreau.commands;

public final class CommandMap {

    public static void registerCommands(Object...objects){
        for(Object object : objects) registerCommand(object);
    }

    public static void registerCommand(Object object){
        for(Method method : object.getClass().getDeclaredMethods()){
            if(method.isAnnotationPresent(Command.class)){
                Command command = method.getAnnotation(Command.class);
                commands.put(command.name(), new SimpleCommand());
            }
        }
    }
}
