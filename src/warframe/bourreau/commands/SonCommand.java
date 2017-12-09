package warframe.bourreau.commands;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import warframe.bourreau.thread.ThreadSon;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.WaitingSound;

import static warframe.bourreau.Init.audioManagers;
import static warframe.bourreau.Init.queueSon;
import static warframe.bourreau.erreur.erreurGestion.afficheErreur;
import static warframe.bourreau.erreur.erreurGestion.saveErreur;
import static warframe.bourreau.thread.ThreadSon.isPlayed;
import static warframe.bourreau.thread.ThreadSon.setPlayed;
import static warframe.bourreau.util.Find.FindAdmin;
import static warframe.bourreau.util.Levenshtein.CompareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class SonCommand extends SimpleCommand {

    @Command(name="leave")
    public static void Leave(MessageReceivedEvent event) {
        try {
            audioManagers.get(event.getGuild().getId()).closeAudioConnection();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    public static void Trump(MessageReceivedEvent event) {
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

    @Command(name="son")
    public static void Son(MessageReceivedEvent event) {
        try {
            if (FindAdmin(event, event.getMember())) {
                if (event.getMessage().getContent().contains(" ")) {
                    String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "list":
                            List(event);
                            break;
                        case "skip":
                            Skip(event);
                            break;
                        case "stop":
                            Stop(event);
                            break;
                        default:
                            MessageBuilder message = new MessageBuilder();
                            String[] commandeRiven = {"list", "nowplaying", "skip", "reset", "stop", "pause"};

                            event.getTextChannel().sendMessage(CompareCommande(commande, commandeRiven)).queue();
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

    private static void List(MessageReceivedEvent event) {
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

    private static void Skip(MessageReceivedEvent event) {
        try {
            setPlayed(false);
            event.getChannel().sendMessage("Skipped the current song.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    private static void Stop(MessageReceivedEvent event) {
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
