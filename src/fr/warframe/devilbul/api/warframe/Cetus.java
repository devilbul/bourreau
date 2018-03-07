package fr.warframe.devilbul.api.warframe;

import fr.warframe.devilbul.Bourreau;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.parser.TimeParser.parseEpochToTime;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;
import static java.util.concurrent.TimeUnit.*;

public class Cetus {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void checkCetusNight() {

        final Runnable refresh = () -> {
            String urlApi = "http://content.warframe.com/dynamic/worldState.php";
            String idTextChannelEidolon;

            try (InputStream is = new URL(urlApi).openStream()) {
                String pathJson = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json")));
                JSONObject eidolonJson = new JSONObject(pathJson);
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject warframeJson = new JSONObject(jsonText);
                String idServeur = eidolonJson.getString("id_serveur");
                idTextChannelEidolon = eidolonJson.getString("id_text_channel");
                int index = warframeJson.getJSONArray("SyndicateMissions").length() - 1;
                long timeNight = Long.valueOf(warframeJson.getJSONArray("SyndicateMissions").getJSONObject(index).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong")) + 3600000;
                if (timeNight - (Instant.now().toEpochMilli() + 3600000) > 9000000)
                    timeNight = Long.valueOf(warframeJson.getJSONArray("SyndicateMissions").getJSONObject(0).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong")) + 3600000;
                long nextNight = timeNight - (Instant.now().toEpochMilli() + 3600000);
                long timePing = eidolonJson.getLong("time");
                Message message, mention;
                EmbedBuilder embed = new EmbedBuilder();

                embed.setTitle("Cetus : Eidolon", "http://warframe.wikia.com/wiki/Sentient");
                embed.setImage("https://vignette.wikia.nocookie.net/warframe/images/6/6c/TeralystRain.png/revision/latest/scale-to-width-down/100?cb=20180210123535");
                embed.setColor(new Color(0, 0, 0));
                embed.setFooter("actualisé à " + new SimpleDateFormat("HH:mm").format(Instant.now().toEpochMilli() + 3600000), logoUrlAlliance);

                if (nextNight <= 3000000) {
                    embed.setThumbnail("https://vignette.wikia.nocookie.net/warframe/images/4/4c/Conclave_Moon.png");
                    embed.setDescription("Nuit en cours");
                    embed.addField("Temps restant", parseEpochToTime(nextNight + 3000000), false);
                    embed.addField("Début du jour", "à " + new SimpleDateFormat("HH:mm").format(timeNight), false);

                    if (eidolonJson.getString("id_message_embed").isEmpty()) {
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                        message = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(embed.build()).complete();

                        file.write(eidolonJson.put("id_message_embed", message.getId()).toString(3));
                        file.flush();
                        file.close();
                    } else
                        Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_embed")).complete().editMessage(embed.build()).queue();

                    if (!eidolonJson.getString("id_message_notif").isEmpty())
                        Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_notif")).complete().delete().queue();
                }

                if (nextNight > 3000000) {
                    embed.setThumbnail("https://vignette.wikia.nocookie.net/warframe/images/c/cc/Conclave_Sun.png");
                    embed.setDescription("Jour en cours");
                    embed.addField("Temps restant", parseEpochToTime(nextNight), false);
                    embed.addField("Début de la nuit", "à " + new SimpleDateFormat("HH:mm").format(timeNight - 3000000), false);

                    if (eidolonJson.getString("id_message_embed").isEmpty()) {
                        message = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(embed.build()).complete();

                        if (message != null) {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                            file.write(eidolonJson.put("id_message_embed", message.getId()).toString(3));
                            file.flush();
                            file.close();
                        }
                    } else
                        Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_embed")).complete().editMessage(embed.build()).queue();

                    if (idTextChannelEidolon != null && eidolonJson.getBoolean("mention")) {
                        if (nextNight < 3060001 + timePing && nextNight > 2999999 + timePing) {
                            MessageBuilder msg = new MessageBuilder();
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                            msg.append("Il va faire tout noir ! (à ").append(new SimpleDateFormat("HH:mm").format(timeNight - 3000000)).append(")");
                            mention = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(msg.build()).complete();

                            file.write(eidolonJson.put("id_message_notif", mention.getId()).toString(3));
                            file.flush();
                            file.close();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(refresh, 0, 60, SECONDS);
        scheduler.schedule(() -> {
            beeperHandle.cancel(true);
        }, 31, DAYS);
    }
}
