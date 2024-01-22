import java.math.BigInteger;
import java.security.*;
import java.util.*;

public class CentralElectionCommission {
    private List<Candidate> candidates;
    private List<Voter> voters;
    private KeyPair keyPair;
    private Map<Integer, String> votes;

    public CentralElectionCommission() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.genKeyPair();
            votes = new HashMap<>();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
        candidates.forEach(candidate -> candidate.setId(Generator.generateNonPrimeId()));
    }

    public void setVoters(List<Voter> voters) {
        this.voters = new ArrayList<>(voters);
        voters.forEach(voter -> voter.setId(Generator.generateNonPrimeId()));
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public void sendVotesListFromElectionCommissions(List<VoteMessage> firstPart, List<VoteMessage> secondPart) {
        for (VoteMessage message : firstPart) {
            VoteMessage secondMessage = findMessageById(message.voterId, secondPart);
            if (secondMessage != null) {
                BigInteger firstPartId = new BigInteger(message.ballot.getData());
                BigInteger secondPartId = new BigInteger(secondMessage.ballot.getData());
                BigInteger data = firstPartId.multiply(secondPartId);
                String dataToDecrypt = String.valueOf(data);
                String decryptedData = RSA.decrypt(dataToDecrypt, keyPair.getPrivate());
                Candidate candidate = findCandidate(decryptedData);
                if (candidate != null) {
                    if (Objects.requireNonNull(findVoter(message.voterId)).canVote) {
                        votes.put(message.voterId, dataToDecrypt);
                        candidate.incrementVotes();
                    }
                    else {
                        System.out.printf("The voter with id %d can't vote\n", message.voterId);
                    }
                } else {
                    System.out.printf("Candidate %s not found\n", decryptedData);
                }
            } else {
                System.out.println("Voter send only one part");
            }
        }
    }

    private VoteMessage findMessageById(int id, List<VoteMessage> messages) {
        for (VoteMessage message : messages) {
            if (message.voterId == id) {
                return message;
            }
        }
        return null;
    }

    private Candidate findCandidate(String data) {
        for (Candidate candidate : candidates) {
            if (candidate.getId() == Integer.parseInt(data)) {
                return candidate;
            }
        }
        return null;
    }

    private Voter findVoter(int id) {
        for (Voter voter : voters) {
            if (voter.getId() == id) {
                return voter;
            }
        }
        return null;
    }

    public void printResult() {
        System.out.println("\n+------------------+--------------+");
        System.out.println("|        ELECTION  RESULTS        |");
        System.out.println("+------------------+--------------+");
        System.out.println("|    CANDIDATES    |     VOTES    |");
        System.out.println("+------------------+--------------+");
        for (Candidate candidate : candidates) {
            System.out.printf("| %16s | %12d |\n", candidate.getName(), candidate.getVotes());
        }
        System.out.println("+------------------+--------------+\n");
    }
}
