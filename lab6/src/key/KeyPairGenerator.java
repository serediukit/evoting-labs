package key;

import key.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class KeyPairGenerator {
    public static BBSKeyPair generateBBSKeyPair() {
        BigInteger[] pq = generatePrime();
        BigInteger n = pq[0].multiply(pq[1]);
        return new BBSKeyPair(new BBSPrivateKey(pq[0], pq[1]), new BBSPublicKey(n));
    }

    public static ElGamalKeyPair generateElGamalKeyPair() {
        BigInteger[] elGamal = generateElGamal();
        return new ElGamalKeyPair(new ElGamalPrivateKey(elGamal[3], elGamal[0]), new ElGamalPublicKey(elGamal[0],  elGamal[1], elGamal[2]));
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
