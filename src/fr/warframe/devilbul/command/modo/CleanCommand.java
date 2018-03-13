package fr.warframe.devilbul.command.modo;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findModo;
import static fr.warframe.devilbul.utils.Recup.recupString;

public class CleanCommand extends SimpleCommand {

    @Command(name = "clean")
    @Help(field = "**syntaxe 1** :      !clean all\n**effet :**           efface tous les messages du salon dans lequel la commande a été faite\n(100 messages max, tant que le message date de moins de 2 semaines)" +
            "\n\n**syntaxe 2** :      !clean <nb_message>\n**effet :**           efface <nb_message> messages du salon dans lequel la commande a été faite\n(100 messages max, tant que le message date de moins de 2 semaines)" +
            "\n\n**syntaxe 3** :      !clean @<pseudo >all\n**effet :**           efface tous les messages de l'utilisateur <pseudo> du salon dans lequel la commande a été faite\n(100 messages max, tant que le message date de moins de 2 semaines)" +
            "\n\n**syntaxe 4** :      !clean @<pseudo> <nb_message>\n**effet :**           efface <nb_message> messages de l'utilisateur <pseudo> du salon dans lequel la commande a été faite\n(100 messages max, tant que le message date de moins de 2 semaines)", categorie = Categorie.Modo)
    public static void clean(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) {
                    if (event.getMessage().getMentionedUsers().size() > 0) {
                        if (event.getMessage().getContentDisplay().contains(" ")) {
                            if (recupString(recupString(event.getMessage().getContentDisplay())).startsWith("all")) {
                                event.getTextChannel().sendMessage("Clean du salon en cours").complete().delete().completeAfter(1, TimeUnit.SECONDS);
                                event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrievePast(100).complete()
                                        .stream().filter(message -> message.getAuthor().equals(event.getMessage().getMentionedUsers().get(0))).collect(Collectors.toList())
                                        .stream().filter(message -> message.getCreationTime().isAfter(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList())).queue();
                                event.getMessage().delete().queue();
                            } else {
                                int nbMessage = 0;

                                try {
                                    nbMessage = Integer.valueOf(recupString(recupString(event.getMessage().getContentDisplay())));
                                } catch (NumberFormatException e) {
                                    event.getTextChannel().sendMessage("Nombre saisi invalide.").queue();
                                    return;
                                }

                                if (nbMessage != 0 && nbMessage < 100 && nbMessage > 0) {
                                    event.getTextChannel().sendMessage("Clean du salon en cours").complete().delete().completeAfter(1, TimeUnit.SECONDS);
                                    event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrievePast(nbMessage).complete()
                                            .stream().filter(message -> message.getAuthor().equals(event.getMessage().getMentionedUsers().get(0))).collect(Collectors.toList())
                                            .stream().filter(message -> message.getCreationTime().isAfter(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList())).queue();
                                    event.getMessage().delete().queue();
                                } else
                                    event.getTextChannel().sendMessage("Valeur invalide, nombre < 100.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucune action saisie.").queue();

                    } else {
                        if (event.getMessage().getContentDisplay().contains(" ")) {
                            if (recupString(event.getMessage().getContentDisplay()).startsWith("all")) {
                                event.getTextChannel().sendMessage("Clean du salon en cours").complete().delete().completeAfter(1, TimeUnit.SECONDS);
                                event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrievePast(100).complete()
                                        .stream().filter(message -> message.getCreationTime().isAfter(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList())).queue();
                                event.getMessage().delete().queue();
                            } else {
                                int nbMessage = 0;

                                try {
                                    nbMessage = Integer.valueOf(recupString(event.getMessage().getContentDisplay()));
                                } catch (NumberFormatException e) {
                                    event.getTextChannel().sendMessage("Nombre saisi invalide.").queue();
                                    return;
                                }

                                if (nbMessage != 0 && nbMessage < 100 && nbMessage > 0) {
                                    event.getTextChannel().sendMessage("Clean du salon en cours").complete().delete().completeAfter(1, TimeUnit.SECONDS);
                                    event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrievePast(nbMessage).complete()
                                            .stream().filter(message -> message.getCreationTime().isAfter(OffsetDateTime.now().minusWeeks(2))).collect(Collectors.toList())).queue();
                                    event.getMessage().delete().queue();
                                } else
                                    event.getTextChannel().sendMessage("Valeur invalide, nombre < 100.").queue();
                            }
                        } else
                            event.getTextChannel().sendMessage("Aucune action saisie.").queue();
                    }
                } else
                    event.getTextChannel().sendMessage("Le bot n'a pas ce droit.").queue();
            } else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
