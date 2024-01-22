import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSA {
    public static String encrypt(String data, PublicKey key) {
        try {
            BigInteger m = new BigInteger(data);
            BigInteger e = ((RSAPublicKey) key).getPublicExponent();
            BigInteger n = ((RSAPublicKey) key).getModulus();
            return String.valueOf(m.modPow(e, n));
        } catch (Exception ignore) {}
        return null;
    }

    public static String decrypt(String data, PrivateKey key) {
        BigInteger m = new BigInteger(data);
        BigInteger d = ((RSAPrivateKey) key).getPrivateExponent();
        BigInteger n = ((RSAPrivateKey) key).getModulus();
        return String.valueOf(m.modPow(d, n));
    }
}
