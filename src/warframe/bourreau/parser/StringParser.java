package warframe.bourreau.parser;

public class StringParser {

    public static String ParseInsertSpace(String str) {
        String res = String.valueOf(str.charAt(0));

        for (int i=1; i<str.length(); i++) {
            if (SearchMajAlphabet(str.charAt(i)))
                res += " " + String.valueOf(str.charAt(i));
            else
                res += String.valueOf(str.charAt(i));
        }

        return res;
    }

    private static Boolean SearchMajAlphabet(Character ch) {
        String alphabetMaj = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i=0; i<alphabetMaj.length(); i++) {
            if (ch.compareTo(alphabetMaj.charAt(i)) == 0)
                return true;
        }

        return false;
    }
}
