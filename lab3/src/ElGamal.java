import java.math.BigInteger;
import java.security.SecureRandom;

public class ElGamal {

    private BigInteger p; // Prime modulus
    private BigInteger g; // Generator
    private BigInteger x; // Private key
    private BigInteger y; // Public key

    public ElGamal() {
        generateKeyPair();
    }

    // Key generation
    private void generateKeyPair() {
        // Choose large prime p and generator g
        p = BigInteger.probablePrime(512, new SecureRandom());
        g = new BigInteger("2"); // For simplicity, using 2 as the generator

        // Choose private key x randomly
        x = new BigInteger(512, new SecureRandom());

        // Calculate public key y = g^x mod p
        y = g.modPow(x, p);
    }

    // Encryption
    public BigInteger[] encrypt(BigInteger plaintext) {
        // Choose random value k
        BigInteger k = new BigInteger(512, new SecureRandom());

        // Calculate c1 = g^k mod p
        BigInteger c1 = g.modPow(k, p);

        // Calculate c2 = (plaintext * y^k) mod p
        BigInteger c2 = plaintext.multiply(y.modPow(k, p)).mod(p);

        // Return the ciphertext as an array [c1, c2]
        return new BigInteger[]{c1, c2};
    }

    // Decryption
    public BigInteger decrypt(BigInteger[] ciphertext) {
        // Extract c1 and c2 from the array
        BigInteger c1 = ciphertext[0];
        BigInteger c2 = ciphertext[1];

        // Calculate s = c1^x mod p
        BigInteger s = c1.modPow(x, p);

        // Calculate the modular inverse of s
        BigInteger sInverse = s.modInverse(p);

        // Calculate the plaintext as (c2 * s^-1) mod p
        return c2.multiply(sInverse).mod(p);
    }
}
