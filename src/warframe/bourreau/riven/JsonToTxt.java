package warframe.bourreau.riven;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonToTxt {

    private static String adresseSortiePrimary = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_primary.txt";
    private static String adresseSortieMelee = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_melee.txt";
    private static String adresseSortieSecondary = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_secondary.txt";
    private static String adresseSortieShotgun = System.getProperty("user.dir") + File.separator + "res" + File.separator + "input" + File.separator + "riven_shotgun.txt";

    public static boolean traitement() { return transformeJsonToTxt(); }

    private static boolean transformeJsonToTxt() {
        try {
            String adresse = new String(Files.readAllBytes(Paths.get("res" + File.separator + "output" + File.separator + "riven.json")));
            FileWriter filePrimary = new FileWriter(adresseSortiePrimary);
            FileWriter fileMelee = new FileWriter(adresseSortieMelee);
            FileWriter fileSecondary = new FileWriter(adresseSortieSecondary);
            FileWriter fileShotgun = new FileWriter(adresseSortieShotgun);
            JSONObject rivenJson = new JSONObject(adresse);
            JSONArray keys = rivenJson.names();
            JSONArray rivenCategoryJson;
            String key;
            double disposition;
            String arme;

            for (int i=0; i < keys.length(); i++) {
                key = keys.getString(i);
                rivenCategoryJson = new JSONObject(adresse).getJSONObject(key).getJSONArray("Rivens");

                for (int j = 0; j < rivenCategoryJson.length(); j++) {
                    disposition = rivenCategoryJson.getJSONObject(j).getDouble("disposition");
                    arme = rivenCategoryJson.getJSONObject(j).getString("name");

                    disposition = (disposition * 100) - 100;

                    switch (key) {
                        case "Rifle":
                            filePrimary.write(((int) disposition) + " " + arme.toUpperCase() + "\n");
                            break;
                        case "Melee":
                            fileMelee.write(((int) disposition) + " " + arme.toUpperCase() + "\n");
                            break;
                        case "Pistol":
                            fileSecondary.write(((int) disposition) + " " + arme.toUpperCase() + "\n");
                            break;
                        case "Shotgun":
                            fileShotgun.write(((int) disposition) + " " + arme.toUpperCase() + "\n");
                            break;
                    }
                }
            }

            filePrimary.flush();
            filePrimary.close();
            fileMelee.flush();
            fileMelee.close();
            fileSecondary.flush();
            fileSecondary.close();
            fileShotgun.flush();
            fileShotgun.close();

            return true;
        }
        catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
