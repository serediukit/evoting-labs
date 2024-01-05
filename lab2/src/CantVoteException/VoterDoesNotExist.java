package CantVoteException;

public class VoterDoesNotExist extends Exception {
    public VoterDoesNotExist(String name) {
        super("The voter doesn't exist in CEC list");
    }
}
