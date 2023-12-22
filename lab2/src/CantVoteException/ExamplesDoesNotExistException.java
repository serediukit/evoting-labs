package CantVoteException;

public class ExamplesDoesNotExistException extends Exception {
    public ExamplesDoesNotExistException(String name) {
        super("The ballots examples for " + name + " doesn't exists");
    }
}
