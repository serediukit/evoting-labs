package CantVoteException;

public class BallotIsNotSignedException extends Exception {
    public BallotIsNotSignedException() {
        super("The ballot is not signed");
    }
}
