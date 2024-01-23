public class BBSKeyPair {
    public final BBSPrivateKey privateKey;
    public final BBSPublicKey publicKey;

    public BBSKeyPair(BBSPrivateKey privateKey, BBSPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }
}
