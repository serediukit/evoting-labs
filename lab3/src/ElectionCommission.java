import CantVoteException.*;

import java.math.BigInteger;
import java.util.*;

public class ElectionCommission {
    private List<Candidate> candidates;
    private List<BigInteger> registrationList;
    private final Map<BigInteger, Ballot> registeredVotes;

    public ElectionCommission() {
        registeredVotes = new TreeMap<>();
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public void sendRegistrationList(List<BigInteger> registrationList) {
        this.registrationList = new ArrayList<>(registrationList);
    }

    public void sendMessage(SignedEncryptedMessage signedEncryptedMessage) {
        try {
            BigInteger decryptedMessage = signedEncryptedMessage.elGamal.decrypt(signedEncryptedMessage.message);
            BigInteger id = decryptedMessage.divide(BigInteger.TEN.pow(24));
            BigInteger regId = decryptedMessage.mod(BigInteger.TEN.pow(24)).divide(BigInteger.TEN.pow(2));
            BigInteger ballot = decryptedMessage.mod(BigInteger.TEN.pow(2));
            VoteMessage message = new VoteMessage(
                    id,
                    regId,
                    new Ballot(ballot.toString())
            );
            if (signedEncryptedMessage.dsa.verify(decryptedMessage, signedEncryptedMessage.signature)) {
                if (checkVoteMessage(message)) {
                    int vote = Integer.parseInt(message.ballot.getData());
                    for (Candidate candidate : candidates) {
                        if (candidate.getId() == vote) {
                            registeredVotes.put(message.regId, message.ballot);
                            candidate.incrementVotes();
                            return;
                        }
                    }
                    throw new CandidateDoesNotExist("candidate" + vote);
                }
            } else {
                throw new SignatureVerificationException();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkVoteMessage(VoteMessage message) throws
            VoterIsNotRegisteredException,
            VoterHasAlreadyVotedException,
            CandidateDoesNotExist {
        if (!registrationList.contains(message.regId)) {
            throw new VoterIsNotRegisteredException();
        }
        if (registeredVotes.containsKey(message.regId)) {
            throw new VoterHasAlreadyVotedException();
        }
        if (!isCandidateInList(Integer.parseInt(message.ballot.getData()))) {
            throw new CandidateDoesNotExist("with id " + message.ballot.getData());
        }
        return true;
    }

    public void conductElection() {
        System.out.println("\n+------------------+--------------+");
        System.out.println("|        ELECTION  RESULTS        |");
        System.out.println("+------------------+--------------+");
        System.out.println("|    CANDIDATES    |     VOTES    |");
        System.out.println("+------------------+--------------+");
        for (Candidate candidate : candidates) {
            System.out.printf("| %16s | %12d |\n", candidate.getName(), candidate.getVotesCount());
        }
        System.out.println("+------------------+--------------+\n");
    }

    public void printVotesList() {
        System.out.println("\n+-----------------------+--------------+");
        System.out.println("|           COUNTED  BALLOTS           |");
        System.out.println("+-----------------------+--------------+");
        System.out.println("|    REGISTRATION ID    |    BALLOT    |");
        System.out.println("+-----------------------+--------------+");
        for (BigInteger regId : registeredVotes.keySet()) {
            System.out.printf("| %21s | %12s |\n", regId, registeredVotes.get(regId).getData());
        }
        System.out.println("+-----------------------+--------------+\n");
    }

    private boolean isCandidateInList(int candidateId) {
        for (Candidate candidate : candidates) {
            if (candidate.getId() == candidateId) {
                return true;
            }
        }
        return false;
    }
}
