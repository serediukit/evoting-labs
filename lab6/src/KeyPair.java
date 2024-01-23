public class KeyPair {
    public final PrivateKey privateKey;
    public final PublicKey publicKey;

    public KeyPair(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
