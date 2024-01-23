import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {
    public BigInteger[] encrypt(BigInteger plaintext, PublicKey publicKey) {
        BigInteger g = publicKey.g;
        BigInteger p = publicKey.p;
        BigInteger y = publicKey.y;

        BigInteger k = new BigInteger(512, new SecureRandom());

        BigInteger a = g.modPow(k, p);

        BigInteger b = plaintext.multiply(y.modPow(k, p)).mod(p);

        return new BigInteger[]{a, b};
    }

    public BigInteger decrypt(BigInteger[] ciphertext, PrivateKey privateKey) {
        BigInteger p = privateKey.elp;
        BigInteger x = privateKey.x;

        BigInteger a = ciphertext[0];
        BigInteger b = ciphertext[1];

        BigInteger s = a.modPow(x, p);
        BigInteger sInverse = s.modInverse(p);

        return b.multiply(sInverse).mod(p);
    }
}
