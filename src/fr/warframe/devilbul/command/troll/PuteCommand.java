package fr.warframe.devilbul.command.troll;

import fr.warframe.devilbul.Bourreau;
import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;

public class PuteCommand extends SimpleCommand {

    @Command(name = "pute")
    @Help(field = "Test pour voir ce que ça fait !", categorie = Categorie.Troll)
    public static void hype(MessageReceivedEvent event) {
        try {
            MessageBuilder pute = new MessageBuilder();

            switch (new Random().nextInt(10)) {
                case 1:
                case 4:
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getMemberById("231732702376624129").getAsMention());
                    pute.append("\n**__tenshipute__** : <3 <3 <3\n");
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606757712723969"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606757712723969"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606757712723969"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606757712723969"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606757712723969"));
                    break;
                case 7:
                    pute.append("**__Yual__** : <3 <3 <3\n");
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606755737206784"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606755737206784"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606755737206784"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606755737206784"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("387606755737206784"));
                    break;
                case 2:
                    pute.append("**__SkiLLoF__** : <3 <3 <3\n");
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("298508665932218379"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("298508665932218379"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("298508665932218379"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("298508665932218379"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("298508665932218379"));
                    break;
                case 3:
                    pute.append("**__-Tsumiki-__** : <3 <3 <3\n");
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    break;
                case 9:
                    pute.append("**__.Heldras.__** : J'aime manger du Caribou\n");
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    break;
                case 0:
                case 5:
                case 6:
                case 8:
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    pute.append(Bourreau.jda.getGuildById("298503533777387530").getEmoteById("417803198766776330"));
                    break;
            }

            event.getTextChannel().sendMessage(pute.build()).queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
