package CantVoteException;

public class OtherVoterException extends Exception {
    public OtherVoterException() {
        super("Another voter is trying to vote");
    }
}
