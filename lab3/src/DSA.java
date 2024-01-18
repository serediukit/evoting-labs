import java.math.BigInteger;
import java.security.SecureRandom;
import java.security.SignatureException;

public class DSA {

    private BigInteger p; // Prime modulus
    private BigInteger q; // Prime divisor of (p-1)
    private BigInteger g; // Generator
    private BigInteger x; // Private key
    private BigInteger y; // Public key

    public DSA() {
        generateKeyPair();
    }

    // Key generation
    private void generateKeyPair() {
        // Choose large prime q and calculate p = 2q + 1
        q = BigInteger.probablePrime(160, new SecureRandom());
        p = q.multiply(new BigInteger("2")).add(BigInteger.ONE);

        // Find a generator g
        do {
            g = new BigInteger(160, new SecureRandom());
        } while (g.modPow(q, p).compareTo(BigInteger.ONE) != 0);

        // Choose private key x randomly
        x = new BigInteger(160, new SecureRandom());

        // Calculate public key y = g^x mod p
        y = g.modPow(x, p);
    }

    // Signature generation
    public BigInteger[] sign(BigInteger message) throws SignatureException {
        BigInteger k;
        BigInteger r;
        BigInteger s;

        do {
            // Choose random value k
            k = new BigInteger(160, new SecureRandom());

            // Calculate r = (g^k mod p) mod q
            r = g.modPow(k, p).mod(q);
        } while (r.equals(BigInteger.ZERO));

        // Calculate s = (k^(-1) * (message + x * r)) mod q
        BigInteger kInverse = k.modInverse(q);
        s = kInverse.multiply(message.add(x.multiply(r))).mod(q);

        // Return the signature as an array [r, s]
        return new BigInteger[]{r, s};
    }

    // Signature verification
    public boolean verify(BigInteger message, BigInteger[] signature) {
        BigInteger r = signature[0];
        BigInteger s = signature[1];

        // Check if 0 < r < q and 0 < s < q
        if (r.compareTo(BigInteger.ZERO) > 0 && r.compareTo(q) < 0 && s.compareTo(BigInteger.ZERO) > 0 && s.compareTo(q) < 0) {
            // Calculate w = s^(-1) mod q
            BigInteger w = s.modInverse(q);

            // Calculate u1 = (message * w) mod q
            BigInteger u1 = message.multiply(w).mod(q);

            // Calculate u2 = (r * w) mod q
            BigInteger u2 = r.multiply(w).mod(q);

            // Calculate v = ((g^u1 * y^u2) mod p) mod q
            BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

            // Verify if v equals r
            return v.equals(r);
        }

        return false;
    }
}
