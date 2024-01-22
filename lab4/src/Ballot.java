public class Ballot {
    private int voterId;
    private int candidateId;

    public Ballot(int voterId, int candidateId) {
        this.voterId = voterId;
        this.candidateId = candidateId;
    }

    public String getData() {
        return voterId + " votes for " + candidateId;
    }
}
