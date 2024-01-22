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
}
