import java.math.BigInteger;

public class SignedEncryptedMessage {
    public BigInteger[] message;
    public ElGamal elGamal;
    public DSA dsa;
    public byte[] signature;

    public SignedEncryptedMessage(VoteMessage voteMessage, ElGamal elGamal, DSA dsa) {
        this.elGamal = elGamal;
        this.dsa = dsa;
        BigInteger msg = new BigInteger(voteMessage.toString());
        signature = dsa.sign(msg);
        message = elGamal.encrypt(msg);
    }
}
