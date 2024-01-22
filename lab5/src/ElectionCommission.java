import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ElectionCommission {
    List<VoteMessage> votes = new ArrayList<>();

    public void sendVoteMessage(VoteMessage voteMessage) {
        if (checkSignature(voteMessage)) {
            if (containsId(voteMessage)) {
                System.out.println("The vote message has been already sent");
            } else {
                votes.add(voteMessage);
            }
        }
    }

    private boolean checkSignature(VoteMessage voteMessage) {
        if (voteMessage.signature != null) {
            return DSA.verify(new BigInteger(voteMessage.toString()), voteMessage.signature, voteMessage.DSAPublicKey);
        }
        return false;
    }

    private boolean containsId(VoteMessage voteMessage) {
        for (VoteMessage vote : votes) {
            if (vote.voterId == voteMessage.voterId) {
                return true;
            }
        }
        return false;
    }

    public List<VoteMessage> getVotes() {
        return votes;
    }
}
