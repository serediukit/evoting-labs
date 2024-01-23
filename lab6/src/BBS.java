import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class BBS {
    public static BigInteger generateX(BigInteger n) {
        BigInteger x;
        do {
            x = getRandomCoprime(n);
        } while (gcd(x, n).compareTo(BigInteger.ZERO) == 0);
        return x;
    }

    public static BigInteger getRandomCoprime(BigInteger n) {
        BigInteger rand;
        do {
            rand = new BigInteger(n.bitLength(), new SecureRandom());
        } while (gcd(rand, n).compareTo(BigInteger.ONE) != 0 || rand.compareTo(n) > 0);
        return rand;
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.compareTo(BigInteger.ZERO) == 0) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    public static BigInteger squareModN(BigInteger a, BigInteger n) {
        return a.modPow(BigInteger.TWO, n);
    }

    public static BigInteger getBit(BigInteger a) {
        return a.testBit(0) ? BigInteger.ONE : BigInteger.ZERO;
    }

    public static BigInteger[] stringToBits(String str) {
        BigInteger[] bits = new BigInteger[str.length()];
        for (int i = 0; i < str.length(); i++) {
            bits[i] = BigInteger.valueOf(str.charAt(i));
        }
        return bits;
    }

    public static BBSResult encrypt(String str, PublicKey publicKey){
        BigInteger n = publicKey.n;
        BigInteger x = generateX(n);
        BigInteger x0 = squareModN(x, n);
        BigInteger[] bits = stringToBits(str);
        BigInteger xi = x0;
        BigInteger[] encryptedBits = new BigInteger[bits.length];
        for (int i = 0; i < bits.length; i++) {
            BigInteger mi = bits[i];
            xi = squareModN(xi, n);
            BigInteger bi = getBit(xi);
            encryptedBits[i] = mi.xor(bi);
        }
        return new BBSResult(encryptedBits, x0);
    }

    public static String decrypt(BigInteger[] bits, BigInteger x0, PrivateKey privateKey) {
        BigInteger p = privateKey.p;
        BigInteger q = privateKey.q;
        BigInteger n = p.multiply(q);
        List<BigInteger> result = new ArrayList<BigInteger>();
        BigInteger b0 = getBit(x0);
        BigInteger xi = x0;
        for (BigInteger mi : bits) {
            xi = squareModN(xi, n);
            BigInteger bi = getBit(xi);
            result.add(mi.xor(bi));
        }
        StringBuilder sb = new StringBuilder();
        for (BigInteger bigInteger : result) {
            sb.append((char) bigInteger.intValue());
        }
        return sb.toString();
    }
}
