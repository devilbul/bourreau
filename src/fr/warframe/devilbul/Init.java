package fr.warframe.devilbul;

import fr.warframe.devilbul.api.warframe.Cetus;
import fr.warframe.devilbul.command.SimpleCommand;
import fr.warframe.devilbul.command.admin.*;
import fr.warframe.devilbul.command.alliance.AllianceCommand;
import fr.warframe.devilbul.command.alliance.ClansCommand;
import fr.warframe.devilbul.command.alliance.LeadCommand;
import fr.warframe.devilbul.command.config.ConfigCommand;
import fr.warframe.devilbul.command.error.*;
import fr.warframe.devilbul.command.help.HelpCommand;
import fr.warframe.devilbul.command.info.*;
import fr.warframe.devilbul.command.modo.*;
import fr.warframe.devilbul.command.riven.*;
import fr.warframe.devilbul.command.sondage.*;
import fr.warframe.devilbul.command.supreme.*;
import fr.warframe.devilbul.command.warframe.*;
import fr.warframe.devilbul.functionality.AvoidDefaultChannel;
import fr.warframe.devilbul.functionality.MinRole;
import fr.warframe.devilbul.functionality.Presentation;
import fr.warframe.devilbul.utils.enumeration.Categorie;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.managers.AudioManager;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static fr.warframe.devilbul.Bourreau.helpList;
import static fr.warframe.devilbul.utils.annotations.command.CommandMap.registerListCommands;
import static fr.warframe.devilbul.utils.annotations.functionality.FunctionalityMap.registerFunctionalities;

public class Init {

    public static ArrayList<Guild> serveur = new ArrayList<>();
    public static String serveurEmoteID;
    public static HashMap<String, AudioManager> audioManagers = new HashMap<>();
    public static String logoUrlAlliance;
    public static List<Object> commandList;
    private JDA jda;

    public Init(JDA jda) {
        this.jda = jda;
    }

    private void initListCommand() {
        commandList = Arrays.asList(new SimpleCommand(), new HelpCommand(), new ConfigCommand(), new ErreurCommand(), new AllianceCommand(), new ClansCommand(),
                new LeadCommand(), new AlertCommand(), new GoalsCommand(), new InvasionsCommand(), new PvpCommand(), new BaroCommand(), new VoidCommand(),
                new FuturFeatureCommand(), new UpdatesCommand(), new IdeaCommand(), new InfoCommand(), new ProgresCommand(), new SiteCommand(), new SteamCommand(),
                new SyndicatCommand(), new SortieCommand(), new TennoCommand(), new BanCommand(), new DeafenCommand(), new UnBanCommand(), new UnDeafenCommand(),
                new MuteCommand(), new UnMuteCommand(), new SetGameCommand(), new KickCommand(), new PingCommand(), new GetBansCommand(), new DeleteErreurCommand(),
                new DetailErreurCommand(), new ListErreurCommand(), new PurgeErreurCommand(), new AddProvisoirCommand(), new DeleteProvisoirCommand(),
                new ListProvisoirCommand(), new AddClanComand(), new RemoveClanCommand(), new AddLogoUrlCommand(), new AddTypeClanCommand(), new RivenCommand(),
                new UpdateRivenCommand(), new TraiteCommand(), new InfluenceCommand(), new ShutdownCommand(), new RebootCommand(), new SondageCommand(),
                new ClearCommand(), new CreateCommand(), new DisplayCommand(), new ResponsesCommand(), new ResultCommand(), new VoteCommand()
        );
    }

    private void initServeur() {
        serveurEmoteID = this.jda.getGuildsByName("serveur test", true).get(0).getId();
        serveur.addAll(this.jda.getGuilds());
    }

    private void initAudioManager() {
        for (Guild guild : this.jda.getGuilds())
            audioManagers.put(guild.getId(), guild.getAudioManager());
    }

    private void initMusicManager() {

    }

    private void initFunctionality() {
        registerFunctionalities(new AvoidDefaultChannel(), new MinRole(), new Presentation());
    }

    private void initHelpList() {
        for (Categorie category : Categorie.values())
            helpList.put(category.toString(), new ArrayList<>());
    }

    private void initCommand() {
        registerListCommands(commandList);
    }

    private void initCetusTimer() {
        new Cetus().checkCetusNight();
    }

    private void initLogoUrl() {
        try {
            String alliance = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "info" + File.separator + "Alliance.json")));
            JSONObject allianceJson = new JSONObject(alliance);

            logoUrlAlliance = allianceJson.getJSONObject("infos").getString("logoUrlAlliance");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void InitMain() {
        initListCommand();
        initServeur();
        initAudioManager();
        initMusicManager();
        initFunctionality();
        initHelpList();
        initCommand();
        initCetusTimer();
        initLogoUrl();
    }
}
