package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.File;
import java.util.concurrent.ThreadLocalRandom;

import warframe.bourreau.parser.CommandParser;
import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Command;

import static warframe.bourreau.InitID.queueSon;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.thread.ThreadSon.isPlayed;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class SonCommand extends SimpleCommand {

    @Command(name="leave")
    public static void Leave(MessageReceivedEvent event) {
        try {
            AudioManager manager = event.getGuild().getAudioManager();
            manager.closeAudioConnection();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Trump(CommandParser.CommandContainer cmd) {
        try {
            String fichier;
            int choix;
            choix = ThreadLocalRandom.current().nextInt(0, 39);

            fichier = "alea" + File.separator + choix + ".wav";

            if (!isPlayed()) new ThreadSon(cmd, fichier).start();
            else queueSon.add(fichier);
        }
        catch (Exception e) {
            afficheErreur(cmd.event, e);
            saveErreur(cmd.event, e);
        }
    }

    @Command(name="son")
    public static void Son(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                if (event.getMessage().getContent().contains(" ")) {
                    String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        /*case "list":
                            List(event);
                            break;
                        case "nowplaying":
                            NowPlaying(event);
                            break;
                        case "skip":
                            Skip(event);
                            break;
                        case "reset":
                            Reset(event);
                            break;
                        case "stop":
                            Stop(event);
                            break;
                        case "pause":
                            Pause(event);
                            break;*/
                        default:
                            MessageBuilder message = new MessageBuilder();
                            String[] commandeRiven = {"list", "nowplaying", "skip", "reset", "stop", "pause"};

                            event.getTextChannel().sendMessage(CompareCommande(commande, commandeRiven)).queue();
                            event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

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

    /*private static void List(MessageReceivedEvent event) {
        try {
            List<AudioSource> queue = player.getAudioQueue();
            boolean error = false;
            int totalSeconds = 0;
            MessageBuilder builder = new MessageBuilder();

            if (queue.isEmpty()) {
                event.getChannel().sendMessage("The queue is currently empty!").queue();
                return;
            }

            builder.append("__Current Queue.  Entries: ").append(String.valueOf(queue.size())).append("__\n");

            for (int i = 0; i < queue.size() && i < 10; i++) {
                AudioInfo info = queue.get(i).getInfo();
                //builder.appendString("**(" + (i + 1) + ")** ");

                if (info == null)
                    builder.append("*Could not get info for this song.*");
                else {
                    AudioTimestamp duration = info.getDuration();
                    builder.append("`[");
                    if (duration == null)
                        builder.append("N/A");
                    else
                        builder.append(duration.getTimestamp());

                    builder.append("]` ").append(info.getTitle()).append("\n");
                }
            }

            for (AudioSource source : queue) {
                AudioInfo info = source.getInfo();

                if (info == null || info.getDuration() == null) {
                    error = true;
                    continue;
                }
                totalSeconds += info.getDuration().getTotalSeconds();
            }

            builder.append("\nTotal Queue Time Length: ").append(AudioTimestamp.fromSeconds(totalSeconds).getTimestamp());
            if (error)
                builder.append("`An error occured calculating total time. Might not be completely valid.");
            event.getChannel().sendMessage(builder.build()).queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }*/

    /*private static void NowPlaying(MessageReceivedEvent event) {
        try {
            if (player.isPlaying()) {
                AudioTimestamp currentTime = player.getCurrentTimestamp();
                AudioInfo info = player.getCurrentAudioSource().getInfo();
                if (info.getError() == null)
                    event.getChannel().sendMessage("**Playing:** " + info.getTitle() + "\n" + "**Time:**    [" + currentTime.getTimestamp() + " / " + info.getDuration().getTimestamp() + "]").queue();
                else
                    event.getChannel().sendMessage("**Playing:** Info Error. Known source: " + player.getCurrentAudioSource().getSource() + "\n" + "**Time:**    [" + currentTime.getTimestamp() + " / (N/A)]").queue();
            } else
                event.getChannel().sendMessage("The player is not currently playing anything!").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Skip(MessageReceivedEvent event) {
        try {
            player.skipToNext();
            event.getChannel().sendMessage("Skipped the current song.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }*/

    /*private static void Reset(MessageReceivedEvent event) {
        try {
            player.stop();
            player = new MusicPlayerOld();
            player.setVolume(DEFAULT_VOLUME);
            audioManager.setSendingHandler(player);
            event.getChannel().sendMessage("Music player has been completely reset.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Stop(MessageReceivedEvent event) {
        try {
            player.stop();
            event.getChannel().sendMessage("Playback has been completely stopped.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Pause(MessageReceivedEvent event) {
        try {
            player.pause();
            event.getChannel().sendMessage("Playback has been paused.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }*/
}
