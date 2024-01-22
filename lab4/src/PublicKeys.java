import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class PublicKeys {
    private static List<PublicKey> publicKeys = new ArrayList<>();

    public static void addPublicKey(PublicKey publicKey) {
        publicKeys.add(publicKey);
    }

    public static List<PublicKey> getPublicKeys() {
        return publicKeys;
    }
}
