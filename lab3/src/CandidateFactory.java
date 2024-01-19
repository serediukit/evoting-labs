import java.util.ArrayList;
import java.util.List;

public class CandidateFactory {
    public static List<Candidate> generateCandidates(int count) {
        List<Candidate> candidates = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            candidates.add(CandidateFactory.createCandidate("candidate" + i));
        }
        return candidates;
    }

    public static Candidate createCandidate(String name) {
        return new Candidate(name);
    }
}
