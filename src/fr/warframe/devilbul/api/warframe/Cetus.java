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
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static fr.warframe.devilbul.Init.logoUrlAlliance;
import static fr.warframe.devilbul.parser.TimeParser.parseEpochToTime;
import static fr.warframe.devilbul.utils.UrlReadJson.readAll;
import static java.util.concurrent.TimeUnit.*;

public class Cetus {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final String idServeur = "298503533777387530";

    public void checkCetusNight() {
        final Runnable beeper = () -> {
            String urlApi = "http://content.warframe.com/dynamic/worldState.php";
            String idRoleEidolon;
            String idTextChannelEidolon;

            try (InputStream is = new URL(urlApi).openStream()) {
                String configRole = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configRole.json")));
                String configTextChannel = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "config" + File.separator + "configTextChannel.json")));
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject warframeJson = new JSONObject(jsonText);
                idRoleEidolon = new JSONObject(configRole).getJSONObject("roles").getJSONObject(idServeur).getJSONObject("roles").getJSONObject("eidolon").getString("idRole");
                idTextChannelEidolon = new JSONObject(configTextChannel).getJSONObject("textChannels").getJSONObject(idServeur).getJSONObject("textChannels").getJSONObject("eidolon").getString("idTextChannel");
                int index = warframeJson.getJSONArray("SyndicateMissions").length() - 1;
                long nextNight = Long.valueOf(warframeJson.getJSONArray("SyndicateMissions").getJSONObject(index).getJSONObject("Expiry").getJSONObject("$date").getString("$numberLong")) - Instant.now().toEpochMilli();

                String pathJson = new String(Files.readAllBytes(Paths.get("resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json")));
                JSONObject eidolonJson = new JSONObject(pathJson);
                long timePing = eidolonJson.getLong("time");
                Message message, mention;
                EmbedBuilder embed = new EmbedBuilder();

                embed.setTitle("Cetus : Eidolon", "http://warframe.wikia.com/wiki/Sentient");
                embed.setThumbnail("https://vignette.wikia.nocookie.net/warframe/images/5/5a/SentientFactionIcon_b.png");
                embed.setImage("https://vignette.wikia.nocookie.net/warframe/images/6/6c/TeralystRain.png/revision/latest/scale-to-width-down/100?cb=20180210123535");
                embed.setColor(new Color(0, 0, 0));
                embed.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())), logoUrlAlliance);

                if (nextNight < 3000001 && nextNight > 59999) {
                    if (idRoleEidolon != null)
                        embed.addField("Temps restant", parseEpochToTime(nextNight + 3000000) + "\n" + Bourreau.jda.getGuildById(idServeur).getRoleById(idRoleEidolon).getAsMention(), false);
                    else
                        embed.addField("Temps restant", parseEpochToTime(nextNight + 3000000), false);

                    embed.setDescription("Fin de la nuit en cours");

                    if (eidolonJson.getString("id_message_embed").isEmpty()) {
                        FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                        message = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(embed.build()).complete();

                        file.write(eidolonJson.put("id_message_embed", message.getId()).toString(3));
                        file.flush();
                        file.close();
                    } else
                        Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_embed")).complete().editMessage(embed.build()).queue();

                } else if (nextNight > 2999999) {
                    if (idRoleEidolon != null)
                        embed.addField("Temps restant", parseEpochToTime(nextNight) + "\n" + Bourreau.jda.getGuildById(idServeur).getRoleById(idRoleEidolon).getAsMention(), false);
                    else
                        embed.addField("Temps restant", parseEpochToTime(nextNight), false);

                    embed.setDescription("Début de la prochaine nuit");

                    System.out.println(nextNight);
                    System.out.println(3000001 + timePing);
                    System.out.println(2999999 + timePing);
                    System.out.println(nextNight < (3060001 + timePing));
                    System.out.println(nextNight > (2999999 + timePing));


                    if (nextNight < 3060001 + timePing) {
                        MessageBuilder msg = new MessageBuilder();
                        msg.append(Bourreau.jda.getGuildById(idServeur).getRoleById(idRoleEidolon).getAsMention());

                        if (nextNight > 2999999 + timePing) {
                            if (idTextChannelEidolon != null && idRoleEidolon != null && eidolonJson.getBoolean("ping")) {
                                FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                                msg.append(" Eidolon dans ").append(parseEpochToTime(nextNight));
                                mention = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(msg.build()).complete();
                                mention.delete().completeAfter(timePing, MILLISECONDS);

                                file.write(eidolonJson.put("id_message_embed", mention.getId()).toString(3));
                                file.flush();
                                file.close();
                            }
                        } else if (nextNight < 3060001) {
                            msg.append(" Eidolon en cours");

                            if (eidolonJson.getString("id_message_mention").isEmpty()) {
                                mention = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(msg.build()).complete();
                                mention.delete().completeAfter(3000000, MILLISECONDS);
                            } else {
                                mention = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_embed")).complete().editMessage(msg.build()).complete();
                                mention.delete().completeAfter(3000000, MILLISECONDS);
                            }
                        }
                    }

                    if (eidolonJson.getString("id_message_embed").isEmpty()) {
                        message = Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).sendMessage(embed.build()).complete();

                        if (message != null) {
                            FileWriter file = new FileWriter(System.getProperty("user.dir") + File.separator + "resources" + File.separator + "eidolon" + File.separator + "eidolon_tracker.json");

                            file.write(eidolonJson.put("id_message_embed", message.getId()).toString(3));
                            file.flush();
                            file.close();
                        }
                    } else {
                        Bourreau.jda.getGuildById(idServeur).getTextChannelById(idTextChannelEidolon).getMessageById(eidolonJson.getString("id_message_embed")).complete().editMessage(embed.build()).queue();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 0, 60, SECONDS);
        scheduler.schedule(() -> {
            beeperHandle.cancel(true);
        }, 31, DAYS);
    }
}
