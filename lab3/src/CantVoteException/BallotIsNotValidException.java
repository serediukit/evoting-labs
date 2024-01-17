package CantVoteException;

public class BallotIsNotValidException extends Exception {
    public BallotIsNotValidException(String name) {
        super("The Ballot of " + name + " is not valid");
    }
}
