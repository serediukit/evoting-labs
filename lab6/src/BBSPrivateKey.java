import java.math.BigInteger;

public class BBSPrivateKey {
    public BigInteger p;
    public BigInteger q;

    public BBSPrivateKey(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
    }
}
