import java.math.BigInteger;

public class Voter {
    private boolean canVote;
    private boolean hasVoted;
    private boolean hasCounted;
    private int id;
    private BigInteger regId;
    private String name;

    public Voter(String name, boolean canVote) {
        this.name = name;
        this.canVote = canVote;
        this.hasVoted = false;
        this.hasCounted = false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRegId(BigInteger regId) {
        this.regId = regId;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
