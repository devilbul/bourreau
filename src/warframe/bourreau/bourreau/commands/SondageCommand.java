package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import warframe.bourreau.util.Command;
import warframe.bourreau.util.SubCommand;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.Bourreau.subCommands;
import static warframe.bourreau.erreur.erreurGestion.*;
import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Levenshtein.compareCommande;
import static warframe.bourreau.util.Recup.recupString;

public class SondageCommand extends SimpleCommand {

    @Command(name="sondage", subCommand=true)
    public static void sondage(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getMessage().getContentDisplay().contains(" ")) {
                    String adresseSondage = System.getProperty("user.dir") + File.separator + "res" + File.separator + "sondage" + File.separator + "sondage.json";
                    String adresseVote = System.getProperty("user.dir") + File.separator + "res" + File.separator + "sondage" + File.separator + "vote.json";
                    String rawCommande = recupString(event.getMessage().getContentDisplay().toLowerCase());
                    String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

                    switch (commande) {
                        case "affiche":
                            afficheSondage(event);
                            break;
                        case "reponses":
                            if (reponsesSondage(event, adresseSondage, adresseVote))
                                afficheSondage(event);
                            break;
                        case "clear":
                            clearSondage(event, adresseSondage, adresseVote);
                            break;
                        case "create":
                            createSondage(event, adresseSondage, adresseVote);
                            break;
                        case "resultat":
                            resultatSondage(event);
                            break;
                        case "vote":
                            voteSondage(event, adresseVote);
                            break;
                        default:
                            MessageBuilder message = new MessageBuilder();

                            event.getTextChannel().sendMessage(compareCommande(commande, subCommands.get("sondage").toArray())).queue();
                            event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes.").queue();

                            message.append("You know nothing, ");
                            message.append(event.getAuthor());

                            event.getTextChannel().sendMessage(message.build()).queue();
                            break;
                    }
                }
                else {
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Aucune action de sondage saisie").queue();

                    message.append("You know nothing, ");
                    message.append(event.getAuthor());

                    event.getTextChannel().sendMessage(message.build()).queue();
                }
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="affiche")
    private static void afficheSondage(MessageReceivedEvent event) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                    EmbedBuilder sondageDisplay = new EmbedBuilder();
                    JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                    StringBuilder reponses = new StringBuilder();

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses.append(reponsesJson.getString("reponse" + (i + 1))).append("\n");

                    sondageDisplay.setTitle("Sondage en cours :", null);
                    sondageDisplay.setDescription("fait par : " + sondageJson.getJSONObject(event.getGuild().getId()).getString("createur"));
                    sondageDisplay.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    sondageDisplay.addField(sondageJson.getJSONObject(event.getGuild().getId()).getString("question"), reponses.toString(), false);
                    sondageDisplay.addField("Pour voter :", "!sondage vote <choix>\navec <choix> doit être parmis la liste ci-dessus", false);
                    sondageDisplay.setColor(new Color(147, 117, 0));
                    sondageDisplay.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                    event.getTextChannel().sendMessage(sondageDisplay.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("aucunes réponses n'a encore été spéficié.").queue();
            }
            else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="clear")
    private static void clearSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
            JSONObject sondageJson = new JSONObject(sondage);
            JSONObject voteJson = new JSONObject(vote);
            FileWriter fileSondage = new FileWriter(adresseSondage);
            FileWriter fileVote = new FileWriter(adresseVote);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                sondageJson.remove(event.getGuild().getId());
                voteJson.remove(event.getGuild().getId());

                fileSondage.write(sondageJson.toString(3));
                fileSondage.flush();
                fileSondage.close();
                fileVote.write(voteJson.toString(3));
                fileVote.flush();
                fileVote.close();

                event.getTextChannel().sendMessage("sondage supprimé.").queue();
            }
            else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="create")
    private static void createSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (!sondageJson.names().toString().contains(event.getGuild().getId())) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if (commande.contains(" ")) {
                    String question = recupString(commande);
                    String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                    JSONObject sondageJSON = new JSONObject();
                    JSONObject voteJSON = new JSONObject(vote);
                    FileWriter fileSondage = new FileWriter(adresseSondage);
                    FileWriter fileVote = new FileWriter(adresseVote);

                    sondageJSON.put("createur", event.getAuthor().getName());
                    sondageJSON.put("createurID", event.getAuthor().getId());
                    sondageJSON.put("question", question);
                    sondageJson.put(event.getGuild().getId(), sondageJSON);
                    voteJSON.put(event.getGuild().getId(), new JSONObject().put("nbVote", 0));

                    event.getTextChannel().sendMessage("sondage créé").queue();
                    event.getTextChannel().sendMessage("tapez **__!sondage reponses__**, suivi des diverses réponses souhaitées.").queue();

                    fileSondage.write(sondageJson.toString(3));
                    fileSondage.flush();
                    fileSondage.close();
                    fileVote.write(voteJSON.toString(3));
                    fileVote.flush();
                    fileVote.close();
                }
                else
                    event.getTextChannel().sendMessage("pas de question saisie.").queue();
            }
            else
                event.getTextChannel().sendMessage("il y a déjà un sondage en cours.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="reponses")
    private static boolean reponsesSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                String commande = recupString(event.getMessage().getContentDisplay());

                if( commande.contains(" ")) {
                    String rawReponses = recupString(commande);
                    String[] reponses = rawReponses.split(" / ");

                    if (reponses.length > 1) {
                        String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                        JSONObject voteJson = new JSONObject(vote);

                        if (!sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                            JSONObject reponsesJson = new JSONObject();
                            FileWriter fileSondage = new FileWriter(adresseSondage);
                            FileWriter fileVote = new FileWriter(adresseVote);

                            for (int i = 0; i < reponses.length; i++) {
                                reponsesJson.put("reponse"+(i+1), reponses[i]);
                                voteJson.getJSONObject(event.getGuild().getId()).put("nbReponse"+(i+1), 0);
                            }

                            reponsesJson.put("nb", reponses.length);

                            sondageJson.getJSONObject(event.getGuild().getId()).put("reponses", reponsesJson);

                            event.getTextChannel().sendMessage("les réponses sont enregistrées.").queue();

                            fileSondage.write(sondageJson.toString(3));
                            fileSondage.flush();
                            fileSondage.close();
                            fileVote.write(voteJson.toString(3));
                            fileVote.flush();
                            fileVote.close();

                            return true;
                        }
                        else {
                            event.getTextChannel().sendMessage("il y a déjà des réponses enregistrés.").queue();
                            return false;
                        }
                    }
                    else {
                        event.getTextChannel().sendMessage("il n'y a qu'une seule réponse saisie, il en faut au moins deux.").queue();
                        return false;
                    }
                }
                else {
                    event.getTextChannel().sendMessage("aucunes réponse saisie.").queue();
                    return false;
                }
            }
            else {
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
                return false;
            }
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
            return false;
        }
    }

    @SubCommand(name="resultat")
    private static void resultatSondage(MessageReceivedEvent event) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.toString().contains("reponses")) {
                    String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                    JSONObject voteJson = new JSONObject(vote);
                    JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                    EmbedBuilder resultat = new EmbedBuilder();
                    StringBuilder reponses = new StringBuilder();
                    int nbReponse = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses").getInt("nb");
                    double[] stats = new double[nbReponse];

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses.append(reponsesJson.getString("reponse" + (i + 1))).append("\n");

                    resultat.setTitle("Résultat sondage en cours :", null);
                    resultat.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    resultat.setDescription("fait par : " + sondageJson.getJSONObject(event.getGuild().getId()).getString("createur"));
                    resultat.addField(sondageJson.getJSONObject(event.getGuild().getId()).getString("question"), reponses.toString(), false);
                    resultat.addField("__résultat :__ ", "", false);

                    for(int i=0; i<sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses").getInt("nb"); i++) {
                        resultat.addField("nombre de vote pour **__" + reponsesJson.getString("reponse" + (i + 1)) + " :__**",
                                String.valueOf(voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + (i + 1))), true);
                    }

                    resultat.addField("__statistique :__", "", false);

                    for (int i = 0; i < nbReponse; i++) {
                        stats[i] = (voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + (i + 1)) / (voteJson.getJSONObject(event.getGuild().getId()).getInt("nbVote") * 1.0)) * 100;
                        resultat.addField("vote pour **__" + reponsesJson.getString("reponse" + (i + 1)) + " :__**",
                                String.valueOf(stats[i]) + "%", true);
                    }

                    resultat.addField("", "", false);
                    resultat.setColor(new Color(147, 117, 0));
                    resultat.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), "http://i.imgur.com/BUkD1OV.png");

                    event.getTextChannel().sendMessage(resultat.build()).queue();
                }
                else
                    event.getTextChannel().sendMessage("aucunes réponses n'a encore été spéficié.").queue();
            }
            else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }

    @SubCommand(name="vote")
    private static void voteSondage(MessageReceivedEvent event, String adresseVote) {
        try {
            String sondage = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "sondage.json")));
            JSONObject sondageJson = new JSONObject(sondage);

            if (sondageJson.names().toString().contains(event.getGuild().getId())) {
                if (sondageJson.getJSONObject(event.getGuild().getId()).toString().contains("reponses")) {
                    String commande = recupString(event.getMessage().getContentDisplay().toLowerCase());

                    if (commande.contains(" ")) {
                        String vote = new String(Files.readAllBytes(Paths.get("res" + File.separator + "sondage" + File.separator + "vote.json")));
                        JSONObject reponsesJson = sondageJson.getJSONObject(event.getGuild().getId()).getJSONObject("reponses");
                        JSONObject voteJson = new JSONObject(vote);
                        JSONObject voteFaitJson = new JSONObject();
                        String[] reponsesList = new String[reponsesJson.getInt("nb")];
                        String reponse = recupString(commande).toLowerCase();
                        Member auteur = event.getMember();

                        for (int i=0; i<reponsesJson.getInt("nb"); i++)
                            reponsesList[i] = reponsesJson.getString("reponse"+(i+1));

                        if (!findVotant(auteur, voteJson.getJSONObject(event.getGuild().getId()))) {
                            if (findList(reponsesList, reponse)) {
                                FileWriter file = new FileWriter(adresseVote);
                                int nbVote = voteJson.getJSONObject(event.getGuild().getId()).getInt("nbVote") + 1;
                                int reponseFaite = findReponse(reponsesList, reponse) + 1;

                                voteFaitJson.put("auteur", event.getAuthor().getName());
                                voteFaitJson.put("auteurID", event.getAuthor().getId());
                                voteFaitJson.put("reponses", reponse);

                                voteJson.getJSONObject(event.getGuild().getId()).put("nbReponse" + reponseFaite, voteJson.getJSONObject(event.getGuild().getId()).getInt("nbReponse" + reponseFaite) + 1);

                                voteJson.getJSONObject(event.getGuild().getId()).put("nbVote", nbVote);
                                voteJson.getJSONObject(event.getGuild().getId()).put("vote" + nbVote, voteFaitJson);

                                event.getTextChannel().sendMessage("a voté.").queue();

                                file.write(voteJson.toString(3));
                                file.flush();
                                file.close();
                            }
                            else
                                event.getTextChannel().sendMessage("ton vote n'est pas dans les réponses possibles.").queue();
                        }
                        else
                            event.getTextChannel().sendMessage("vous avez déjà voté.").queue();
                    }
                    else
                        event.getTextChannel().sendMessage("aucune réponses n'a été apportée.").queue();
                }
                else
                    event.getTextChannel().sendMessage("aucunes réponses n'a encore été spéficié.").queue();
            }
            else
                event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
        }
        catch (Exception e) {
            afficheErreur(event, e);
            saveErreur(event, e);
        }
    }
}
