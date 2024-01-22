import java.util.Random;

public class Util {
    private static final String chars = " !#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_abcdefghijklmnopqrstuvwxyz{|}~";

    public static char getRandomChar() {
        return chars.charAt(new Random().nextInt(chars.length()));
    }

    public static int getCharPosition(char c) {
        return chars.indexOf(c);
    }
}
