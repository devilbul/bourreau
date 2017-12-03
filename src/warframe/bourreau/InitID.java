package warframe.bourreau;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.managers.AudioManager;
import warframe.bourreau.commands.*;
import warframe.bourreau.listener.MusicListener;
import warframe.bourreau.music.MusicManager;

import java.util.ArrayList;

import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.util.CommandMap.registerCommands;
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

    //music
    public static AudioManager audioManager;
    public static MusicManager manager;
    public static ArrayList<String> queueSon;

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
        if (!jda.getCategoriesByName("Clan",true).isEmpty())    clanID = jda.getCategoriesByName("Clan", true).get(0).getId();
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

    private static void InitAudioManager(JDA jda){
        audioManager = jda.getGuilds().get(0).getAudioManager();
    }

    private static void InitMusicManager(JDA jda) {
        manager = new MusicManager();
        manager.getPlayer(jda.getGuilds().get(0)).getAudioPlayer().addListener(new MusicListener());
    }

    private static void InitQueue() {
        queueSon = new ArrayList<>();
    }

    private static void InitHashMap() {
        registerCommands(new AdminCommand(), new BasedCommand(), new CandidatCommand(), new ClaimCommand(), new ErreurCommand(), new GestionCommand(),
                new HelpCommand(), new InfoCommand(), new RaidCommand(), new RegleCommand(), new RivenCommand(), new SalonCommand(), new ShutdownCommand(),
                new SimpleCommand(), new SonCommand(), new SondageCommand(), new TrollCommand());
        commands.put("ah" , new SimpleCommand());
        commands.put("bucher" , new SimpleCommand());
        commands.put("gg" , new SimpleCommand());
        commands.put("gogole" , new SimpleCommand());
        commands.put("nah" , new SimpleCommand());
        commands.put("pigeon" , new SimpleCommand());
        commands.put("son", new SimpleCommand());
        commands.put("souffrir" , new SimpleCommand());
        commands.put("trump" , new SimpleCommand());
        commands.put("trumpcomp" , new SimpleCommand());
        commands.put("trumpcomp2" , new SimpleCommand());
        commands.put("trumpcomp3" , new SimpleCommand());

    }

    static void InitIDMain(JDA jda) {
        InitTextChannel(jda);
        InitVoiceChannel(jda);
        InitCategory(jda);
        InitEmote(jda);
        InitRole(jda);
        InitAudioManager(jda);
        InitMusicManager(jda);
        InitHashMap();
        InitQueue();

        //serveurID = jda.getGuildsByName("French Connection", true).get(0).getId();
        serveurID = jda.getGuildsByName("serveur test", true).get(0).getId();
    }
}
