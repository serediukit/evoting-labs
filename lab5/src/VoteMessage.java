import java.math.BigInteger;

public class VoteMessage {
    public int voterId;
    public Ballot ballot;
    public BigInteger[] signature;

    public VoteMessage(int voterId, Ballot ballot) {
        this.voterId = voterId;
        this.ballot = ballot;
    }
}
