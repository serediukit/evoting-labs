package CantVoteException;

public class ExamplesDoesNotExistException extends Exception {
    public ExamplesDoesNotExistException() {
        super("The ballots examples doesn't exists");
    }
}
