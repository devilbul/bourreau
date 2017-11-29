package warframe.bourreau.listener;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import warframe.bourreau.Bourreau;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.Bourreau.*;
import static warframe.bourreau.commands.BasedCommand.Presentation;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.handle.HandleCommand.handleCommand;
import static warframe.bourreau.handle.HandleCommandPrivate.handleCommandPrivate;
import static warframe.bourreau.messsage.MessageOnEvent.MessageDeBienvenue;

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
        if (!event.getAuthor().equals(event.getJDA().getSelfUser()) && !event.getAuthor().isBot() && !event.getMessage().getChannel().toString().startsWith("PC")) {
            if (event.getMember().getRoles().size() == 0) Presentation(event);

            if (event.getMessage().getContent().startsWith("!")) {
                if (event.getMember().getRoles().size() > 0) {
                    event.getTextChannel().addReactionById(event.getMessage().getId(), event.getJDA().getEmoteById(checkID)).queue();
                    if (event.getMessage().getContent().startsWith("!")) {
                        AddReaction(event);
                        handleCommand(Bourreau.parser.parse(event.getMessage().getContent().toLowerCase(), event));
                    }
                }
            }
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor() != event.getJDA().getSelfUser()) {
            if (!(event.getJDA().getGuildById(serveurID).getMember(event.getAuthor()).getRoles().size() == 0)) {
                if (event.getMessage().getContent().startsWith("!")) {
                    AddReactionPrivate(event);
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), event.getJDA().getEmoteById(checkID)).queue();
                    handleCommandPrivate(Bourreau.parserPrivate.parsePrivate(event.getMessage().getContent().toLowerCase(), event));
                }
            }
        }
    }

    private void AddReaction(MessageReceivedEvent event) {
        try {
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
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private void AddReactionPrivate(PrivateMessageReceivedEvent event) {
            switch (event.getAuthor().getId()) {
                case "147022628085825536":  //lukinu_u
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(bombydouID)).queue();
                    break;
                case "180419578554220545":  //devilbul
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(allianceID)).queue();
                    break;
                case "155820408195514369":  //skillof
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(dogeID)).queue();
                    break;
                case "231732702376624129":  //tenshikorosu
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(tenshinoobID)).queue();
                    break;
                case "104327158662438912":  //cgs_knackie
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(saw6ID)).queue();
                    break;
                case "291736390721339414":  //yual
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(soonID)).queue();
                    break;
                case "247284270303805441":  //toxiicanna
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(weedID)).queue();
                    break;
                default:
                    event.getAuthor().openPrivateChannel().complete().addReactionById(event.getMessage().getId(), getJda().getGuildById(serveurID).getEmoteById(bourreauID)).queue();
                    break;
            }
    }
}