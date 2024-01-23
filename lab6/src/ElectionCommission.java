import key.BBSKeyPair;
import key.BBSPrivateKey;
import key.ElGamalKeyPair;
import key.KeyPairGenerator;
import utils.BBSResult;

import java.math.BigInteger;
import java.util.*;

public class ElectionCommission {
    private List<Candidate> candidates;
    private Map<Integer, BBSPrivateKey> voterPrivateKeys;
    private List<Ballot> ballots;
    private ElGamalKeyPair elGamalKeyPair;

    public ElectionCommission() {
        voterPrivateKeys = new HashMap<>();
        ballots = new ArrayList<>();
        elGamalKeyPair = KeyPairGenerator.generateElGamalKeyPair();
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public List<Token> getTokens(List<Integer> voterIds) {
        BBSKeyPair bbsKeyPair = KeyPairGenerator.generateBBSKeyPair();
        for (Integer id : voterIds)
            voterPrivateKeys.put(id, bbsKeyPair.privateKey);

        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < voterIds.size(); i++) {
            Token token = new Token(voterIds.get(i), elGamalKeyPair.publicKey, bbsKeyPair.publicKey);
            tokens.add(token);
        }

        return tokens;
    }

    public void sendEncryptedBallot(BigInteger[] encrypted) {
        VoteMessage voteMessage = getVoteMessageFromEncrypted(encrypted);
        String ballotData = BBS.decrypt(voteMessage.encryptedMessage, voteMessage.x0, voterPrivateKeys.get(voteMessage.id));
        Candidate candidate = candidates.stream().filter(c -> c.getId() == Integer.parseInt(ballotData)).findFirst().orElse(null);
        if (candidate != null) {
            if (!isVoterVotedBefore(voteMessage.id)) {
                ballots.add(new Ballot(voteMessage.encryptedMessage, voteMessage.id));
                candidate.incrementVotes();
            } else {
                System.out.printf("The voter with id %d has already voted\n", voteMessage.id);
            }
        } else {
            System.out.println("The candidate doesn't exist");
        }
    }

    private VoteMessage getVoteMessageFromEncrypted(BigInteger[] encrypted) {
        BigInteger decryptedInteger = ElGamal.decrypt(encrypted, elGamalKeyPair.privateKey);
        BigInteger id = decryptedInteger.mod(BigInteger.TEN.pow(10));
        decryptedInteger = decryptedInteger.divide(BigInteger.TEN.pow(10));
        BigInteger x0 = decryptedInteger.mod(BigInteger.TEN.pow(42));
        decryptedInteger = decryptedInteger.divide(BigInteger.TEN.pow(42));
        int length = (int) Math.ceil((double) String.valueOf(decryptedInteger).length() / 2);
        BigInteger[] encryptedMessage = new BigInteger[length];
        for (int i = length - 1; i >= 0; i--) {
            encryptedMessage[i] = decryptedInteger.mod(new BigInteger("100"));
            decryptedInteger = decryptedInteger.divide(new BigInteger("100"));
        }
        return new VoteMessage(new BBSResult(encryptedMessage, x0), id.intValue());
    }

    private boolean isVoterVotedBefore(int voterId) {
        for (Ballot ballot : ballots) {
            if (ballot.voterId == voterId)
                return true;
        }
        return false;
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
