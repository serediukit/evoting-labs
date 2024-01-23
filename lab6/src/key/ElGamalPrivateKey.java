package key;

import java.math.BigInteger;

public class ElGamalPrivateKey {
    public BigInteger x;
    public BigInteger p;

    public ElGamalPrivateKey(BigInteger x, BigInteger p) {
        this.x = x;
        this.p = p;
    }
}
