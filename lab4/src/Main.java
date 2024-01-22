import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = DataFactory.getCandidates();
        List<Voter> voters = DataFactory.getVoters();

        Voter A = voters.get(0);
        Voter B = voters.get(1);
        Voter C = voters.get(2);
        Voter D = voters.get(3);

        for (Voter voter : voters) {
            PublicKeys.addPublicKey(voter.getPublicKey());
        }

        for (Voter voter : voters) {
            voter.createBallot(new Random().nextInt(2));
        }

        for (Voter voter : voters) {
            voter.encryptBallot(voters);
        }

        for (int i = 1; i < voters.size(); i++) {
            voters.get(i).sendBallotTo(A);
        }

        for (Voter voter : voters) {
            voter.decryptBallots();
            voter.shuffleBallots();
        }

        A.decryptBallotsWithOutDeleting();
        A.sign();
        A.shuffleBallots();
        A.sendBallots(B);
        A.sendBallots(C);
        A.sendBallots(D);

        B.verifySign();
        B.decryptBallotsWithOutDeleting();
        B.sign();
        B.shuffleBallots();
        B.sendBallots(A);
        B.sendBallots(C);
        B.sendBallots(D);

        C.verifySign();
        C.decryptBallotsWithOutDeleting();
        C.sign();
        C.shuffleBallots();
        C.sendBallots(A);
        C.sendBallots(B);
        C.sendBallots(D);

        D.verifySign();
        D.decryptBallotsWithOutDeleting();
        D.sign();
        D.shuffleBallots();
        D.sendBallots(A);
        D.sendBallots(B);
        D.sendBallots(C);

        A.verifySign();
        A.deleteFirstRandomString();

        for (String message : A.getMessages()) {
            System.out.println(message);
        }
    }
}