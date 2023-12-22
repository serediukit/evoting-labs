import CantVoteException.OtherVoterException;
import CantVoteException.VoteIsNotValidException;
import CantVoteException.VoterHasAlreadyVotedException;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

class Voter {
    private final boolean canVote;
    private boolean hasVoted;
    private boolean hasCounted;
    private int id;
    private final String name;
    private final KeyPair keyPair;
    private int vote;
    private String encryptedVote;
    private ArrayList<ArrayList<Ballot>> ballotsExamples;
    private ArrayList<Ballot> signedBallots;

    public Voter(String name, KeyPair keyPair) {
        this.name = name;
        this.keyPair = keyPair;
        canVote = true;
        hasVoted = false;
        hasCounted = false;
        vote = -1;
    }

    public Voter(String name, KeyPair keyPair, boolean canVote) {
        this.name = name;
        this.keyPair = keyPair;
        this.canVote = canVote;
        hasVoted = false;
        hasCounted = false;
        vote = -1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public int getVote() {
        return vote;
    }

    public String getEncryptedVote() {
        return encryptedVote;
    }

    public void setEncryptedVote(String encryptedVote) {
        this.encryptedVote = encryptedVote;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public boolean canVote() {
        return canVote;
    }

    public void makeVote(int vote) throws VoterHasAlreadyVotedException, VoteIsNotValidException, OtherVoterException {
        if (!hasVoted && signedBallots != null) {
            if (signedBallots.get(vote).getDecryptedData(keyPair.getPrivate()) == null) {
                throw new OtherVoterException(name);
            }
            String[] data = signedBallots.get(vote).getDecryptedData(keyPair.getPrivate()).split(" ");
            if (Integer.parseInt(data[0]) == id && Integer.parseInt(data[1]) == vote) {
                this.vote = Integer.parseInt(data[1]);
                hasVoted = true;
            } else {
                throw new VoteIsNotValidException(name);
            }
        } else {
            throw new VoterHasAlreadyVotedException(this.name);
        }
    }

    public void makeCounted() {
        hasCounted = true;
    }

    public boolean checkIfCounted() {
        return hasCounted;
    }

    public void generateBallots(int examplesCount, int candidatesCount) {
        ballotsExamples = new ArrayList<>();
        for (int i = 0; i < examplesCount; i++) {
            ArrayList<Ballot> temp = new ArrayList<>();
            for (int j = 0; j < candidatesCount; j++)
                temp.add(new Ballot(this, j));
            ballotsExamples.add(temp);
        }
    }

    public ArrayList<ArrayList<Ballot>> getBallotsExamples() {
        return ballotsExamples;
    }

    public void setSignedBallots(ArrayList<Ballot> signedBallots) {
        this.signedBallots = signedBallots;
    }

    public boolean hasSignedBallots() {
        if (signedBallots == null) return false;
        return !signedBallots.isEmpty();
    }
}