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
}
