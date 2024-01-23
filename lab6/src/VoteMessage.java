import java.math.BigInteger;
import java.util.Arrays;

public class VoteMessage {
    public BigInteger[] encryptedMessage;
    public BigInteger x0;
    public int id;

    public VoteMessage(BBSResult bbsResult, int id) {
        this.encryptedMessage = bbsResult.encryptedMessage;
        this.x0 = bbsResult.x0;
        this.id = id;
        System.out.println(Arrays.toString(encryptedMessage));
    }
}
