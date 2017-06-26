package warframe.bourreau.commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.util.Find.*;
import static warframe.bourreau.util.Recup.recupString;

public class SondageCommand extends  Command {

    public static void Sondage(MessageReceivedEvent event) {
        if (event.getMessage().getContent().contains(" ")) {
            String adresseSondage = System.getProperty("user.dir") + File.separator + "sondage" + File.separator + "sondage.json";
            String adresseVote = System.getProperty("user.dir") + File.separator + "sondage" + File.separator + "vote.json";
            String rawCommande = recupString(event.getMessage().getContent().toLowerCase());
            String commande = rawCommande.replaceFirst(" ", "@").split("@")[0];

            switch (commande) {
                case "affiche":
                    AfficheSondage(event, adresseSondage);
                    break;
                case "reponses":
                    if (ReponsesSondage(event, adresseSondage, adresseVote))
                        AfficheSondage(event, adresseSondage);
                    break;
                case "clear":
                    ClearSondage(event, adresseSondage, adresseVote);
                    break;
                case "create":
                    CreateSondage(event, adresseSondage, adresseVote);
                    break;
                case "resultat":
                    ResultatSondage(event, adresseSondage, adresseVote);
                    break;
                case "vote":
                    VoteSondage(event, adresseSondage, adresseVote);
                    break;
                default:
                    MessageBuilder message = new MessageBuilder();

                    event.getTextChannel().sendMessage("Commande inconnue. !help pour lister les commandes. \nPS : apprends à écrire.").queue();

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

    private static void AfficheSondage(MessageReceivedEvent event, String adresseSondage) {
        try {
            if (new File(adresseSondage).exists()) {
                String sondage = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "sondage.json")));
                JSONObject sondageJson = new JSONObject(sondage);

                if (sondageJson.toString().contains("reponses")) {
                    EmbedBuilder sondageDisplay = new EmbedBuilder();
                    JSONObject reponsesJson = sondageJson.getJSONObject("reponses");
                    String reponses = "";

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses += reponsesJson.getString("reponse" + (i + 1)) + "\n";

                    sondageDisplay.setTitle("Sondage en cours :", null);
                    sondageDisplay.setDescription("fait par : " + sondageJson.getString("createur"));
                    sondageDisplay.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    sondageDisplay.addField(sondageJson.getString("question"), reponses, false);
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
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ClearSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        if (new File(adresseSondage).exists() && new File(adresseVote).exists()) {
            if (new File(adresseSondage).exists())
                if (new File(adresseSondage).delete())

            if (new File(adresseVote).exists())
                if (new File(adresseVote).delete())

            event.getTextChannel().sendMessage("sondage supprimé.").queue();
        }
        else
            event.getTextChannel().sendMessage("aucun sondage en cours.").queue();
    }

    private static void CreateSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            if (!(new File(adresseSondage).exists())) {
                String commande = recupString(event.getMessage().getContent());

                if (commande.contains(" ")) {
                    String question = recupString(commande);
                    JSONObject sondageJSON = new JSONObject();
                    JSONObject voteJSON = new JSONObject();
                    FileWriter fileSondage = new FileWriter(adresseSondage);
                    FileWriter fileVote = new FileWriter(adresseVote);

                    sondageJSON.put("createur", event.getAuthor().getName());
                    sondageJSON.put("createurID", event.getAuthor().getId());
                    sondageJSON.put("question", question);
                    voteJSON.put("nbVote", 0);

                    event.getTextChannel().sendMessage("sondage créé").queue();
                    event.getTextChannel().sendMessage("tapez **__!sondage reponses__**, suivi des diverses réponses souhaitées.").queue();

                    fileSondage.write(sondageJSON.toString());
                    fileSondage.flush();
                    fileSondage.close();
                    fileVote.write(voteJSON.toString());
                    fileVote.flush();
                    fileVote.close();
                }
                else
                    event.getTextChannel().sendMessage("pas de question saisie.").queue();
            }
            else
                event.getTextChannel().sendMessage("il y a déjà un sondage en cours.").queue();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean ReponsesSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            if (new File(adresseSondage).exists()) {
                String commande = recupString(event.getMessage().getContent());

                if( commande.contains(" ")) {
                    String rawReponses = recupString(commande);
                    String[] reponses = rawReponses.split(" / ");

                    if (reponses.length>1) {
                        String sondage = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "sondage.json")));
                        String vote = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "vote.json")));
                        JSONObject sondageJson = new JSONObject(sondage);
                        JSONObject voteJson = new JSONObject(vote);

                        if (!sondageJson.toString().contains("reponses")) {
                            JSONObject reponsesJson = new JSONObject();
                            FileWriter fileSondage = new FileWriter(adresseSondage);
                            FileWriter fileVote = new FileWriter(adresseVote);

                            for (int i = 0; i < reponses.length; i++) {
                                reponsesJson.put("reponse"+(i+1), reponses[i]);
                                voteJson.put("nbReponse"+(i+1), 0);
                            }

                            reponsesJson.put("nb", reponses.length);

                            sondageJson.put("reponses", reponsesJson);

                            event.getTextChannel().sendMessage("les réponses sont enregistrées.").queue();

                            fileSondage.write(sondageJson.toString());
                            fileSondage.flush();
                            fileSondage.close();
                            fileVote.write(voteJson.toString());
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
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private static void ResultatSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            if (new File(adresseSondage).exists() && new File(adresseVote).exists()) {
                String sondage = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "sondage.json")));
                JSONObject sondageJson = new JSONObject(sondage);

                if (sondageJson.toString().contains("reponses")) {
                    String vote = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "vote.json")));
                    JSONObject voteJson = new JSONObject(vote);
                    JSONObject reponsesJson = sondageJson.getJSONObject("reponses");
                    EmbedBuilder resultat = new EmbedBuilder();
                    String reponses = "";
                    int nbReponse = sondageJson.getJSONObject("reponses").getInt("nb");
                    double[] stats = new double[nbReponse];

                    for (int i = 0; i < reponsesJson.getInt("nb"); i++)
                        reponses += reponsesJson.getString("reponse" + (i + 1)) + "\n";

                    resultat.setTitle("Résultat sondage en cours :", null);
                    resultat.setThumbnail("http://wivtunisia.com/wp-content/uploads/2014/10/survey-icon-green1.png");
                    resultat.setDescription("fait par : " + sondageJson.getString("createur"));
                    resultat.addField(sondageJson.getString("question"), reponses, false);
                    resultat.addField("__résultat :__ ", "", false);

                    for(int i=0; i<sondageJson.getJSONObject("reponses").getInt("nb"); i++) {
                        resultat.addField("nombre de vote pour **__" + reponsesJson.getString("reponse" + (i + 1)) + " :__**",
                                String.valueOf(voteJson.getInt("nbReponse" + (i + 1))), true);
                    }

                    resultat.addField("__statistique :__", "", false);

                    for (int i = 0; i < nbReponse; i++) {
                        stats[i] = (voteJson.getInt("nbReponse" + (i + 1)) / (voteJson.getInt("nbVote") * 1.0)) * 100;
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
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void VoteSondage(MessageReceivedEvent event, String adresseSondage, String adresseVote) {
        try {
            if (new File(adresseSondage).exists() && new File(adresseVote).exists()) {
                String sondage = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "sondage.json")));
                JSONObject sondageJson = new JSONObject(sondage);

                if (sondageJson.toString().contains("reponses")) {
                    String commande = recupString(event.getMessage().getContent().toLowerCase());

                    if (commande.contains(" ")) {
                        String vote = new String(Files.readAllBytes(Paths.get("sondage" + File.separator + "vote.json")));
                        JSONObject reponsesJson = sondageJson.getJSONObject("reponses");
                        JSONObject voteJson = new JSONObject(vote);
                        JSONObject voteFaitJson = new JSONObject();
                        String[] reponsesList = new String[reponsesJson.getInt("nb")];
                        String reponse = recupString(commande).toLowerCase();
                        Member auteur = event.getMember();

                        for (int i=0; i<reponsesJson.getInt("nb"); i++)
                            reponsesList[i] = reponsesJson.getString("reponse"+(i+1));

                        if (!FindVotant(auteur, voteJson)) {
                            if (FindList(reponsesList, reponse)) {
                                FileWriter file = new FileWriter(adresseVote);
                                int nbVote = voteJson.getInt("nbVote") + 1;
                                int reponseFaite = FindReponse(reponsesList, reponse) + 1;

                                voteFaitJson.put("auteur", event.getAuthor().getName());
                                voteFaitJson.put("auteurID", event.getAuthor().getId());
                                voteFaitJson.put("reponses", reponse);

                                voteJson.put("nbReponse" + reponseFaite, voteJson.getInt("nbReponse" + reponseFaite) + 1);

                                voteJson.put("nbVote", nbVote);
                                voteJson.put("vote" + nbVote, voteFaitJson);

                                event.getTextChannel().sendMessage("a voté.").queue();

                                file.write(voteJson.toString());
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
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
