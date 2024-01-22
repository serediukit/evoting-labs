import java.security.PublicKey;

public class Voter {
    private int id;
    private String name;
    private Ballot[] ballots;

    public Voter(String name) {
        this.name = name;
        ballots = new Ballot[2];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void createBallots(int candidateId) {
        int[] multipliers = Util.getMultipliers(candidateId);
        ballots[0] = new Ballot(String.valueOf(multipliers[0]));
        ballots[1] = new Ballot(String.valueOf(multipliers[1]));
    }

    public void encryptBallots(PublicKey publicKey) {

    }
}
