import java.nio.charset.StandardCharsets;

public class Program {
    public static ElectionCommission electionCommission;
    public static RegistrationOffice registrationOffice;
    private Token token;

    public Program(Credentials credentials, Token token) {
        if (registrationOffice.checkCredentials(credentials)) {
            this.token = token;
        } else {
            System.out.println("Incorrect credentials");
        }
    }

    public void vote(Candidate candidate) {
        byte[] candidateBytes = candidate.toString().getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = Encryptor.apply(candidateBytes, token.publicKey);
        electionCommission.sendBallot(new Ballot(encryptedBytes, token.voterId));
    }
}
