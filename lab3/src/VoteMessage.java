import java.math.BigInteger;

public class VoteMessage {
    public final BigInteger id;
    public final BigInteger regId;
    public final Ballot ballot;

    public VoteMessage(BigInteger id, BigInteger regId, Ballot ballot) {
        this.id = id;
        this.regId = regId;
        this.ballot = ballot;
    }
}
