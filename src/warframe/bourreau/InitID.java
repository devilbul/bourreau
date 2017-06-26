package warframe.bourreau;

import net.dv8tion.jda.core.JDA;

import static warframe.bourreau.util.Find.FindBucher;
import static warframe.bourreau.util.Find.FindEmote;

public class InitID {
    //id textchannel
    public static String reglementID;
    public static String reclamationID;
    public static String accueilID;
    public static String candidatureID;
    public static String raidsID;
    public static String botSpamID;

    //id voicechannel
    public static String bucherID;

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
    public static String tennoID;
    public static String heretiqueID;
    public static String membreAllianceID;
    public static String leaderClanID;
    public static String modoID;

    private static void InitTextChannel(JDA jda) {
        accueilID = jda.getTextChannelsByName("accueil",true).get(0).getId();

        if (!jda.getTextChannelsByName("reglement",true).isEmpty())      reglementID = jda.getTextChannelsByName("reglement",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel reglement");

        if (!jda.getTextChannelsByName("reclamation",true).isEmpty())    reclamationID = jda.getTextChannelsByName("reclamation",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel reclamation");

        if (!jda.getTextChannelsByName("candidature",true).isEmpty())    candidatureID = jda.getTextChannelsByName("candidature",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel candidature");

        if (!jda.getTextChannelsByName("raids",true).isEmpty())          raidsID = jda.getTextChannelsByName("raids",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel raids");

        if (!jda.getTextChannelsByName("bot_spam",true).isEmpty())          botSpamID = jda.getTextChannelsByName("bot_spam",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon textuel bot_spam");
    }

    private static void InitVoiceChannel(JDA jda) {
        if (FindBucher(jda) != null)    bucherID = FindBucher(jda);
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas de salon vocal Bucher");
    }

    private static void InitEmote(JDA jda) {
        if (FindEmote(jda, "bombydou") != null)     bombydouID = FindEmote(jda, "bombydou").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote bombydou");

        if (FindEmote(jda, "warframe/bourreau") != null)     bourreauID = FindEmote(jda, "warframe/bourreau").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote warframe.bourreau");

        if (FindEmote(jda, "tenshinoob") != null)   tenshinoobID = FindEmote(jda, "tenshinoob").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote tenshinoob");

        if (FindEmote(jda, "trollface") != null)    trollfaceID = FindEmote(jda, "trollface").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote trollface");

        if (FindEmote(jda, "alliance") != null)     allianceID = FindEmote(jda, "alliance").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote alliance");

        if (FindEmote(jda, "saw6") != null)         saw6ID = FindEmote(jda, "saw6").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote saw6");

        if (FindEmote(jda, "FDP") != null)          FDPID = FindEmote(jda, "FDP").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote FDP");

        if (FindEmote(jda, "doge") != null)         dogeID = FindEmote(jda, "doge").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote doge");

        if (FindEmote(jda, "prime_soon") != null)   soonID = FindEmote(jda, "prime_soon").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote prime_soon");

        if (FindEmote(jda, "weed") != null)         weedID = FindEmote(jda, "weed").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote weed");

        if (FindEmote(jda, "check_mark") != null)         checkID = FindEmote(jda, "check_mark").getId();
        else jda.getTextChannelById(accueilID).sendMessage("Il n'y a pas d'emote check");
    }

    private static void InitRole(JDA jda) {
        if (!jda.getGuilds().get(0).getRolesByName("Admin",true).isEmpty())                      adminID = jda.getGuilds().get(0).getRolesByName("Admin",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Admin");

        if (!jda.getGuilds().get(0).getRolesByName("Tenno",true).isEmpty())                      tennoID = jda.getGuilds().get(0).getRolesByName("Tenno",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Tenno");

        if (!jda.getGuilds().get(0).getRolesByName("Hérétique, au bucher !",true).isEmpty())     heretiqueID = jda.getGuilds().get(0).getRolesByName("Hérétique, au bucher !",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Hérétique, au bucher !");

        if (!jda.getGuilds().get(0).getRolesByName("Membre Alliance",true).isEmpty())            membreAllianceID = jda.getGuilds().get(0).getRolesByName("Membre Alliance",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Membre Alliance");

        if (!jda.getGuilds().get(0).getRolesByName("Leader Clan",true).isEmpty())                leaderClanID = jda.getGuilds().get(0).getRolesByName("Leader Clan",true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Leader Clan");

        if (!jda.getGuilds().get(0).getRolesByName("Modo", true).isEmpty())                       modoID = jda.getGuilds().get(0).getRolesByName("Modo", true).get(0).getId();
        else jda.getTextChannelById(accueilID).sendMessage("il n'y a pas de rôle Leader Clan");
    }

    static void InitIDMain(JDA jda) {
        InitTextChannel(jda);
        InitVoiceChannel(jda);
        InitEmote(jda);
        InitRole(jda);
    }
}
