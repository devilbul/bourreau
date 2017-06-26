package warframe.bourreau.timer;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TimerTask;

import static warframe.bourreau.InitID.raidsID;
import static warframe.bourreau.util.DateHeure.GiveDate;
import static warframe.bourreau.util.DateHeure.GiveHeure;

public class RaidTimer extends TimerTask {
    private JDA jda;

    @Override
    public void run() {
        try {
            String adresseRaid = System.getProperty("user.dir") + File.separator + "raid" + File.separator + "raid_du_" + GiveDate() + ".json";
            if (!new File(adresseRaid).exists()) {
                JSONObject raidJson = new JSONObject();
                JSONObject dateJson = new JSONObject();
                Message recensement = jda.getGuilds().get(0).getTextChannelById(raidsID).getMessageById("314307308756795392").complete();
                MessageBuilder messageRaid = new MessageBuilder();

                dateJson.put("date", GiveDate());
                dateJson.put("heure", GiveHeure());

                raidJson.put("date", dateJson);

                messageRaid.append("Début recensement des présences pour les raids du ").append(GiveDate());

                recensement.editMessage(messageRaid.build()).queue();

                FileWriter file = new FileWriter(adresseRaid);

                file.write(raidJson.toString());
                file.flush();
                file.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    RaidTimer(JDA jda) { this.jda = jda; }
}
