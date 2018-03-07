package fr.warframe.devilbul.utils;

import fr.warframe.devilbul.utils.enumeration.ClanWarframe;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.GuildImpl;
import net.dv8tion.jda.core.entities.impl.MemberImpl;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static fr.warframe.devilbul.riven.TxtToJson.sortie;
import static fr.warframe.devilbul.utils.Transforme.transformeInfluence;
import static fr.warframe.devilbul.utils.enumeration.ClanWarframe.*;

public class Find {

    public static VoiceChannel findUserVC(MessageReceivedEvent event) {
        Member member = event.getMember();
        VoiceChannel channel = null;

        for (int i = 0; i < event.getGuild().getVoiceChannels().size(); i++)
            if (event.getGuild().getVoiceChannels().get(i).getMembers().contains(member)) {
                channel = event.getGuild().getVoiceChannels().get(i);
                break;
            }

        return channel;
    }

    public static VoiceChannel findUserMention(MessageReceivedEvent event) {
        User user = event.getMessage().getMentionedUsers().get(0);
        Member member = new MemberImpl((GuildImpl) event.getGuild(), user);
        VoiceChannel channel = null;

        for (int i = 0; i < event.getGuild().getVoiceChannels().size(); i++) {
            if (event.getGuild().getVoiceChannels().get(i).getMembers().contains(member)) {
                channel = event.getGuild().getVoiceChannels().get(i);
                break;
            }
        }

        return channel;
    }

    public static double findRivenJsonInfluenceDouble(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);

            for (Object name : rivenJson.names())
                for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return Double.parseDouble(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item")
                                .getString(String.valueOf(rivenJson.getJSONObject(String.valueOf(name))
                                        .getJSONObject("item").names().get(i))));

            return 0.0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static String findRivenJsonInfluenceTransforme(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);

            for (Object name : rivenJson.names())
                for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return transformeInfluence(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item")
                                .getString(String.valueOf(rivenJson.getJSONObject(String.valueOf(name))
                                        .getJSONObject("item").names().get(i))));

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String findRivenJsonInfluenceCatgorie(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            for (Object name : rivenJson.names())
                for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++)
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return name.toString();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static boolean findAdmin(MessageReceivedEvent event, Member user) {
        try {
            if (findAdminSupreme(event.getAuthor().getId()) || findAdminProvisoir(event.getAuthor().getId()))
                return true;
            else {
                String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
                JSONObject configRolesJson = new JSONObject(configRoles);
                String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("admin").getString("idRole");

                if (!roleID.isEmpty()) {
                    Role admin = event.getGuild().getRoleById(roleID);

                    return event.getGuild().getMembersWithRoles(admin).contains(user);
                }

                return user.hasPermission(Permission.ADMINISTRATOR);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean findModo(MessageReceivedEvent event, Member user) {
        try {
            String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRolesJson = new JSONObject(configRoles);
            String roleID = configRolesJson.getJSONObject("roles").getJSONObject(event.getGuild().getId()).getJSONObject("roles").getJSONObject("modo").getString("idRole");

            if (!roleID.isEmpty()) {
                Role modo = event.getGuild().getRoleById(roleID);

                return event.getGuild().getMembersWithRoles(modo).contains(user);
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean findAdminPrive(PrivateMessageReceivedEvent event, User user) {
        try {
            String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRolesJson = new JSONObject(configRoles);
            String roleID;
            Role admin;

            for (Guild guild : event.getJDA().getGuilds()) {
                roleID = configRolesJson.getJSONObject("roles").getJSONObject(guild.getId()).getJSONObject("roles").getJSONObject("admin").getString("idRole");
                admin = event.getJDA().getGuildById(guild.getId()).getRoleById(roleID);

                for (int i = 0; i < guild.getMembersWithRoles(admin).size(); i++)
                    if (guild.getMembersWithRoles(admin).get(i).equals(user))
                        return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean findModoPrive(PrivateMessageReceivedEvent event, User user) {
        try {
            String configRoles = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
            JSONObject configRolesJson = new JSONObject(configRoles);
            String roleID;
            Role modo;

            for (Guild guild : event.getJDA().getGuilds()) {
                roleID = configRolesJson.getJSONObject("roles").getJSONObject(guild.getId()).getJSONObject("roles").getJSONObject("modo").getString("idRole");
                modo = event.getJDA().getGuildById(guild.getId()).getRoleById(roleID);

                for (int i = 0; i < guild.getMembersWithRoles(modo).size(); i++)
                    if (guild.getMembersWithRoles(modo).get(i).equals(user))
                        return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean findList(String[] list, String element) {
        boolean trouve = false;

        for (String aList : list) {
            if (aList.toLowerCase().equals(element))
                trouve = true;
        }

        return trouve;
    }

    public static int findReponse(String[] choixList, String choix) {
        for (int i = 0; i < choixList.length; i++) {
            if (choixList[i].toLowerCase().equals(choix))
                return i;
        }

        return -1;
    }

    public static boolean findVotant(Member votant, JSONObject vote) {
        boolean trouve = false;

        for (int i = 0; i < vote.getInt("nbVote"); i++) {
            if (vote.getJSONObject("vote" + (i + 1)).getString("auteur").equals(votant.getUser().getName()) && vote.getJSONObject("vote" + (i + 1)).getString("auteurID").equals(votant.getUser().getId()))
                trouve = true;
        }

        return trouve;
    }

    public static boolean findClan(JSONArray arrayJson, String clan) {
        boolean trouve = false;

        for (int i = 0; i < arrayJson.length(); i++) {
            if (arrayJson.getString(i).equals(clan))
                trouve = true;
        }

        return trouve;
    }

    public static String findClanKey(JSONArray arrayJson, String clan) {
        String trouve = "";

        for (int i = 0; i < arrayJson.length(); i++) {
            if (arrayJson.getString(i).toLowerCase().equals(clan.toLowerCase()))
                trouve = arrayJson.getString(i);
        }

        return trouve;
    }

    public static boolean findClanLower(JSONArray arrayJson, String clan) {
        boolean trouve = false;

        for (int i = 0; i < arrayJson.length(); i++) {
            if (arrayJson.getString(i).toLowerCase().equals(clan.toLowerCase()))
                trouve = true;
        }

        return trouve;
    }

    public static boolean findRole(MessageReceivedEvent event, Role role) {
        Member user = event.getMember();
        List<Member> list = event.getGuild().getMembersWithRoles(role);

        for (Member aList : list) {
            if (!aList.getUser().equals(event.getJDA().getSelfUser()) && aList.equals(user))
                return true;
        }

        return false;
    }

    public static boolean findJsonKey(JSONObject json, String key) {
        for (int i = 0; i < json.names().length(); i++) {
            if (json.names().get(i).equals(key))
                return true;
        }

        return false;
    }

    public static boolean findNamesJSONObkect(JSONArray array, String name) {
        for (int i = 0; i < array.length(); i++) {
            if (array.getString(i).equals(name))
                return true;
        }

        return false;
    }

    /*public static boolean findRolePrive(PrivateMessageReceivedEvent event, Role role) {
        for (int i=0; i<getJDA().getGuildById(serveurID).getMemberById(event.getAuthor().getId()).getRoles().size(); i++) {
            if (getJDA().getGuildById(serveurID).getMemberById(event.getAuthor().getId()).getRoles().get(i).equals(role))
                return true;
        }

        return false;
    }*/

    private static boolean findUser(Guild guild, User cible) {
        for (Member user : guild.getMembers())
            if (user.getUser().equals(cible))
                return true;
        return false;
    }

    public static boolean findUserToServers(PrivateMessageReceivedEvent event) {
        User auteur = event.getAuthor();

        for (Guild guild : event.getJDA().getGuilds())
            if (findUser(guild, auteur))
                if (guild.getMember(auteur).getRoles().size() > 0)
                    return true;
        return false;
    }

    public static boolean findCommand(MessageReceivedEvent event, String commande) {
        try {
            String configCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configCommand.json")));
            JSONObject configCommandJson = new JSONObject(configCommand);

            if (findJsonKey(configCommandJson.getJSONObject("commandes"), event.getGuild().getId())) {
                JSONArray listeCommandeJson = configCommandJson.getJSONObject("commandes").getJSONObject(event.getGuild().getId()).getJSONArray("commandes");

                for (int i = 0; i < listeCommandeJson.length(); i++)
                    if (listeCommandeJson.getString(i).equals(commande))
                        return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Role findAdminRole(GuildJoinEvent event) {
        for (Role role : event.getGuild().getRoles())
            for (Permission permission : role.getPermissions())
                if (permission.equals(Permission.ADMINISTRATOR))
                    return role;

        return null;
    }

    public static int findRivenScript(Elements scripts) {
        for (int i = 0; i < scripts.size(); i++)
            if (scripts.get(i).toString().contains("var RivenStuff = {"))
                return i;

        return -1;
    }

    public static boolean findAdminSupreme(String id) {
        try {
            String adminSupreme = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "adminSupreme.json")));
            JSONArray adminSupremeJson = new JSONArray(adminSupreme);

            for (int i = 0; i < adminSupremeJson.length(); i++) {
                if (adminSupremeJson.getString(i).equals(id))
                    return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean findAdminProvisoir(String id) {
        try {
            String adminProvisoir = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "adminProvisoir.json")));
            JSONArray adminProvisoirJson = new JSONArray(adminProvisoir);

            for (int i = 0; i < adminProvisoirJson.length(); i++) {
                if (adminProvisoirJson.getJSONObject(i).getString("id").equals(id))
                    return true;
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int findIndexStringArray(JSONArray array, String value) {
        for (int i = 0; i < array.length(); i++)
            if (array.get(i).getClass().getSimpleName().equals("String"))
                if (array.getString(i).equals(value))
                    return i;

        return -1;
    }

    public static Category findCategory(Guild guild, String category) {
        for (Category category1 : guild.getCategories())
            if (category1.getName().equals(category))
                return category1;

        return null;
    }

    public static Role findRole(Guild guild, String role) {
        for (Role role1 : guild.getRoles())
            if (role1.getName().equals(role))
                return role1;

        return null;
    }

    public static VoiceChannel findVoiceChannel(Guild guild, String voiceChannel) {
        for (VoiceChannel voiceChannel1 : guild.getVoiceChannels())
            if (voiceChannel1.getName().equals(voiceChannel))
                return voiceChannel1;

        return null;
    }

    public static boolean findCommandStringList(String[] liste, String commande) {
        for (String s : liste)
            if (s.equals(commande))
                return true;

        return false;
    }

    public static boolean findValueObjectList(List<Object> liste, Object objet) {
        for (Object o : liste)
            if (o.equals(objet))
                return true;

        return false;
    }

    public static boolean findGuildJDAList(List<Guild> liste, String idGuild) {
        for (Guild guild : liste)
            if (guild.getId().equals(idGuild))
                return true;

        return false;
    }

    public static boolean findEmoteGuildList(List<Emote> liste, String idEmote) {
        for (Emote emote : liste)
            if (emote.getId().equals(idEmote))
                return true;

        return false;
    }

    public static boolean isTypeClan(String in) {
        List<ClanWarframe> types = Arrays.asList(Fantome, Ombre, Tempete, Montagne, Lune);

        for (ClanWarframe type : types)
            if (in.equals(type.getType()))
                return true;

        return false;
    }

    public static boolean isMusicCommand(String command) {
        try {
            String musicCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "music.json")));
            JSONArray musicCommandJson = new JSONObject(musicCommand).getJSONArray("music_command");

            for (int i = 0; i < musicCommandJson.length(); i++)
               if (musicCommandJson.getJSONObject(i).getString("command").equals(command))
                   return true;

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getMusicCommand(String command) {
        try {
            String musicCommand = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "music.json")));
            JSONArray musicCommandJson = new JSONObject(musicCommand).getJSONArray("music_command");

            for (int i = 0; i < musicCommandJson.length(); i++)
               if (musicCommandJson.getJSONObject(i).getString("command").equals(command))
                   return musicCommandJson.getJSONObject(i).getString("file");

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isUserCensure(String id) {
        try {
            String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
            JSONArray antiSpamJson = new JSONArray(antiSpam);

            for (int i = 0; i < antiSpamJson.length(); i++)
                if (antiSpamJson.getJSONObject(i).getString("id").equals(id))
                    return true;

            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getUserCensure(String id) {
        try {
            String antiSpam = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "anti_spam.json")));
            JSONArray antiSpamJson = new JSONArray(antiSpam);

            for (int i = 0; i < antiSpamJson.length(); i++)
                if (antiSpamJson.getJSONObject(i).getString("id").equals(id))
                    return i;

            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
