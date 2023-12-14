package CantVoteException;

public class CantVoteException extends Exception {
    public CantVoteException() {
        super("The voter can't vote");
    }
}
