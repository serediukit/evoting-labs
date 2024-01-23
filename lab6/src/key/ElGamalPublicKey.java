package key;

import java.math.BigInteger;

public class ElGamalPublicKey {
    public BigInteger p;
    public BigInteger g;
    public BigInteger y;

    public ElGamalPublicKey(BigInteger p, BigInteger g, BigInteger y) {
        this.p = p;
        this.g = g;
        this.y = y;
    }
}
