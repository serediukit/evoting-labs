package CantVoteException;

public class BallotIsNotValidException extends Exception {
    public BallotIsNotValidException(String data) {
        super("The Ballot with data " + data + " is not valid");
    }
}
