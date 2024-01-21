import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        List<Integer> testVotes = new ArrayList<>(asList(0, 1, 2, 0, 2, 1, 2, 2, 0, 1));
        int count = 0;

        List<Candidate> candidates = CandidateFactory.generateCandidates(3);
        List<Voter> voters = VoterFactory.generateVoters(10);
        voters.get(5).setCanVote(false);
        voters.get(7).setCanVote(false);

        ElectionCommission electionCommission = new ElectionCommission();
        RegistrationOffice registrationOffice = new RegistrationOffice(electionCommission);

        electionCommission.setCandidates(candidates);

        for (Voter voter : voters) {
            voter.setRegId(registrationOffice.registerVoter(voter));
            voter.setId(IDGenerator.generateID());
        }
//        voters.get(3).setRegId(registrationOffice.registerVoter(voters.get(3)));

        for (Voter voter : voters) {
            voter.createKeys();
            VoteMessage message = voter.getVoteMessage(testVotes.get(count));
            SignedEncryptedMessage signedEncryptedMessage = new SignedEncryptedMessage(message, voter.getElGamal(), voter.getDsa());
            electionCommission.sendMessage(signedEncryptedMessage);
            count++;
        }

        electionCommission.conductElection();
        electionCommission.printVotesList();
    }
}