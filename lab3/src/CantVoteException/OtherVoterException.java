package CantVoteException;

public class OtherVoterException extends Exception {
    public OtherVoterException(String name) {
        super(name + " voter is trying to vote for another voter");
    }
}
