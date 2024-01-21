package CantVoteException;

public class IncorrectMessageException extends Exception {
    public IncorrectMessageException() {
        super("The message is incorrect");
    }
}
