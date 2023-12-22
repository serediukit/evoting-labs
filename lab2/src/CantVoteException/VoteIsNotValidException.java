package CantVoteException;

public class VoteIsNotValidException extends Exception {
    public VoteIsNotValidException(String name) {
        super("The " + name + "'s vote is not valid");
    }
}
