import java.util.Random;

public class Util {
//    private static final String chars = " !#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_abcdefghijklmnopqrstuvwxyz{|}~";
    private static final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public static char getRandomChar() {
        return chars.charAt(new Random().nextInt(chars.length()));
    }
}
