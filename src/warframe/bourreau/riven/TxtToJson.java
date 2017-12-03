package warframe.bourreau.riven;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TxtToJson {

    private static String origine = "RivenMods.txt";
    public static String sortie = "RivenMods.json";
    private static String adresse = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + origine;
    private static String adresseSortie = System.getProperty("user.dir") + File.separator + "res" + File.separator + "info" + File.separator + sortie;

    public static boolean Traitement() {
        if (TransformeTxtToJson())
            if (DeleteBlankObjet())
                return true;

        return false;
    }

    private static boolean TransformeTxtToJson() {
        try {
            Scanner scanner = new Scanner(new File(adresse));
            FileWriter rivenJsonFile = new FileWriter(adresseSortie);
            JSONObject rivenJson = new JSONObject();
            JSONObject rivenTypeJson = null;
            JSONObject infoJson = null;
            JSONObject challengeJson = null;
            JSONArray upgradesJson = null;
            JSONObject itemJson = null;
            JSONObject complicationJson = null;
            JSONObject upgradeJson = null;
            String rivenActuel = "";
            String complicationActuel = "";
            String line;
            int jsonActuel = 0;
            int INFO = 1;
            int CHALLENGES = 2;
            int UPGRADES = 3;
            int ITEM = 4;
            int COMLPICATION = 5;
            int UPGRADE = 6;
            int CHALLENGE = 7;

            while (scanner.hasNext()) {
                line = scanner.nextLine().toLowerCase();

                if (line.contains("riven mod]")) {
                    if (!line.contains("rifle")) {
                        rivenTypeJson.put("info", infoJson);
                        rivenTypeJson.put("challenge", challengeJson);
                        rivenTypeJson.put("upgrades", upgradesJson);
                        rivenTypeJson.put("item", itemJson);

                        rivenJson.put(rivenActuel, rivenTypeJson);
                    }

                    rivenActuel = line.split(" ")[0].substring(1);
                    jsonActuel = INFO;

                    rivenTypeJson = new JSONObject();
                    infoJson = new JSONObject();
                    challengeJson = new JSONObject();
                    upgradesJson = new JSONArray();
                    itemJson = new JSONObject();
                    complicationJson = new JSONObject();
                    upgradeJson = new JSONObject();
                }

                if (line.contains("item compatibility:"))
                    jsonActuel = ITEM;
                else if (line.contains("upgrade entries:"))
                    jsonActuel = UPGRADES;
                else if (line.contains("available challenges:"))
                    jsonActuel = CHALLENGES;
                else if (line.contains("complication chance:"))
                    complicationJson.put(line.split(":")[0].substring(3),line.split(": ")[1]);
                else if (line.contains("-") && line.contains("[") && line.contains("]")) {
                    challengeJson.put(complicationActuel,complicationJson);
                    jsonActuel = CHALLENGE;
                    complicationActuel = line.split("- ")[1];
                    complicationJson = new JSONObject();
                } else if (line.contains("-") && jsonActuel == 5) {
                    challengeJson.put(complicationActuel,complicationJson);
                    jsonActuel = CHALLENGE;
                    complicationActuel = line.split("- ")[1];
                    complicationJson = new JSONObject();
                } else if (line.contains("complications:"))
                    jsonActuel = COMLPICATION;
                else if (line.contains("- upgrade:")) {
                    upgradesJson.put(upgradeJson);
                    jsonActuel = UPGRADE;
                    upgradeJson = new JSONObject();
                }

                if (line.length() != 0) {
                    if (jsonActuel == ITEM) {
                        if (!line.contains("item compatibility:"))
                            itemJson.put(line.split(",")[0].substring(3),
                                    new JSONArray()
                                            .put(line.split(",")[1].substring(1))
                                            .put(line.split(":")[1].substring(1)));
                    } else if (jsonActuel == INFO) {
                        if (!line.contains("riven mod]") && line.contains(":")) {
                            if (line.contains("polarities:")) {
                                infoJson.put(line.split(":")[0],
                                        new JSONArray()
                                                .put(line.split(": ")[1].split(" ")[0])
                                                .put(line.split(": ")[1].split(" ")[1])
                                                .put(line.split(": ")[1].split(" ")[2]));
                            } else if (line.contains("attenuation:") && line.contains("number of buffs")) {
                                infoJson.put(line.split(":")[0],
                                        new JSONArray()
                                                .put(line.split(": ")[1].split(", ")[0])
                                                .put(line.split(": ")[1].split(", ")[1])
                                                .put(line.split(": ")[1].split(", ")[2])
                                                .put(line.split(": ")[1].split(", ")[3])
                                                .put(line.split(": ")[1].split(", ")[4])
                                                .put(line.split(": ")[1].split(", ")[5]));
                            } else if (line.contains("fusion limit range:"))
                                infoJson.put(line.split(":")[0], line.split(": ")[1].split("-")[0]);
                            else if (line.contains("number of curses:") || line.contains("number of buffs:"))
                                infoJson.put(line.split(":")[0],
                                        new JSONArray()
                                                .put(line.split(": ")[1].split("-")[0])
                                                .put(line.split(": ")[1].split("-")[1]));
                        }
                    } else if (jsonActuel == COMLPICATION) {
                        if (!line.contains("complications:")) {
                            if (line.contains("%"))
                                complicationJson.put(line.split(":")[0].substring(3),line.split(": ")[1]);
                            else if (line.contains("(") && line.contains(")"))
                                complicationJson.put(line.split(" [(]")[0].substring(6),
                                        new JSONObject()
                                                .put(line.split(" [(]")[1].split(":")[0],line.split(": ")[1].split(",")[0])
                                                .put(line.split(", ")[1].split(" ")[0],line.split(", ")[1].split(" ")[1].split("[)]")[0]));
                        }
                    } else if (jsonActuel == UPGRADE) {
                        String str;
                        String lineSave = line;
                        if (line.replace(":",":$").split(":")[1].length() > 1) {
                            if (lineSave.split(":")[1].contains(" "))
                                str = lineSave.split(":")[1].substring(1);
                            else
                                str = lineSave.split(":")[1];

                            upgradeJson.put(line.split(":")[0].substring(3),str);
                        }
                        else
                            upgradeJson.put(line.split(":")[0].substring(3),"");
                    }
                }

                challengeJson.put(complicationActuel,complicationJson);

                rivenTypeJson.put("info", infoJson);
                rivenTypeJson.put("challenge", challengeJson);
                rivenTypeJson.put("upgrades", upgradesJson);
                rivenTypeJson.put("item", itemJson);

                rivenJson.put(rivenActuel, rivenTypeJson);
            }

            rivenJsonFile.write(rivenJson.toString());
            rivenJsonFile.flush();
            rivenJsonFile.close();

            scanner.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean DeleteBlankObjet()  {
        try {
            String riven = new String(Files.readAllBytes(Paths.get("res" + File.separator + "info" + File.separator + sortie)));
            JSONObject rivenJson = new JSONObject(riven);
            FileWriter rivenJsonFile = new FileWriter(adresseSortie);

            for (Object name : rivenJson.names()) {
                for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("challenge").names().length(); i++) {
                    if (rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("challenge").names().get(i).equals(""))
                        rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("challenge").remove(String.valueOf(rivenJson.getJSONObject(String.valueOf(name)).getJSONObject("challenge").names().get(i)));
                }

                for (int i = 0; i < rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").length(); i++) {
                    if (rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").getJSONObject(i).length() == 0)
                        rivenJson.getJSONObject(String.valueOf(name)).getJSONArray("upgrades").remove(i);
                }
            }

            rivenJsonFile.write(rivenJson.toString());
            rivenJsonFile.flush();
            rivenJsonFile.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
