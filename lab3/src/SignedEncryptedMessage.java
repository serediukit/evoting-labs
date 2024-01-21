import java.math.BigInteger;

import CantVoteException.IncorrectMessageException;

public class SignedEncryptedMessage {
    public BigInteger[] message;
    public ElGamal elGamal;
    public DSA dsa;
    public byte[] signature;

    public SignedEncryptedMessage(VoteMessage voteMessage, ElGamal elGamal, DSA dsa) {
        this.elGamal = elGamal;
        this.dsa = dsa;
        String msgData = voteMessage.toString();
        if (!msgData.equals("Invalid")) {
            BigInteger msg = new BigInteger(msgData);
            signature = dsa.sign(msg);
            message = elGamal.encrypt(msg);
        }
    }
}
