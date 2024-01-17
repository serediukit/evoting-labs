package CantVoteException;

public class VoterAlreadyHasSignedBallots extends Exception {
    public VoterAlreadyHasSignedBallots() {
        super("The voter has already signed ballots");
    }
}
