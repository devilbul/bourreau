package warframe.bourreau.util;

import org.json.JSONArray;

public class JSON {

    public static void jsonArrayDeleteDuplicates(JSONArray array) {
        int nb;
        int lastPos;

        for (int i=0; i < array.length(); i++) {
            nb = 0;
            lastPos = i;

            for (int j=0; j < array.length(); j++) {
                if (array.get(i).equals(array.get(j))) {
                    nb++;
                    lastPos = i;
                }
            }

            if (nb > 1)
                array.remove(lastPos);
        }
    }
}
