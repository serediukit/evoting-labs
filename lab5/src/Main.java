import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] votes = new int[] { 1, 0, 2, 3, 2, 1, 2, 0, 2, 3 };

        List<Candidate> candidates = DataFactory.getCandidates(4);
        List<Voter> voters = DataFactory.getVoters(10);
        CentralElectionCommission centralElectionCommission = new CentralElectionCommission();
        centralElectionCommission.setCandidates(candidates);
        centralElectionCommission.setVoters(voters);
        for (int i = 0; i < votes.length; i++) {
            voters.get(i).createBallots(candidates.get(votes[i]).getId());
            voters.get(i).encryptBallots(centralElectionCommission.getPublicKey());
        }
    }
}