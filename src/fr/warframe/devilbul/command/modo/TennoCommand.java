package fr.warframe.devilbul.command.modo;

import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.utils.annotations.command.Command;
import fr.warframe.devilbul.utils.annotations.help.Help;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static fr.warframe.devilbul.exception.ErreurGestion.afficheErreur;
import static fr.warframe.devilbul.exception.ErreurGestion.saveErreur;
import static fr.warframe.devilbul.functionality.Presentation.information;
import static fr.warframe.devilbul.utils.Find.findAdmin;
import static fr.warframe.devilbul.utils.Find.findModo;

public class TennoCommand extends SimpleCommand {

    @Command(name="tenno")
    @Help(field = "**syntaxe** :      !tenno @<pseudo>\n**condition :**   l'utilisateur doit être nouveau sur le serveur, ou aucun rôle, en particulier le rôle tenno\n" +
            "                        \n**effet :**            contourne la présentation, pour l'accès au reste du discord", categorie = Categorie.Modo)
    public static void addUserToTenno(MessageReceivedEvent event) {
        try {
            if (findAdmin(event, event.getMember()) || findModo(event, event.getMember())) {
                if (event.getMessage().toString().contains("@")) {
                    String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
                    JSONObject configRolesJson = new JSONObject(configRoles);
                    String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("tenno").getString("idRole");
                    User newTenno = event.getMessage().getMentionedUsers().get(0);

                    event.getGuild().getController().addRolesToMember(event.getGuild().getMemberById(newTenno.getId()), event.getGuild().getRoleById(roleID)).complete();
                    information(event.getAuthor().getId(), event.getGuild().getId());

                    event.getTextChannel().sendMessage("nouveau Tenno, " + newTenno.getName()).queue();
                }
                else
                    event.getTextChannel().sendMessage("pas de personne mentionnée").queue();
            }
            else
                event.getTextChannel().sendMessage("Tu n'as pas les droits pour cela. ^^").queue();
        } catch (Exception e) {
            afficheErreur(event.getMessage().getContentDisplay(), e);
            saveErreur(event.getMessage().getTextChannel().getName(), event.getAuthor().getName(), event.getAuthor().getId(), event.getMessage().getContentDisplay(), e);
        }
    }
}
