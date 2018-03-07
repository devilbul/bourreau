package fr.warframe.devilbul.handle;

import fr.warframe.devilbul.parser.CommandParser;
import fr.warframe.devilbul.utils.music.WaitingSound;

import static fr.warframe.devilbul.Init.queueSon;
import static fr.warframe.devilbul.functionality.AntiSpam.isCensured;
import static fr.warframe.devilbul.utils.Find.getMusicCommand;
import static fr.warframe.devilbul.utils.Find.isMusicCommand;

public class HandleMusicCommand {

    public static void handleMusicCommand(CommandParser.CommandContainer cmd) {
        if (isMusicCommand(cmd.invoke)) {
            if (isCensured(cmd.event)) {
                cmd.event.getTextChannel().sendMessage("Censuré").queue();
                return;
            }

            String musicFile = getMusicCommand(cmd.invoke);

            queueSon.add(new WaitingSound(cmd.event, musicFile));
        }
    }
}
