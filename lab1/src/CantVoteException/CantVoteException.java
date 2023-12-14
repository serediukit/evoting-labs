package CantVoteException;

public class CantVoteException extends Exception {
    public CantVoteException(String name) {
        super("The voter " + name + " can't vote");
    }
}
