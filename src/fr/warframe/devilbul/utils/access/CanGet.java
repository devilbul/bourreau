package fr.warframe.devilbul.utils.access;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findAdminSupreme;
import static fr.warframe.devilbul.utils.Find.findModo;

public class CanGet {

    public static boolean canGetHelpDetail(MessageReceivedEvent event, String categorie) {
        switch (categorie) {
            case "Admin":
                return findAdmin(event, event.getMember());
            case "Modo":
                return findModo(event, event.getMember());
            case "Supreme":
                return findAdminSupreme(event.getAuthor().getId());
            default:
                return true;
        }
    }
}
