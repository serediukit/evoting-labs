import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {

    private BigInteger p;
    private BigInteger g;
    private BigInteger x;
    private BigInteger y;

    public ElGamal() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        p = BigInteger.probablePrime(512, new SecureRandom());
        g = new BigInteger("2");

        x = new BigInteger(512, new SecureRandom());

        y = g.modPow(x, p);
    }

    public BigInteger[] encrypt(BigInteger plaintext) {
        BigInteger k = new BigInteger(512, new SecureRandom());

        BigInteger c1 = g.modPow(k, p);

        BigInteger c2 = plaintext.multiply(y.modPow(k, p)).mod(p);

        return new BigInteger[]{c1, c2};
    }

    // Decryption
    public BigInteger decrypt(BigInteger[] ciphertext) {
        BigInteger c1 = ciphertext[0];
        BigInteger c2 = ciphertext[1];

        BigInteger s = c1.modPow(x, p);
        BigInteger sInverse = s.modInverse(p);

        return c2.multiply(sInverse).mod(p);
    }
}
