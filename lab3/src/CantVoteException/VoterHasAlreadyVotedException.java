package CantVoteException;

public class VoterHasAlreadyVotedException extends Exception{
    public VoterHasAlreadyVotedException() {
        super("The voter has already voted");
    }
}
