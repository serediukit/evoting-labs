import java.nio.charset.StandardCharsets;
import java.util.*;

public class ElectionCommission {
    private List<Candidate> candidates;
    private Map<Integer, PrivateKey> voterPrivateKeys;
    private List<Ballot> ballots;

    public ElectionCommission() {
        voterPrivateKeys = new HashMap<>();
        ballots = new ArrayList<>();
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = new ArrayList<>(candidates);
    }

    public List<Token> getTokens(List<Integer> voterIds) {
        KeyPair keyPair = KeyPairGenerator.generateKeyPair();
        for (Integer id : voterIds)
            voterPrivateKeys.put(id, keyPair.privateKey);

        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < voterIds.size(); i++) {
            Token token = new Token(voterIds.get(i), keyPair.publicKey);
            tokens.add(token);
        }

        return tokens;
    }

    public void sendBallot(Ballot ballot) {
        ballots.add(ballot);
    }

    public void printResult() {
        Map<Candidate, Integer> votes = new HashMap<>();

        for (Ballot ballot : ballots) {
            PrivateKey privateKey = voterPrivateKeys.get(ballot.voterId);

            byte[] candidateBytes = Encryptor.apply(ballot.encryptedBytes, privateKey);
            String candidateStr = new String(candidateBytes, StandardCharsets.UTF_8);

            Candidate candidate = candidates
                    .stream()
                    .filter(c -> Objects.equals(c.toString(), candidateStr))
                    .findFirst()
                    .orElse(null);

            if (votes.containsKey(candidate)) {
                votes.put(candidate, votes.get(candidate) + 1);
            } else {
                votes.put(candidate, 1);
            }
        }

        for (Candidate candidate : candidates) {
            if (!votes.containsKey(candidate)) {
                votes.put(candidate, 0);
            }
        }

        System.out.println("ELECTION RESULTS");
        for (Candidate candidate : candidates) {
            System.out.println(candidate + " - " + votes.get(candidate));
        }
    }
}
