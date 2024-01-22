import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Voter {
    private int id;
    private String name;
    private Ballot[] ballots;
    private VoteMessage[] voteMessages;
    private KeyPair keyPair;

    public Voter(String name) {
        this.name = name;
        ballots = new Ballot[2];
        voteMessages = new VoteMessage[2];
        keyPair = Generator.generateDSAKeyPair();
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
        for (int i = 0; i < ballots.length; i++) {
            ballots[i] = new Ballot(String.valueOf(multipliers[i]));
        }
    }

    public void encryptBallots(PublicKey publicKey) {
        for (Ballot ballot : ballots) {
            ballot.setData(RSA.encrypt(ballot.getData(), publicKey));
        }
    }

    public void createVoteMessages() {
        for (int i = 0; i < ballots.length; i++) {
            voteMessages[i] = new VoteMessage(id, ballots[i]);
        }
    }

    public void signMessages() {
        for (VoteMessage message : voteMessages) {
            message.addSignature(keyPair);
        }
    }

    public VoteMessage[] getVoteMessages() {
        return voteMessages;
    }
}
