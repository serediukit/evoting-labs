package CantVoteException;

public class VoterDoesNotExist extends Exception {
    public VoterDoesNotExist() {
        super("The voter doesn't exist in CEC list");
    }
}
