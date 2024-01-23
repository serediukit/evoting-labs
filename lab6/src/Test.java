import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        String data = "23532";
        BBSResult result = ElGamal.encrypt(data, keyPair.publicKey);
        String decrypted = ElGamal.decrypt(result.encryptedMessage, result.x0, keyPair.privateKey);
        System.out.println(decrypted);
    }
}
