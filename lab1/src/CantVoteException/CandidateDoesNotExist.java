package CantVoteException;

public class CandidateDoesNotExist extends Exception {
    public CandidateDoesNotExist() {
        super("The candidate doesn't exist in CEC list");
    }
}
