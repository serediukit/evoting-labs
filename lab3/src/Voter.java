import java.math.BigInteger;

public class Voter {
    private boolean canVote;
    private BigInteger id;
    private BigInteger regId;
    private final String name;
    private ElGamal elGamal;
    private DSA dsa;

    public Voter(String name, boolean canVote) {
        this.name = name;
        this.canVote = canVote;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setRegId(BigInteger regId) {
        if (regId != null)
            this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public BigInteger getId() {
        return id;
    }

    public boolean canVote() {
        return canVote;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public VoteMessage getVoteMessage(int candidateId) {
        return new VoteMessage(id, regId, new Ballot(String.valueOf(candidateId)));
    }

    public void createKeys() {
        elGamal = new ElGamal();
        dsa = new DSA();
    }

    public ElGamal getElGamal() {
        return elGamal;
    }

    public DSA getDsa() {
        return dsa;
    }
}
