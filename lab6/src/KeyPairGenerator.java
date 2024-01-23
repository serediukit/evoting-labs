import java.util.Random;

public class KeyPairGenerator {
    public static KeyPair generateKeyPair() {
        var random = new Random();

        var privateBytes = new byte[16];
        var publicBytes = new byte[16];

        random.nextBytes(privateBytes);
        random.nextBytes(publicBytes);

        return new KeyPair(new PrivateKey(privateBytes), new PublicKey(publicBytes));
    }
}
