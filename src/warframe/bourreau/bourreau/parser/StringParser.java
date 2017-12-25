package warframe.bourreau.parser;

public class StringParser {

    public static String parseInsertSpace(String str) {
        StringBuilder res = new StringBuilder(String.valueOf(str.charAt(0)));

        for (int i=1; i<str.length(); i++) {
            if (searchMajAlphabet(str.charAt(i)))
                res.append(" ").append(String.valueOf(str.charAt(i)));
            else
                res.append(String.valueOf(str.charAt(i)));
        }

        return res.toString();
    }

    private static Boolean searchMajAlphabet(Character ch) {
        String alphabetMaj = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i=0; i<alphabetMaj.length(); i++) {
            if (ch.compareTo(alphabetMaj.charAt(i)) == 0)
                return true;
        }

        return false;
    }
}
