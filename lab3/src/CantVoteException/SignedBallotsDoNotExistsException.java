package CantVoteException;

public class SignedBallotsDoNotExistsException extends Exception {
    public SignedBallotsDoNotExistsException(String name) {
        super("Signed ballots for " + name + " do not exists");
    }
}
