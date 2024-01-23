import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = DataFactory.getCandidates(4);
        List<Voter> voters = DataFactory.getVoters(10);

        ElectionCommission electionCommission = new ElectionCommission();
        electionCommission.setCandidates(candidates);
        Program.electionCommission = electionCommission;
        RegistrationOffice registrationOffice = new RegistrationOffice(electionCommission);
        registrationOffice.generateId(voters.size());
        voters.forEach(registrationOffice::register);

        for (Voter voter : voters) {
            voter.install();
            voter.vote(candidates.get((int) (Math.random() * candidates.size())));
        }

        electionCommission.printResult();
    }
}