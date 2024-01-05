import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Random;

public class Encryptor {
    public static String encrypt(String data, PublicKey key) {
        try {
            BigInteger m = new BigInteger(data);
            BigInteger e = ((RSAPublicKey) key).getPublicExponent();
            BigInteger n = ((RSAPublicKey) key).getModulus();
            return String.valueOf(m.modPow(e, n));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String decrypt(String data, PrivateKey key) {
        try {
            BigInteger m = new BigInteger(data);
            BigInteger d = ((RSAPrivateKey) key).getPrivateExponent();
            BigInteger n = ((RSAPrivateKey) key).getModulus();
            return String.valueOf(m.modPow(d, n));
        } catch (Exception ignored) { }
        return null;
    }

    public static BigInteger findR(PublicKey key) {
        BigInteger n = ((RSAPublicKey) key).getModulus();
        BigInteger r = BigInteger.probablePrime(8, new Random());
        while (!r.gcd(n).equals(BigInteger.ONE)) r = BigInteger.probablePrime(8, new Random());
        return r;
    }

    public static BigInteger getM_(int data, PublicKey key, BigInteger r) {
        BigInteger m_ = new BigInteger(String.valueOf(data));
        BigInteger e = ((RSAPublicKey) key).getPublicExponent();
        BigInteger n = ((RSAPublicKey) key).getModulus();
        return m_.multiply(r.pow(e.intValue())).mod(n);
    }

    public static BigInteger getS_(BigInteger m_, PrivateKey key) {
        BigInteger d = ((RSAPrivateKey) key).getPrivateExponent();
        BigInteger n = ((RSAPrivateKey) key).getModulus();
        return m_.modPow(d, n);
    }

    public static BigInteger getS(BigInteger s_, BigInteger r, PublicKey key) {
        BigInteger n = ((RSAPublicKey) key).getModulus();
        return s_.multiply(r.modInverse(n)).mod(n);
    }

    public static BigInteger getM(BigInteger s, PublicKey key) {
        BigInteger e = ((RSAPublicKey) key).getPublicExponent();
        BigInteger n = ((RSAPublicKey) key).getModulus();
        return s.modPow(e, n);
    }
}
