package warframe.bourreau.api;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static warframe.bourreau.parser.TimeParser.ParseSecToTime;
import static warframe.bourreau.parser.TimeParser.ParseTimeToSec;
import static warframe.bourreau.util.Recup.recupPseudo;
import static warframe.bourreau.util.Recup.recupString;
import static warframe.bourreau.util.urlReadJson.readAll;

public class wfRaidAPI {

    public static void RaidStat(MessageReceivedEvent event) {
        String user;
        String urlApi = "https://api.trials.wf/api/player/pc/";
        String urlSite = "https://trials.wf/player/?user=";

        if (event.getMessage().getContent().contains(" ")) {
            user = recupPseudo(event.getMessage().getContent());
            urlApi += user + "/completed";
            urlSite += user;

            TraiteRaidStat(event, user, urlApi, urlSite);
        }
        else {
            user = event.getAuthor().getName();
            urlApi += user + "/completed";
            urlSite += user;

            TraiteRaidStat(event, user, urlApi, urlSite);
        }
    }

    public static void RaidStatDetails(MessageReceivedEvent event) {
        String user;
        String urlApi = "https://api.trials.wf/api/player/pc/";
        String urlSite = "https://trials.wf/player/?user=";

        if (recupString(event.getMessage().getContent()).contains(" ")) {
            user = recupPseudo(recupString(event.getMessage().getContent()));
            urlApi += user + "/completed";
            urlSite += user;

            TraiteRaidStatDetail(event, user, urlApi, urlSite);
        }
        else {
            user = event.getAuthor().getName();
            urlApi += user + "/completed";
            urlSite += user;

            TraiteRaidStatDetail(event, user, urlApi, urlSite);
        }
    }

    private static void TraiteRaidStat(MessageReceivedEvent event, String user, String urlApi, String urlSite) {
        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray raidJson = new JSONArray(jsonText);
            EmbedBuilder statRaid = new EmbedBuilder();

            if (raidJson.length() >= 1) {
                int nbRaid = raidJson.length();
                int nbRaidCompleted = 0;
                float clearRate;
                long bestTime = 7200;
                long sumTime = 0;

                for (int i=0; i<nbRaid; i++) {
                    if(raidJson.getJSONObject(i).getString("objective").equals("VICTORY")) {
                        nbRaidCompleted++;
                        sumTime += ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));
                    }

                    if(ParseTimeToSec(raidJson.getJSONObject(i).getString("time")) < bestTime
                            && raidJson.getJSONObject(i).getString("objective").equals("VICTORY"))
                        bestTime = ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));
                }

                clearRate = (float) (nbRaidCompleted)/ (float) (nbRaid);

                statRaid.setTitle("Stats de Raid de " + user + " :", urlSite);
                statRaid.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/e/e3/Arcane_Energize_160.png/revision/latest?cb=20151102150026");
                statRaid.addField("**__Tous raids confondus :__**", "**raid réussi :** " + nbRaidCompleted + " / " + nbRaid +
                                "\n**pourcentage de réussite :** " + (int) (clearRate * 10000)/100.0 + "%" +
                                "\n**meilleur temps :** " + ParseSecToTime(bestTime) +
                                "\n**temps moyen :** " + ParseSecToTime(sumTime / nbRaidCompleted)
                        ,false);
                statRaid.addField("pour plus de details,", "tapez : **__!raid detail " + user + "__**", false);
                statRaid.setColor(new Color(100, 100, 100));
                statRaid.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : trails.wf", "http://i.imgur.com/BUkD1OV.png");
                event.getTextChannel().sendMessage(statRaid.build()).queue();
            }
            else
                event.getTextChannel().sendMessage("Pas de raid, pour le pseudo saisi.").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void TraiteRaidStatDetail(MessageReceivedEvent event, String user, String urlApi, String urlSite) {
        try (InputStream is = new URL(urlApi).openStream()) {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONArray raidJson = new JSONArray(jsonText);
            EmbedBuilder statRaid = new EmbedBuilder();

            if (raidJson.length() >= 1) {
                int nbRaid = raidJson.length();

                //LoR
                int nbRaidLoR = 0;
                int nbRaidLoRCompleted = 0;
                float clearRateLoR;
                long bestTimeLoR = 7200;
                long sumTimeLoR = 0;

                //LoRNM
                int nbRaidLoRNM = 0;
                int nbRaidLoRNMCompleted = 0;
                float clearRateLoRNM;
                long bestTimeLoRNM = 7200;
                long sumTimeLoRNM = 0;

                //JV
                int nbRaidJV = 0;
                int nbRaidJVCompleted = 0;
                float clearRateJV;
                long bestTimeJV = 7200;
                long sumTimeJV = 0;

                for (int i=0; i<nbRaid; i++) {
                    switch (raidJson.getJSONObject(i).getString("type")) {
                        case "lor":
                            nbRaidLoR++;
                            if(raidJson.getJSONObject(i).getString("objective").equals("VICTORY")) {
                                nbRaidLoRCompleted++;
                                sumTimeLoR += ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));
                            }

                            if(ParseTimeToSec(raidJson.getJSONObject(i).getString("time")) < bestTimeLoR
                                    && raidJson.getJSONObject(i).getString("objective").equals("VICTORY"))
                                bestTimeLoR = ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));

                            break;
                        case "lornm":
                            nbRaidLoRNM++;

                            if(raidJson.getJSONObject(i).getString("objective").equals("VICTORY")) {
                                nbRaidLoRNMCompleted++;
                                sumTimeLoRNM += ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));
                            }

                            if(ParseTimeToSec(raidJson.getJSONObject(i).getString("time")) < bestTimeLoRNM
                                    && raidJson.getJSONObject(i).getString("objective").equals("VICTORY"))
                                bestTimeLoRNM = ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));

                            break;
                        case "jv":
                            nbRaidJV++;

                            if(raidJson.getJSONObject(i).getString("objective").equals("VICTORY")) {
                                nbRaidJVCompleted++;
                                sumTimeJV += ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));
                            }

                            if(ParseTimeToSec(raidJson.getJSONObject(i).getString("time")) < bestTimeJV
                                    && raidJson.getJSONObject(i).getString("objective").equals("VICTORY"))
                                bestTimeJV = ParseTimeToSec(raidJson.getJSONObject(i).getString("time"));

                            break;
                    }
                }

                if (nbRaidLoR != 0) clearRateLoR = (float) (nbRaidLoRCompleted)/ (float) (nbRaidLoR);
                else clearRateLoR = 0;
                if (nbRaidLoRNM != 0) clearRateLoRNM = (float) (nbRaidLoRNMCompleted)/ (float) (nbRaidLoRNM);
                else clearRateLoRNM = 0;
                if (nbRaidJV != 0) clearRateJV = (float) (nbRaidJVCompleted)/ (float) (nbRaidJV);
                else clearRateJV = 0;

                statRaid.setTitle("Stats de Raid de " + user + " :", urlSite);
                statRaid.setThumbnail("https://vignette3.wikia.nocookie.net/warframe/images/e/e3/Arcane_Energize_160.png/revision/latest?cb=20151102150026");

                if (nbRaidLoRCompleted != 0)
                    statRaid.addField("__Le Droit de Rétribution :__", "**raid réussi :** " + nbRaidLoRCompleted + " / " + nbRaidLoR +
                                    "\n**pourcentage de réussite :** " + (int) (clearRateLoR * 10000)/100.0 + "%" +
                                    "\n**meilleur temps :** " + ParseSecToTime(bestTimeLoR) +
                                    "\n**temps moyen :** " + ParseSecToTime(sumTimeLoR / nbRaidLoRCompleted)
                            ,false);
                else
                    statRaid.addField("__Le Droit de Rétribution :__", "**raid réussi :** 0 /  0 \n**pourcentage de réussite :** 0%**meilleur temps :** 00:00" +
                            "\n**temps moyen :** 00:00",false);

                if (nbRaidLoRNMCompleted != 0)
                    statRaid.addField("__Le Droit de Rétribution (Cauchemar) :__", "**raid réussi :** " + nbRaidLoRNMCompleted + " / " + nbRaidLoRNM +
                                    "\n**pourcentage de réussite :** " + (int) (clearRateLoRNM * 10000)/100.0 + "%" +
                                    "\n**meilleur temps :** " + ParseSecToTime(bestTimeLoRNM) +
                                    "\n**temps moyen :** " + ParseSecToTime(sumTimeLoRNM / nbRaidLoRNMCompleted)
                            ,false);
                else
                    statRaid.addField("__Le Droit de Rétribution (Cauchemar :__", "**raid réussi :** 0 /  0 \n**pourcentage de réussite :** 0%\n**meilleur temps :** 00:00" +
                            "\n**temps moyen :** 00:00",false);

                if (nbRaidJVCompleted != 0)
                    statRaid.addField("__Le Verdict de Jordas :__", "**raid réussi :** " + nbRaidJVCompleted + " / " + nbRaidJV +
                                    "\n**pourcentage de réussite :** " + (int) (clearRateJV * 10000)/100.0 + "%" +
                                    "\n**meilleur temps :** " + ParseSecToTime(bestTimeJV) +
                                    "\n**temps moyen :** " + ParseSecToTime(sumTimeJV / nbRaidJVCompleted)
                            ,false);
                else
                    statRaid.addField("__Le Verdict de Jordas :__", "**raid réussi :** 0 /  0 \n**pourcentage de réussite :** 0%\n**meilleur temps :** 00:00" +
                            "\n**temps moyen :** 00:00",false);

                statRaid.setColor(new Color(100, 100, 100));
                statRaid.setFooter(new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss").format(new Date(Instant.now().toEpochMilli())) + " | Source : trails.wf", "http://i.imgur.com/BUkD1OV.png");
                event.getTextChannel().sendMessage(statRaid.build()).queue();
            }
            else
                event.getTextChannel().sendMessage("Pas de raid, pour le pseudo saisi.").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
