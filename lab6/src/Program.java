import java.nio.charset.StandardCharsets;

public class Program {
    public static ElectionCommission electionCommission;
    private Credentials credentials;
    private Token token;

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void vote(Candidate candidate) {
        byte[] candidateBytes = candidate.toString().getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = Encryptor.apply(candidateBytes, token.publicKey);
        electionCommission.sendBallot(new Ballot(encryptedBytes, token.voterId));
    }
}
