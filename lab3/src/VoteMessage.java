public class VoteMessage {
    public final int id;
    public final int regId;
    public final Ballot ballot;

    public VoteMessage(int id, int regId, Ballot ballot) {
        this.id = id;
        this.regId = regId;
        this.ballot = ballot;
    }
}
