import java.math.BigInteger;

public class PrivateKey {
    public BigInteger p;
    public BigInteger q;
    public BigInteger x;
    public BigInteger elp;

    public PrivateKey(BigInteger p, BigInteger q, BigInteger x, BigInteger elp) {
        this.p = p;
        this.q = q;
        this.x = x;
        this.elp = elp;
    }
}
