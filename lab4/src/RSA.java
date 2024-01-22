import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RSA {
    private static final Map<byte[], String> encodedData = new HashMap<>();

    public static byte[] encrypt(String data, PublicKey publicKey) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            sb.append(Util.getRandomChar());
        }
        byte[] result =  sb.toString().getBytes(StandardCharsets.UTF_8);
        encodedData.put(result, data);
        return result;
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) {
        return encodedData.get(data);
    }

    public static void printData() {
        for (byte[] key : encodedData.keySet()) {
            System.out.println(new String(key, StandardCharsets.UTF_8) + " : " + encodedData.get(key));
        }
    }
}
