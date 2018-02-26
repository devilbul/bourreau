package fr.warframe.devilbul.functionality;

import fr.warframe.devilbul.Bourreau;
import fr.warframe.devilbul.utils.annotations.functionality.Functionality;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static fr.warframe.devilbul.Init.serveurEmoteID;
import static fr.warframe.devilbul.utils.find.Contains.messageContains;

public class Presentation {

    @Functionality(name = "presentation", description = "afin que les nouveaux membres se présente")
    public static boolean isMadePresentation(String message, String idTextChanenl, String idAuthor, String idServeur) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(idServeur).getJSONArray("fonctionnalites");
            JSONObject configTextChannelJson = new JSONObject(configTextChannel).getJSONObject("textChannels").getJSONObject(idServeur).getJSONObject("textChannels");
            Member destinataire = Bourreau.jda.getGuildById(idServeur).getMemberById(idAuthor);

            if (idTextChanenl.equals(configTextChannelJson.getJSONObject("accueil").getString("idTextChannel")) && configFunctionalityJson.toList().contains("presentation") && (destinataire.getRoles().contains(Bourreau.jda.getRoleById("414106039286497281")) || destinataire.getRoles().size() == 0)) {
                List<String> mots = Arrays.asList("presente", "presentation", "présente", "présentation", "mastery rank", "palier", "je suis", "moi c'est", "tenno", "rank", "maitrise", "pallier", "maîtrise", "founder", "fondateur", "pc", "ps4", "xbox", "warframe", "rang");

                if (message.contains(" ") && messageContains(message, mots))
                    return true;
                else {
                    String textChannelID = configTextChannelJson.getJSONObject("accueil").getString("idTextChannel");
                    MessageBuilder messageMP = new MessageBuilder();

                    messageMP.append("Salutation !\n");
                    messageMP.append(destinataire.getUser());
                    messageMP.append(", votre présentation n'est pas correctement.");
                    messageMP.append("\nVeuillez refaire votre présentation dans le salon textuel ");
                    messageMP.append(Bourreau.jda.getGuildById(idServeur).getTextChannelById(textChannelID));
                    messageMP.append("\nA bientôt, peut-être.\n");
                    messageMP.append(Bourreau.jda.getSelfUser());
                    destinataire.getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
                }
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void affecteRole(String idMessage, String idTextChanenl, String idAuthor, String idServeur) {
        try {
            String configFunctionaliy = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configFunctionality.json")));
            String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            String configEmotes = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configEmote.json")));
            JSONArray configFunctionalityJson = new JSONObject(configFunctionaliy).getJSONObject("fonctionnalites").getJSONObject(idServeur).getJSONArray("fonctionnalites");
            JSONObject configRolesJson = new JSONObject(configRoles);
            JSONObject configEmotesJson = new JSONObject(configEmotes);
            String emoteID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idEmote");
            String serverID = configEmotesJson.getJSONObject("emotes").getJSONObject("default").getString("idServer");

            if (configFunctionalityJson.toList().contains("min_role_to_use_bot")) {
                String roleID = configRolesJson.getJSONObject("roles").getJSONObject(idServeur).getJSONObject("roles").getJSONObject("tenno").getString("idRole");
                Role tenno = Bourreau.jda.getGuildById(idServeur).getRoleById(roleID);

                Bourreau.jda.getGuildById(idServeur).getController().addRolesToMember(Bourreau.jda.getGuildById(idServeur).getMemberById(idAuthor), tenno).queue();
            }

            Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChanenl).addReactionById(idMessage, Bourreau.jda.getGuildById(serverID).getEmoteById(emoteID)).queue();
            information(idAuthor, idServeur);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void information(String idAuthor, String idServeur) {
        Member destinataire = Bourreau.jda.getGuildById(idServeur).getMemberById(idAuthor);
        MessageBuilder messageMP = new MessageBuilder();

        messageMP.append("Salut à toi, ");
        messageMP.append(destinataire);
        messageMP.append(", Tenno\n\nTu as maintenant accès au reste du discord, notament aux commandes du bot ");
        messageMP.append(Bourreau.jda.getSelfUser());
        messageMP.append(",\nque tu peux voir avec la commande **!help**");
        messageMP.append("\n\nAu plaisir de te revoir sur le discord.");
        messageMP.append("\n\nCordialement, ");
        messageMP.append(Bourreau.jda.getSelfUser());
        messageMP.append("\n\nPS : tu peux participer à l'évolution du bot, avec le commande **!idee**, en écrivant ton idée sur le google docs\n");
        messageMP.append(Bourreau.jda.getGuildById(serveurEmoteID).getMemberById("180419578554220545"));

        destinataire.getUser().openPrivateChannel().complete().sendMessage(messageMP.build()).queue();
    }
}
