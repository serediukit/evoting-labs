import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

public class Ballot {
    private String data;

    public Ballot(Voter voter, int id, int vote) {
        data = encryptData(voter, "" + id + vote);
    }

    private String encryptData(Voter voter, String data) {
        KeyPair keys = voter.getKeyPair();
        return Encryptor.encrypt(data, keys.getPublic());
    }

    public String getDecryptedData(PrivateKey key) {
        return Encryptor.decrypt(data, key);
    }
}
