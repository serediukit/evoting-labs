import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.ArrayList;

import static java.util.Arrays.asList;

public class Main {
    public static void main(String[] args) {
        final int countOfExamples = 10;
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

            KeyPair CECKeyPair = keyPairGenerator.generateKeyPair();

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

            ArrayList<Candidate> candidates = new ArrayList<>(asList(candidate1, candidate2));
            ArrayList<Voter> voters = new ArrayList<>(asList(voter1, voter2, voter3, voter4, voter5, voter6));

            // Create and configure Central Election Commission
            CentralElectionCommission CEC = new CentralElectionCommission(CECKeyPair);

            for (Candidate candidate : candidates)
                CEC.addCandidate(candidate);

            for (Voter voter : voters)
                CEC.addVoter(voter);

            for (Voter voter : voters) {
//                if (voter.getId() != 3) {
                    voter.generateBallots(countOfExamples, candidates.size(), CECKeyPair.getPublic());
                    voter.setSignedBallots(CEC.getSignedBallot(voter.getBallotsExamples(), voter.getR()));
//                }
            }
//            voter4.generateFakeBallots(countOfExamples, candidates.size(), CECKeyPair.getPublic());
//            voter4.setSignedBallots(CEC.getSignedBallot(voter4.getBallotsExamples(), voter4.getR()));

            Ballot voter1Ballot = voter1.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 0);
            voter1Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter1, voter1Ballot);
//            CEC.sendBallot(voter2, voter1Ballot);


            Ballot voter2Ballot = voter2.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 0);
            voter2Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter2, voter2Ballot);

            Ballot voter3Ballot = voter3.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 0);
            voter3Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter3, voter3Ballot);
            CEC.sendBallot(voter3, voter3Ballot);
            CEC.sendBallot(voter3, voter3Ballot);

            Ballot voter4Ballot = voter4.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 1);
            voter4Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter4, voter4Ballot);

            Ballot voter5Ballot = voter5.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 0);
            voter5Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter5, voter5Ballot);

            Ballot voter6Ballot = voter6.chooseSignedBallotWithCandidate(CECKeyPair.getPublic(), 0);
            voter6Ballot.encrypt(CECKeyPair.getPublic());
            CEC.sendBallot(voter6, voter6Ballot);

            // Conduct the election
            CEC.conductElection();

            System.out.println("Checking if " + voter2.getName() + " has been counted: " + voter2.checkIfCounted());
            System.out.println(CEC.getBallotFor(voter2).getData());
            System.out.println("Checking if " + voter3.getName() + " has been counted: " + voter3.checkIfCounted());
            System.out.println(CEC.getBallotFor(voter3).getData());
            System.out.println("Checking if " + voter4.getName() + " has been counted: " + voter4.checkIfCounted());
            System.out.println(CEC.getBallotFor(voter4).getData());

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}