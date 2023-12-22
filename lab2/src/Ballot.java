import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.Base64;

public class Ballot {
    private String data;
    private boolean isSigned = false;

    public Ballot(Voter voter, int vote) {
        data = encryptData(voter, voter.getId() + " " + vote);
    }

    private String encryptData(Voter voter, String data) {
        KeyPair keys = voter.getKeyPair();
        return Encryptor.encrypt(data, keys.getPublic());
    }

    public String getDecryptedData(PrivateKey key) {
        return Encryptor.decrypt(data, key);
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void makeSigned() {
        isSigned = true;
    }
}
