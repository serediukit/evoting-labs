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

    @Override
    public String toString() {
        BigInteger id_ = id.multiply(BigInteger.TEN.pow(24));
        BigInteger regId_ = regId.multiply(BigInteger.TEN.pow(2));
        return (id_.add(regId_).add(new BigInteger(ballot.getData()))).toString();
    }
}
