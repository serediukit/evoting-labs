import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    public static List<Candidate> getCandidates(int amount) {
        List<Candidate> candidates = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            candidates.add(new Candidate("Candidate " + i));
        }
        return candidates;
    }

    public static List<Voter> getVoters(int amount) {
        List<Voter> voters = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            voters.add(new Voter("Voter " + i));
        }
        return voters;
    }
}
