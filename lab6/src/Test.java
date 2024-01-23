import java.math.BigInteger;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        ElGamalKeyPair keyPair = KeyPairGenerator.generateElGamalKeyPair();
        String data = "23532";
        BigInteger[] result = ElGamal.encrypt(new BigInteger(data), keyPair.publicKey);
        String decrypted = String.valueOf(ElGamal.decrypt(result, keyPair.privateKey));
        System.out.println(decrypted);
    }
}
