import java.math.BigInteger;

public class Ballot {
    public BigInteger[] encryptedMessage;
    public int voterId;

    public Ballot(BigInteger[] encryptedMessage, int voterId) {
        this.encryptedMessage = encryptedMessage;
        this.voterId = voterId;
    }
}
