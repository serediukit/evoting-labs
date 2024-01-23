public class Ballot {
    public byte[] encryptedBytes;
    public int voterId;

    public Ballot(byte[] encryptedBytes, int voterId) {
        this.encryptedBytes = encryptedBytes;
        this.voterId = voterId;
    }
}
