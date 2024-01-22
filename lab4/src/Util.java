import java.util.Random;

public class Util {
    public static char getRandomChar() {
        String chars = " !#$%&()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[]^_abcdefghijklmnopqrstuvwxyz{|}~";
        return chars.charAt(new Random().nextInt(chars.length()));
    }
}
