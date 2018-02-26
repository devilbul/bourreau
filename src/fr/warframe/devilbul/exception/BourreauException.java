package fr.warframe.devilbul.exception;

public class BourreauException extends Exception {

    public BourreauException(String message) {
        super(message);
        System.out.println(message);
    }
}
