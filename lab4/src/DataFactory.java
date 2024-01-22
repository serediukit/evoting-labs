import java.util.ArrayList;
import java.util.List;

public class DataFactory {
    public static List<Candidate> getCandidates() {
        List<Candidate> candidates = new ArrayList<Candidate>();
        candidates.add(new Candidate("candidate1"));
        candidates.add(new Candidate("candidate2"));
        return candidates;
    }

    public static List<Voter> getVoters() {
        List<Voter> voters = new ArrayList<Voter>();
        voters.add(new Voter("A"));
        voters.add(new Voter("B"));
        voters.add(new Voter("C"));
        voters.add(new Voter("D"));
        return voters;
    }
}
