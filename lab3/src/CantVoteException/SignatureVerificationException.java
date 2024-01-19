package CantVoteException;

public class SignatureVerificationException extends Exception {
    public SignatureVerificationException() {
        super("The signature is not valid");
    }
}
