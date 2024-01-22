import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = DataFactory.getCandidates();
        List<Voter> voters = DataFactory.getVoters();

        for (Voter voter : voters) {
            PublicKeys.addPublicKey(voter.getPublicKey());
        }

        for (Voter voter : voters) {
            voter.createBallot(new Random().nextInt(2));
        }

        for (Voter voter : voters) {
            voter.encryptBallot(voters);
        }
    }
}