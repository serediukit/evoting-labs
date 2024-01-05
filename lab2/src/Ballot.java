import java.security.PrivateKey;
import java.security.PublicKey;

public class Ballot {
    private String data = "";
    private boolean isSigned = false;

    public Ballot(Voter voter, int vote) {
        data = voter.getId() + "" + vote;
    }

    public Ballot(String data) {
        this.data = data;
    }

    public void encrypt(PublicKey key) {
        data = Encryptor.encrypt(data, key);
    }

    public void decrypt(PrivateKey key) {
        data = Encryptor.decrypt(data, key);
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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
