import java.math.BigInteger;
import java.security.*;

public class DSA {
    public static byte[] sign(BigInteger msg, PrivateKey privateKey) {
        byte[] message = String.valueOf(msg).getBytes();
        try {
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(privateKey);
            dsa.update(message);
            return dsa.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean verify(BigInteger msg, byte[] signature, PublicKey publicKey) {
        byte[] message = String.valueOf(msg).getBytes();
        try {
            Signature verifyDsa = Signature.getInstance("SHA256withDSA");
            verifyDsa.initVerify(publicKey);
            verifyDsa.update(message);
            return verifyDsa.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
