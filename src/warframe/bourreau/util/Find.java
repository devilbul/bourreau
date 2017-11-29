package warframe.bourreau.util;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.entities.impl.GuildImpl;
import net.dv8tion.jda.core.entities.impl.MemberImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static warframe.bourreau.InitID.*;
import static warframe.bourreau.Bourreau.getJda;
import static warframe.bourreau.riven.TxtToJson.sortie;
import static warframe.bourreau.util.Transforme.TransformeInfluence;

public class Find {

    public static VoiceChannel FindUserVC(MessageReceivedEvent event) {
        User user = event.getAuthor();
        Member member = new MemberImpl((GuildImpl) event.getGuild(), user);
        VoiceChannel channel = null;

        for (int i=0; i<event.getGuild().getVoiceChannels().size(); i++) {
            if (event.getGuild().getVoiceChannels().get(i).getMembers().contains(member)) {
                channel = event.getGuild().getVoiceChannels().get(i);
                break;
            }
        }

        return channel;
    }

    public static VoiceChannel FindUserMention(MessageReceivedEvent event) {
        User user = event.getMessage().getMentionedUsers().get(0);
        Member member = new MemberImpl((GuildImpl) event.getGuild(), user);
        VoiceChannel channel = null;

        for (int i=0; i<event.getGuild().getVoiceChannels().size(); i++) {
            if (event.getGuild().getVoiceChannels().get(i).getMembers().contains(member)) {
                channel = event.getGuild().getVoiceChannels().get(i);
                break;
            }
        }

        return channel;
    }

    public static double FindRivenJsonInfluenceDouble(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);

            for (Object name : rivenJson.names()) {
                for (int i=0; i<rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++) {
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return Double.parseDouble(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item")
                                .getJSONArray(String.valueOf(rivenJson.getJSONObject(String.valueOf(name))
                                        .getJSONObject("item").names().get(i))).getString(1));
                }
            }

            return 0.0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public static String FindRivenJsonInfluenceTransforme(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);

            for (Object name : rivenJson.names()) {
                for (int i=0; i<rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++) {
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return TransformeInfluence(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item")
                                .getJSONArray(String.valueOf(rivenJson.getJSONObject(String.valueOf(name))
                                        .getJSONObject("item").names().get(i))).getString(1));
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String FindRivenJsonInfluenceCatgorie(String cherche) {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);

            for (Object name : rivenJson.names()) {
                for (int i=0; i<rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().length(); i++) {
                    if (cherche.equals(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("item").names().get(i)))
                        return name.toString();
                }
            }

            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean FindAdmin(MessageReceivedEvent event, Member user) {
        Role admin = event.getGuild().getRoleById(adminID);

        return event.getGuild().getMembersWithRoles(admin).contains(user);
    }

    public static boolean FindModo(MessageReceivedEvent event, Member user) {
        Role modo = event.getGuild().getRoleById(modoID);

        return event.getGuild().getMembersWithRoles(modo).contains(user);
    }

    public static boolean FindAdminPrive(PrivateMessageReceivedEvent event, User user) {
        boolean trouve = false;

        Role admin = event.getJDA().getGuilds().get(0).getRoleById(adminID);

        for (int i=0; i< event.getJDA().getGuilds().get(0).getMembersWithRoles(admin).size(); i++) {
            if (event.getJDA().getGuilds().get(0).getMembersWithRoles(admin).get(i).getUser().equals(user))
                trouve = true;
        }

        return trouve;
    }

    public static boolean FindModoPrive(PrivateMessageReceivedEvent event, User user) {
        boolean trouve = false;

        Role admin = event.getJDA().getGuilds().get(0).getRoleById(modoID);

        for (int i=0; i< event.getJDA().getGuilds().get(0).getMembersWithRoles(admin).size(); i++) {
            if (event.getJDA().getGuilds().get(0).getMembersWithRoles(admin).get(i).getUser().equals(user))
                trouve = true;
        }

        return trouve;
    }

    public static boolean FindList(String[] list, String element) {
        boolean trouve = false;

        for (String aList : list) {
            if (aList.toLowerCase().equals(element))
                trouve = true;
        }

        return trouve;
    }

    public static int FindReponse(String[] choixList, String choix) {
        for (int i=0; i<choixList.length; i++) {
            if (choixList[i].toLowerCase().equals(choix))
                return i;
        }

        return -1;
    }

    public static boolean FindVotant(Member votant, JSONObject vote) {
        boolean trouve = false;

        for (int i=0; i<vote.getInt("nbVote"); i++) {
            if (vote.getJSONObject("vote"+(i+1)).getString("auteur").equals(votant.getUser().getName()) && vote.getJSONObject("vote"+(i+1)).getString("auteurID").equals(votant.getUser().getId()))
                trouve = true;
        }

        return trouve;
    }

    public static boolean FindClan(JSONArray arrayJson, String clan) {
        boolean trouve = false;

        for (int i=0; i<arrayJson.length(); i++) {
            if (arrayJson.getString(i).equals(clan))
                trouve = true;
        }

        return trouve;
    }
    public static String FindClanKey(JSONArray arrayJson, String clan) {
        String trouve = "";

        for (int i=0; i<arrayJson.length(); i++) {
            if (arrayJson.getString(i).toLowerCase().equals(clan.toLowerCase()))
                trouve = arrayJson.getString(i);
        }

        return trouve;
    }

    public static boolean FindClanLower(JSONArray arrayJson, String clan) {
        boolean trouve = false;

        for (int i=0; i<arrayJson.length(); i++) {
            if (arrayJson.getString(i).toLowerCase().equals(clan.toLowerCase()))
                trouve = true;
        }

        return trouve;
    }

    public static Emote FindEmote(JDA jda, String emoteName) {
        for (int i=0; i<jda.getGuilds().get(0).getEmotes().size(); i++){
            if (jda.getGuilds().get(0).getEmotes().get(i).getName().equals(emoteName))
                 return jda.getGuilds().get(0).getEmotes().get(i);
        }

        return null;
    }

    public static String FindBucher(JDA jda) {
        for (int i=0; i<jda.getVoiceChannels().size() ; i++) {
            if (jda.getVoiceChannels().get(i).getName().contains("Bucher"))
                return jda.getVoiceChannels().get(i).getId();
        }

        return null;
    }

    public static boolean FindRole(MessageReceivedEvent event, Role role) {
        Member user = event.getMember();
        List<Member> list = event.getGuild().getMembersWithRoles(role);

        for (Member aList : list) {
            if (!aList.getUser().equals(event.getJDA().getSelfUser()) && aList.equals(user))
                return true;
        }

        return false;
    }

    public static boolean FindJsonKey(JSONObject json, String key) {
        for (int i=0; i<json.names().length(); i++) {
            if (json.names().get(i).equals(key))
                return true;
        }

        return false;
    }

    public static boolean FindNamesJSONObkect(JSONArray array, String name) {
        for (int i=0; i<array.length(); i++) {
            if (array.getString(i).equals(name))
                return true;
        }

        return false;
    }

    public static boolean FindRolePrive(PrivateMessageReceivedEvent event, Role role) {
        for (int i=0; i<getJda().getGuildById(serveurID).getMemberById(event.getAuthor().getId()).getRoles().size(); i++) {
            if (getJda().getGuildById(serveurID).getMemberById(event.getAuthor().getId()).getRoles().get(i).equals(role))
                return true;
        }

        return false;
    }
}
