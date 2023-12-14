package CantVoteException;

public class VoterHasAlreadyVotedException extends Exception{
    public VoterHasAlreadyVotedException(String name) {
        super("The voter " + name + " has already voted");
    }
}
