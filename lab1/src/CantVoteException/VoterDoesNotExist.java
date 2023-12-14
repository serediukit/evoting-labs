package CantVoteException;

public class VoterDoesNotExist extends Exception {
    public VoterDoesNotExist(String name) {
        super("The voter " + name + " doesn't exist in CEC list");
    }
}
