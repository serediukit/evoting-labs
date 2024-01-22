import java.math.BigInteger;

public class Ballot {
    private int voterId;
    private int candidateId;
    private BigInteger[] signature;

    public Ballot(int voterId, int candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public String getData() {
        return voterId + " votes for " + candidateId;
    }

    public void addSignature(BigInteger[] signature) {
        this.signature = signature;
    }

    public BigInteger[] getSignatures() {
        return signature;
    }

    public void removeSign() {
        signature = null;
    }
}
