package CantVoteException;

public class CandidateDoesNotExist extends Exception {
    public CandidateDoesNotExist(String name) {
        super("The candidate " + name + " doesn't exist in CEC list");
    }
}
