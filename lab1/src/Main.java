import java.security.KeyPair;
import java.security.KeyPairGenerator;

public class Main {
    public static void main(String[] args) {
        try {
            // Generate key pairs for voters
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair voterKeyPair1 = keyPairGenerator.generateKeyPair();
            KeyPair voterKeyPair2 = keyPairGenerator.generateKeyPair();
            KeyPair voterKeyPair3 = keyPairGenerator.generateKeyPair();
            KeyPair voterKeyPair4 = keyPairGenerator.generateKeyPair();
            KeyPair voterKeyPair5 = keyPairGenerator.generateKeyPair();
            KeyPair voterKeyPair6 = keyPairGenerator.generateKeyPair();

            // Create candidates
            Candidate candidate1 = new Candidate("Candidate A");
            Candidate candidate2 = new Candidate("Candidate B");

            // Create voters
            Voter voter1 = new Voter("Voter 1", voterKeyPair1);
            Voter voter2 = new Voter("Voter 2", voterKeyPair2, false);
            Voter voter3 = new Voter("Voter 3", voterKeyPair3);
            Voter voter4 = new Voter("Voter 4", voterKeyPair4);
            Voter voter5 = new Voter("Voter 5", voterKeyPair5);
            Voter voter6 = new Voter("Voter 6", voterKeyPair6, false);

            // Create and configure Central Election Commission
            CentralElectionCommission CEC = new CentralElectionCommission();
            CEC.addCandidate(candidate1);
            CEC.addCandidate(candidate2);
            CEC.addVoter(voter1);
            CEC.addVoter(voter2);
            CEC.addVoter(voter3);
            CEC.addVoter(voter4);
            CEC.addVoter(voter5);
            CEC.addVoter(voter6);

            CEC.makeVote(voter1, 0);
            CEC.makeVote(voter2, 0);
            CEC.makeVote(voter3, 0);
            CEC.makeVote(voter3, 0);
            CEC.makeVote(voter3, 0);
            CEC.makeVote(voter3, 0);
            CEC.printVotingStatus();
            CEC.makeVote(voter4, 1);
            CEC.makeVote(voter5, 0);
            CEC.makeVote(voter6, 1);

            // Conduct the election
            CEC.conductElection();

            System.out.println("Checking if voter has been counted: " + voter2.checkIfCounted());
            System.out.println("Checking if voter has been counted: " + voter3.checkIfCounted());
            System.out.println("Checking if voter has been counted: " + voter4.checkIfCounted());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}