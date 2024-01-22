import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Random;

public class Generator {
    public static int generateNonPrimeId() {
        Random rand = new Random();
        int primeId;
        do {
            primeId = rand.nextInt(10000);
        } while (Util.isPrime(primeId));
        return primeId;
    }

    public static KeyPair generateDSAKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
