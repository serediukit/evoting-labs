import java.math.BigInteger;
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
        BBSResult encryptResult = BBS.encrypt(String.valueOf(candidate.getId()), token.publicKey);
        VoteMessage voteMessage = new VoteMessage(encryptResult, token.voterId);

    }
}
