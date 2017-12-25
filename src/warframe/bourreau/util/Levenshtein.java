package warframe.bourreau.util;

public class Levenshtein {

    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static String compareCommande(String str, Object[] obj) {
        String commandeProche;
        int invariant = 1000;
        int indice = -1;

        for (int i=0; i<obj.length; i++) {
            if (distance(str, obj[i].toString()) < invariant) {
                invariant = distance(str, obj[i].toString());
                indice = i;
            }
        }

        commandeProche = obj[indice].toString();

        return "peut-être voulez vous écrire : " + commandeProche;
    }
}
