import java.math.BigInteger;
import java.security.*;
import java.security.SignatureException;

public class DSA {

    private KeyPair keyPair;

    public DSA() {
        generateKeyPair();
    }

    private void generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA");
            keyGen.initialize(2048);
            keyPair = keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] sign(BigInteger msg) {
        byte[] message = String.valueOf(msg).getBytes();
        try {
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(keyPair.getPrivate());
            dsa.update(message);
            return dsa.sign();
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean verify(BigInteger msg, byte[] signature) {
        byte[] message = String.valueOf(msg).getBytes();
        try {
            Signature verifyDsa = Signature.getInstance("SHA256withDSA");
            verifyDsa.initVerify(keyPair.getPublic());
            verifyDsa.update(message);
            return verifyDsa.verify(signature);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            return false;
        }
    }
}
