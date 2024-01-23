import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Candidate> candidates = DataFactory.getCandidates(4);
        List<Voter> voters = DataFactory.getVoters(10);

        ElectionCommission electionCommission = new ElectionCommission();
        electionCommission.setCandidates(candidates);
        RegistrationOffice registrationOffice = new RegistrationOffice(electionCommission);
        registrationOffice.generateId(voters.size());
        voters.forEach(voter -> registrationOffice.register(voter));
    }
}