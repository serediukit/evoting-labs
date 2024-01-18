import java.math.BigInteger;
import java.security.*;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;

public class Test {
    public static void main(String[] args) {
        ElGamal elGamal = new ElGamal();

        // Приклад шифрування
        BigInteger plaintext = new BigInteger("12345");
        BigInteger[] ciphertext = elGamal.encrypt(plaintext);

        System.out.println("Plaintext: " + plaintext);
        System.out.println("Ciphertext (c1): " + ciphertext[0]);
        System.out.println("Ciphertext (c2): " + ciphertext[1]);

        // Приклад розшифрування
        BigInteger decryptedText = elGamal.decrypt(ciphertext);
        System.out.println("Decrypted Text: " + decryptedText);

        System.out.println("--------------------------------------------");

        try {
            DSA dsa = new DSA();

            // Повідомлення для підпису
            BigInteger message = new BigInteger("812");

            // Підпис
            byte[] signature = dsa.sign(message);
            if (signature != null) {
                System.out.println("Signature: " + new BigInteger(1, signature));

                // Перевірка підпису
                boolean isValid = dsa.verify(message, signature);
                System.out.println("Is Signature Valid? " + isValid);
            }
        } catch (Exception ignore) {}

    }
}
