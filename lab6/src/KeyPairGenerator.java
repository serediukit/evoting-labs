import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class KeyPairGenerator {
    public static KeyPair generateKeyPair() {
        BigInteger[] pq = generatePrime();
        BigInteger n = pq[0].multiply(pq[1]);

        BigInteger[] eg = generateElGamal();

        return new KeyPair(new PrivateKey(pq[0], pq[1], eg[3], eg[0]), new PublicKey(n, eg[0], eg[1], eg[2]));
    }

    private static BigInteger[] generatePrime() {
        var random = new Random();

        var p = BigInteger.probablePrime(64, random);
        var q = BigInteger.probablePrime(64, random);

        while (p.compareTo(q) == 0) {
            p = BigInteger.probablePrime(64, random);
            q = BigInteger.probablePrime(64, random);
        }

        return new BigInteger[]{p, q};
    }

    private static BigInteger[] generateElGamal() {
        BigInteger p = BigInteger.probablePrime(512, new SecureRandom());
        BigInteger g = new BigInteger("2");

        BigInteger x = new BigInteger(512, new SecureRandom());

        BigInteger y = g.modPow(x, p);
        return new BigInteger[]{p, g, y, x};
    }
}
