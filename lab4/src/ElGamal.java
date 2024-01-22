import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public BigInteger[] sign(String message) {
        BigInteger m = getHashedText(message);

        BigInteger k = new BigInteger(512, new SecureRandom());

        BigInteger r = g.modPow(k, p);

        BigInteger s = m.multiply(y.modPow(k, p)).mod(p);

        return new BigInteger[]{r, s};
    }

    public boolean verify(String message, BigInteger[] ciphertext) {
        BigInteger m = getHashedText(message);

        BigInteger r = ciphertext[0];
        BigInteger s = ciphertext[1];

        if (
                r.compareTo(BigInteger.ZERO) < 0
                || r.compareTo(p) > 0
                || s.compareTo(BigInteger.ZERO) < 0
                || s.compareTo(p.subtract(BigInteger.ONE)) > 0)
            return false;

        BigInteger a = r.modPow(x, p);
        BigInteger b = a.modInverse(p);

        return s.multiply(b).mod(p).compareTo(m) == 0;
    }

    public boolean verifySign(String message, BigInteger[] ciphertext) {
        return true;
    }

    private BigInteger getHashedText(String message) {
        byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
        int sum = getSum(bytes);
        return new BigInteger(String.valueOf(sum));
    }

    private int getSum(byte[] bytes) {
        int sum = 0;
        for (byte aByte : bytes) sum += (int) aByte;
        return sum;
    }
}
