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
    }

    public BigInteger getMessage() {
        BigInteger res = new BigInteger("0");
        for (BigInteger bigInteger : encryptedMessage) {
            res = res.add(bigInteger);
            res = res.multiply(new BigInteger("100"));
        }
        res = res.multiply(BigInteger.TEN.pow(40));
        res = res.add(x0);
        res = res.multiply(BigInteger.TEN.pow(10));
        res = res.add(BigInteger.valueOf(id));
        return res;
    }
}
