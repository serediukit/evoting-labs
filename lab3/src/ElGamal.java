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

        BigInteger a = g.modPow(k, p);

        BigInteger b = plaintext.multiply(y.modPow(k, p)).mod(p);

        return new BigInteger[]{a, b};
    }

    public BigInteger decrypt(BigInteger[] ciphertext) {
        BigInteger a = ciphertext[0];
        BigInteger b = ciphertext[1];

        BigInteger s = a.modPow(x, p);
        BigInteger sInverse = s.modInverse(p);

        return b.multiply(sInverse).mod(p);
    }
}
