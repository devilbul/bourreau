package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.SubCommand;
import warframe.bourreau.util.WaitingSound;

import static warframe.bourreau.Bourreau.subCommands;
import static warframe.bourreau.Init.audioManagers;
import static warframe.bourreau.Init.queueSon;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.thread.ThreadSon.isPlayed;
import static warframe.bourreau.thread.ThreadSon.setPlayed;
import static warframe.bourreau.util.Find.findAdmin;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class SonCommand extends SimpleCommand {

    @Command(name="acdc", subCommand=false)
    public static void adcd(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (!isPlayed()) new ThreadSon(event, "acdc.mp3").start();
                else queueSon.add(new WaitingSound(event, "acdc.mp3"));
            } else event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="ah", subCommand=false)
    public static void ah(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "ah.mp3").start();
            else queueSon.add(new WaitingSound(event, "ah.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="bucher", subCommand=false)
    public static void bucher(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "bucher.mp4").start();
            else queueSon.add(new WaitingSound(event, "bucher.mp4"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="gg", subCommand=false)
    public static void gg(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "gg.mp3").start();
            else queueSon.add(new WaitingSound(event, "gg.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="gogole", subCommand=false)
    public static void gogole(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "gogole.mp3").start();
            else queueSon.add(new WaitingSound(event, "gogole.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="nah", subCommand=false)
    public static void nah(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "nah.ogg").start();
            else queueSon.add(new WaitingSound(event, "nah.ogg"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="pigeon", subCommand=false)
    public static void pigeon(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "pigeon.wav").start();
            else queueSon.add(new WaitingSound(event, "pigeon.wav"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="souffrir", subCommand=false)
    public static void souffrir(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event, "souffrir.wav").start();
            else queueSon.add(new WaitingSound(event, "souffrir.wav"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="trump", subCommand=false)
    public static void trump(MessageReceivedEvent event) {
        try {
            String fichier;
            int choix;
            choix = ThreadLocalRandom.current().nextInt(0, 39);

            fichier = "alea" + File.separator + choix + ".wav";

            if (!isPlayed()) new ThreadSon(event, fichier).start();
            else queueSon.add(new WaitingSound(event, fichier));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="trumpcomp", subCommand=false)
    public static void trumpcomp(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event,"trump.mp3").start();
            else queueSon.add(new WaitingSound(event,"trump.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="trumpcomp2", subCommand=false)
    public static void trumpcomp2(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event,"trump2.mp3").start();
            else queueSon.add(new WaitingSound(event,"trump2.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="trumpcomp3", subCommand=false)
    public static void trumpcomp3(MessageReceivedEvent event) {
        try {
            if (!isPlayed()) new ThreadSon(event,"trump3.mp3").start();
            else queueSon.add(new WaitingSound(event,"trump3.mp3"));
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="leave", subCommand=false)
    public static void leave(MessageReceivedEvent event) {
        try {
            audioManagers.get(event.getGuild().getId()).closeAudioConnection();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @Command(name="son", subCommand=true)
    public static void son(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember())) {
                if (event.getMessage().getContentDisplay().contains(" ")) {
                    String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "list":
                            list(event);
                            break;
                        case "skip":
                            skip(event);
                            break;
                        case "stop":
                            stop(event);
                            break;
                        default:
                            MessageBuilder message = new MessageBuilder();

                            event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("son").toArray())).queue();
                            event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                            message.append("You know nothing, ");
                            message.append(event.getAuthor());

                            event.getTextChannel().sendMessage(message.build()).queue();
                            break;
                    }
                } else {
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Aucune action de son saisie").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getTextChannel().sendMessage(message.build()).queue();
                }
            }
            else event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch(Exception e){
                afficheErreur(event, e);
                saveErreur(event, e);
        }
    }

    @SubCommand(name="list")
    private static void list(MessageReceivedEvent event) {
        try {
            MessageBuilder queue = new MessageBuilder();

            queue.append("Playlist :");

            if (queueSon.size() > 0)
                for (WaitingSound aQueueSon : queueSon)
                    queue.append("\n   • ").append(aQueueSon.getCommandeSon());
            else
                queue.append("\n   pas de son en attente.");

            event.getTextChannel().sendMessage(queue.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="skip")
    private static void skip(MessageReceivedEvent event) {
        try {
            setPlayed(false);
            event.getChannel().sendMessage("Skipped the current song.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="stop")
    private static void stop(MessageReceivedEvent event) {
        try {
            queueSon = new ArrayList<>();
            setPlayed(false);
            event.getChannel().sendMessage("Stop.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
