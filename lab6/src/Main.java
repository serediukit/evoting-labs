import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = DataFactory.getCandidates(4);
        List<Voter> voters = DataFactory.getVoters(10);
//        voters.get(4).canVote = false;

        ElectionCommission electionCommission = new ElectionCommission();
        electionCommission.setCandidates(candidates);
        RegistrationOffice registrationOffice = new RegistrationOffice(electionCommission);
        registrationOffice.generateId(voters.size());
        voters.forEach(registrationOffice::register);

        Application.electionCommission = electionCommission;
        Application.registrationOffice = registrationOffice;

        for (Voter voter : voters) {
            voter.install();
            voter.vote(candidates.get((int) (Math.random() * candidates.size())));
            if (voters.indexOf(voter) == 3) {
                voter.vote(candidates.get((int) (Math.random() * candidates.size())));
            }
        }

        electionCommission.printResult();
    }
}