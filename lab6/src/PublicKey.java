import java.math.BigInteger;

public class PublicKey {
    public BigInteger n;
    public BigInteger p;
    public BigInteger g;
    public BigInteger y;


    public PublicKey(BigInteger n, BigInteger p, BigInteger g, BigInteger y) {
        this.n = n;
        this.p = p;
        this.g = g;
        this.y = y;
    }
}
