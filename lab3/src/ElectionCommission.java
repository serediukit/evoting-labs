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
            String stringMessage = decryptedMessage.toString(16);
            String[] splitMessage = stringMessage.split(" ");
            VoteMessage message = new VoteMessage(
                    new BigInteger(splitMessage[0]),
                    new BigInteger(splitMessage[1]),
                    new Ballot(splitMessage[2])
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

    private boolean checkVoteMessage(VoteMessage message) throws VoterIsNotRegisteredException, VoterHasAlreadyVotedException {
        if (!registrationList.contains(message.regId)) {
            throw new VoterIsNotRegisteredException();
        }
        if (registeredVotes.containsKey(message.regId)) {
            throw new VoterHasAlreadyVotedException();
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
}
