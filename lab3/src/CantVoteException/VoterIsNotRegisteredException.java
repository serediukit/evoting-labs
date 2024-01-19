package CantVoteException;

public class VoterIsNotRegisteredException extends Exception{
    public VoterIsNotRegisteredException() {
        super("The voter is not registered");
    }
}
