package CantVoteException;

public class VoterHasAlreadyVotedException extends Exception{
    public VoterHasAlreadyVotedException() {
        super("This voter has already voted");
    }
}
