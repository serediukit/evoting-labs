import key.ElGamalPrivateKey;
import key.ElGamalPublicKey;

import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {
    public static BigInteger[] encrypt(BigInteger plaintext, ElGamalPublicKey publicKey) {
        BigInteger g = publicKey.g;
        BigInteger p = publicKey.p;
        BigInteger y = publicKey.y;

        BigInteger k = new BigInteger(512, new SecureRandom());

        BigInteger a = g.modPow(k, p);

        BigInteger b = plaintext.multiply(y.modPow(k, p)).mod(p);

        return new BigInteger[]{a, b};
    }

    public static BigInteger decrypt(BigInteger[] ciphertext, ElGamalPrivateKey privateKey) {
        BigInteger p = privateKey.p;
        BigInteger x = privateKey.x;

        BigInteger a = ciphertext[0];
        BigInteger b = ciphertext[1];

        BigInteger s = a.modPow(x, p);
        BigInteger sInverse = s.modInverse(p);

        return b.multiply(sInverse).mod(p);
    }
}
