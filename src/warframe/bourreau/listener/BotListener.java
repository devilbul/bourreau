package warframe.bourreau.listener;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import warframe.bourreau.Main;

import java.util.concurrent.TimeUnit;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.commands.Command.Presentation;
import static warframe.bourreau.handle.HandleCommand.handleCommand;
import static warframe.bourreau.handle.HandleCommandPrivate.handleCommandPrivate;
import static warframe.bourreau.util.Find.FindRole;
import static warframe.bourreau.util.MessageOnEvent.MessageDeBienvenue;
import static warframe.bourreau.util.MessagePrive.MessageReclamationAdmin;
import static warframe.bourreau.util.MessagePrive.MessageRecrutement;

public class BotListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) { if (!event.getMember().getUser().isBot()) MessageDeBienvenue(event); }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        if (!event.getMember().getUser().isBot()) {
            MessageBuilder message = new MessageBuilder();
            message.append("Au revoir ");
            message.append(event.getMember().getUser());

            event.getJDA().getTextChannelById(accueilID).sendMessage(message.build()).queue();
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if ((event.getAuthor() != event.getJDA().getSelfUser()) && !event.getAuthor().isBot() && !event.getMessage().getChannel().toString().startsWith("PC")) {
            event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getEmoteById(checkID)).queue();

            if (event.getMember().getRoles().size() == 0) Presentation(event);
            else if (event.getMember().getRoles().size() > 0){
                if (event.getTextChannel().getId().equals(candidatureID) && !FindRole(event, event.getGuild().getRoleById(membreAllianceID))) {
                    event.getTextChannel().sendMessage("les leaders de clans ont été prévenue de votre candidature !").queue();
                    AddReaction(event);
                    MessageRecrutement(event);

                }
                else if (event.getTextChannel().getId().equals(reclamationID) && !FindRole(event, event.getGuild().getRoleById(adminID))) {
                    AddReaction(event);
                    MessageReclamationAdmin(event);
                    event.getTextChannel().sendMessage("les administrateurs ont été prévenue de votre réclamation !").complete().delete().completeAfter(30, TimeUnit.SECONDS);
                    event.getMessage().delete().complete();
                }
                else {
                    if (event.getMessage().getContent().startsWith("!")) {
                        AddReaction(event);
                        handleCommand(Main.parser.parse(event.getMessage().getContent().toLowerCase(), event));
                    }
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor() != event.getJDA().getSelfUser()) {
            if (!(event.getJDA().getGuilds().get(0).getMember(event.getAuthor()).getRoles().size() == 0)) {
                event.getAuthor().openPrivateChannel().queue();
                event.getAuthor().getPrivateChannel().addReactionById(event.getMessage().getId(), event.getJDA().getEmoteById(checkID)).queue();
                handleCommandPrivate(Main.parserPrivate.parsePrivate(event.getMessage().getContent().toLowerCase(), event));
                event.getAuthor().getPrivateChannel().close();
            }
        }
    }

    private void AddReaction(MessageReceivedEvent event) {
        switch (event.getAuthor().getId()) {
            case "147022628085825536":  //lukinu_u
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(bombydouID)).queue();
                break;
            case "180419578554220545":  //devilbul
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(allianceID)).queue();
                break;
            case "155820408195514369":  //skillof
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(dogeID)).queue();
                break;
            case "231732702376624129":  //tenshikorosu
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(tenshinoobID)).queue();
                break;
            case "104327158662438912":  //cgs_knackie
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(saw6ID)).queue();
                break;
            case "291736390721339414":  //yual
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(soonID)).queue();
                break;
            case "247284270303805441":  //toxiicanna
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(weedID)).queue();
                break;
            default:
                event.getTextChannel().addReactionById(event.getMessage().getId(), event.getGuild().getEmoteById(bourreauID)).queue();
                break;
        }
    }
}