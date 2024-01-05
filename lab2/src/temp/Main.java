package temp;

import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;

class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keys = keyPairGenerator.generateKeyPair();
//        System.out.println(keys.getPublic());
//        System.out.println(((RSAPublicKey) keys.getPublic()).getModulus());
//        System.out.println(((RSAPublicKey) keys.getPublic()).getPublicExponent());
//        System.out.println();
//        System.out.println(keys.getPrivate());
//        System.out.println(((RSAPrivateKey) keys.getPrivate()).getModulus());
//        System.out.println(((RSAPrivateKey) keys.getPrivate()).getPrivateExponent());
//
//        BigInteger r = new BigInteger(new byte[] {5});
//        System.out.println(r);

        BigInteger m1 = new BigInteger("423");
        System.out.println("m: " + m1);
        BigInteger r = Encryptor.findR(keys.getPublic());
        System.out.println("r: " + r);
        BigInteger m_ = Encryptor.getM_(m1.intValue(), keys.getPublic(), r);
        System.out.println("m_: " + m_);
        BigInteger s_ = Encryptor.getS_(m_, keys.getPrivate());
        System.out.println("s_: " + s_);
        BigInteger s = Encryptor.getS(s_, r, keys.getPublic());
        System.out.println("s: " + s);
        BigInteger m = Encryptor.getM(s, keys.getPublic());
        System.out.println("m: " + m);
        System.out.println("voterId: " + m.divide(BigInteger.TEN));
        System.out.println("vote: " + m.mod(BigInteger.TEN));
    }
}