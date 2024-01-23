import java.math.BigInteger;

public class BBSResult {
    public BigInteger[] encryptedMessage;
    public BigInteger x0;

    public BBSResult(BigInteger[] encryptedMessage, BigInteger x0) {
        this.encryptedMessage = encryptedMessage;
        this.x0 = x0;
    }
}
