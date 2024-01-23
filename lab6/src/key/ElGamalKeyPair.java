package key;

public class ElGamalKeyPair {
    public final ElGamalPrivateKey privateKey;
    public final ElGamalPublicKey publicKey;

    public ElGamalKeyPair(ElGamalPrivateKey privateKey, ElGamalPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
