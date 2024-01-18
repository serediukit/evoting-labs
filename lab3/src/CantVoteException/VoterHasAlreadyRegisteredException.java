package CantVoteException;


public class VoterHasAlreadyRegisteredException extends Exception {
    public VoterHasAlreadyRegisteredException(String name) {
        super("Voter " + name + " has already registered");
    }
}
