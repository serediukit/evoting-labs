import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectionCommission {
    private List<Candidate> candidates;
    private Map<Integer, PrivateKey> voterPrivateKeys;

    public ElectionCommission() {
        voterPrivateKeys = new HashMap<>();
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
}
