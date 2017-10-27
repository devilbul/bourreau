package warframe.bourreau;

import net.dv8tion.jda.core.JDA;

import static warframe.bourreau.util.Find.FindBucher;
import static warframe.bourreau.util.Find.FindEmote;

public class InitID {
    //id serveur
    public static String serveurID;

    //id textchannel
    public static String accueilID;
    public static String raidsID;
    public static String botSpamID;

    //id voicechannel
    public static String bucherID;

    //id category
    public static String clanID;

    //id emotes
    public static String bombydouID;
    public static String bourreauID;
    public static String tenshinoobID;
    public static String trollfaceID;
    public static String allianceID;
    public static String saw6ID;
    public static String FDPID;
    public static String dogeID;
    public static String soonID;
    public static String weedID;
    public static String checkID;

    //id role
    public static String adminID;
    public static String modoID;
    public static String tennoID;
    public static String heretiqueID;
    public static String membreAllianceID;
    public static String leaderClanID;

    private static void InitTextChannel(JDA jda) {
        accueilID = jda.getTextChannelsByName("accueil",true).get(0).getId();

        if (!jda.getTextChannelsByName("raids",true).isEmpty())          raidsID = jda.getTextChannelsByName("raids",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel raids").queue();

        if (!jda.getTextChannelsByName("bot_spam",true).isEmpty())          botSpamID = jda.getTextChannelsByName("bot_spam",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel bot_spam").queue();
    }

    private static void InitVoiceChannel(JDA jda) {
        if (FindBucher(jda) != null)    bucherID = FindBucher(jda);
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon vocal Bucher").queue();
    }

    private static void InitCategory(JDA jda) {
        if (!jda.getCategoriesByName("Alliance",true).isEmpty())    clanID = jda.getCategoriesByName("Alliance", true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de categorie Alliance").queue();
    }

    private static void InitEmote(JDA jda) {
        if (FindEmote(jda, "bombydou") != null)     bombydouID = FindEmote(jda, "bombydou").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote bombydou").queue();

        if (FindEmote(jda, "bourreau") != null)     bourreauID = FindEmote(jda, "bourreau").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote bourreau").queue();

        if (FindEmote(jda, "tenshinoob") != null)   tenshinoobID = FindEmote(jda, "tenshinoob").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote tenshinoob").queue();

        if (FindEmote(jda, "trollface") != null)    trollfaceID = FindEmote(jda, "trollface").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote trollface").queue();

        if (FindEmote(jda, "alliance") != null)     allianceID = FindEmote(jda, "alliance").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote alliance").queue();

        if (FindEmote(jda, "saw6") != null)         saw6ID = FindEmote(jda, "saw6").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote saw6").queue();

        if (FindEmote(jda, "FDP") != null)          FDPID = FindEmote(jda, "FDP").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote FDP").queue();

        if (FindEmote(jda, "doge") != null)         dogeID = FindEmote(jda, "doge").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote doge").queue();

        if (FindEmote(jda, "prime_soon") != null)   soonID = FindEmote(jda, "prime_soon").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote prime_soon").queue();

        if (FindEmote(jda, "weed") != null)         weedID = FindEmote(jda, "weed").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote weed").queue();

        if (FindEmote(jda, "check_mark") != null)         checkID = FindEmote(jda, "check_mark").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote check").queue();
    }

    private static void InitRole(JDA jda) {
        if (!jda.getGuilds().get(0).getRolesByName("Admin",true).isEmpty())                      adminID = jda.getGuilds().get(0).getRolesByName("Admin",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Admin").queue();

        if (!jda.getGuilds().get(0).getRolesByName("Tenno",true).isEmpty())                      tennoID = jda.getGuilds().get(0).getRolesByName("Tenno",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Tenno").queue();

        if (!jda.getGuilds().get(0).getRolesByName("Hérétique, au bucher !",true).isEmpty())     heretiqueID = jda.getGuilds().get(0).getRolesByName("Hérétique, au bucher !",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Hérétique, au bucher !").queue();

        if (!jda.getGuilds().get(0).getRolesByName("Membre Alliance",true).isEmpty())            membreAllianceID = jda.getGuilds().get(0).getRolesByName("Membre Alliance",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Membre Alliance").queue();

        if (!jda.getGuilds().get(0).getRolesByName("Leader Clan",true).isEmpty())                leaderClanID = jda.getGuilds().get(0).getRolesByName("Leader Clan",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Leader Clan").queue();

        if (!jda.getGuilds().get(0).getRolesByName("Modo", true).isEmpty())                       modoID = jda.getGuilds().get(0).getRolesByName("Modo", true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Leader Clan").queue();
    }

    static void InitIDMain(JDA jda) {
        InitTextChannel(jda);
        InitVoiceChannel(jda);
        InitCategory(jda);
        InitEmote(jda);
        InitRole(jda);

        //serveurID = jda.getGuildsByName("French Connection", true).get(0).getId();
        serveurID = jda.getGuildsByName("serveur test", true).get(0).getId();
    }
}
