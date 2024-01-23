import utils.BBSResult;
import utils.Credentials;

import java.math.BigInteger;

public class Application {
    public static ElectionCommission electionCommission;
    public static RegistrationOffice registrationOffice;
    private Token token;

    public Application(Credentials credentials, Token token) {
        if (registrationOffice.checkCredentials(credentials)) {
            this.token = token;
        } else {
            System.out.println("Incorrect credentials");
        }
    }

    public void vote(Candidate candidate) {
        BBSResult encryptResult = BBS.encrypt(String.valueOf(candidate.getId()), token.bbsPublicKey);
        VoteMessage voteMessage = new VoteMessage(encryptResult, token.voterId);
        BigInteger messageToEncrypt = voteMessage.getMessage();
        BigInteger[] encrypted = ElGamal.encrypt(messageToEncrypt, token.elGamalPublicKey);
        electionCommission.sendEncryptedBallot(encrypted);
    }
}
