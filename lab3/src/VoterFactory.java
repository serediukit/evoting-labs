import java.util.ArrayList;
import java.util.List;

public class VoterFactory {
    public static List<Voter> generateVoters(int count) {
        List<Voter> voters = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            voters.add(VoterFactory.createVoter("voter " + i, true));
        }
        return voters;
    }

    public static Voter createVoter(String name, boolean canVote) {
        return new Voter(name, canVote);
    }
}
