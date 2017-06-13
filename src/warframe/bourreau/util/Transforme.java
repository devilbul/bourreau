package warframe.bourreau.util;

class Transforme {

    static String TransformeInfluence(String args) {
        String res;
        double influence = Double.parseDouble(args);
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.##");

        influence = (influence - 1) * 100;
        res = df.format(influence);
        res = String.valueOf(res) + "%";

        return res;
    }
}
