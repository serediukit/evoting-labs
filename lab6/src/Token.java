public class Token {
    public final int voterId;
    public final PublicKey publicKey;

    public Token(int voterId, PublicKey publicKey) {
        this.voterId = voterId;
        this.publicKey = publicKey;
    }

    @Override
    public String toString() {
        return "Token{" +
                "voterId=" + voterId +
                ", publicKey=" + publicKey +
                "}\n";
    }
}
