package warframe.bourreau;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;
import java.util.ArrayList;
import java.util.HashMap;

import warframe.bourreau.commands.*;
import warframe.bourreau.listener.MusicListener;
import warframe.bourreau.music.MusicManager;
import warframe.bourreau.util.WaitingSound;


import static warframe.bourreau.Bourreau.commands;
import static warframe.bourreau.util.CommandMap.registerCommands;

public class Init {
    public static ArrayList<Guild> serveur = new ArrayList<>();
    public static String serveurEmoteID;

    public static HashMap<String, AudioManager> audioManagers = new HashMap<>();
    public static HashMap<String, MusicManager> managers = new HashMap<>();
    public static ArrayList<WaitingSound> queueSon = new ArrayList<>();

    private static void initServeur(JDA jda) {
        serveurEmoteID = jda.getGuildsByName("serveur test", true).get(0).getId();
        serveur.addAll(jda.getGuilds());
    }

    private static void initAudioManager(JDA jda) {
        for (Guild guild : jda.getGuilds())
            audioManagers.put(guild.getId(), guild.getAudioManager());
    }

    private static void initMusicManager(JDA jda) {
        for (Guild guild : jda.getGuilds()) {
            managers.put(guild.getId(), new MusicManager());
            managers.get(guild.getId()).getPlayer(guild).getAudioPlayer().addListener(new MusicListener());
        }
    }

    private static void initCommand() {
        registerCommands(new ConfigCommand(), new AdminCommand(), new BasedCommand(), new CandidatCommand(), new ClaimCommand(), new ErreurCommand(), new GestionCommand(),
                new HelpCommand(), new InfoCommand(), new RaidCommand(), new RegleCommand(), new RivenCommand(), new SalonCommand(), new ShutdownCommand(),
                new SimpleCommand(), new SonCommand(), new SondageCommand(), new TrollCommand());
    }

    static void InitMain(JDA jda) {
        initServeur(jda);
        initAudioManager(jda);
        initMusicManager(jda);
        initCommand();
    }
}
