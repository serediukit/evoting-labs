import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = CandidateFactory.generateCandidates(3);
        List<Voter> voters = VoterFactory.generateVoters(10);

        RegistrationOffice registrationOffice = new RegistrationOffice();
        for (Voter voter : voters) {
            voter.setRegId(registrationOffice.registerVoter(voter));
        }

        ElectionCommission electionCommission = new ElectionCommission(registrationOffice.getRegistrationList());
    }
}