import java.math.BigInteger;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class VoteMessage {
    public int voterId;
    public Ballot ballot;
    public byte[] signature;
    public PublicKey DSAPublicKey;

    public VoteMessage(int voterId, Ballot ballot) {
        this.voterId = voterId;
        this.ballot = ballot;
    }

    public void addSignature(KeyPair keyPair) {
        signature = DSA.sign(new BigInteger(toString()), keyPair.getPrivate());
        DSAPublicKey = keyPair.getPublic();
    }

    @Override
    public String toString() {
        return voterId + ballot.getData();
    }
}
