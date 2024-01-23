public class Token {
    public final int voterId;
    public final ElGamalPublicKey elGamalPublicKey;
    public final BBSPublicKey bbsPublicKey;

    public Token(int voterId, ElGamalPublicKey elGamalPublic, BBSPublicKey bbsPublic) {
        this.voterId = voterId;
        this.elGamalPublicKey = elGamalPublic;
        this.bbsPublicKey = bbsPublic;
    }

    @Override
    public String toString() {
        return "Token{" +
                "voterId=" + voterId +
                ", elGamalPublicKey=" + elGamalPublicKey +
                ", bbsPublicKey=" + bbsPublicKey +
                "}\n";
    }
}
