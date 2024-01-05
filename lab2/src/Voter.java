import CantVoteException.OtherVoterException;
import CantVoteException.SignedBallotsDoNotExistsException;
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
    private ArrayList<ArrayList<Ballot>> ballotsExamples;
    private ArrayList<Ballot> signedBallots;

    public Voter(String name, KeyPair keyPair) {
        this.name = name;
        this.keyPair = keyPair;
        canVote = true;
        hasVoted = false;
        hasCounted = false;
    }

    public Voter(String name, KeyPair keyPair, boolean canVote) {
        this.name = name;
        this.keyPair = keyPair;
        this.canVote = canVote;
        hasVoted = false;
        hasCounted = false;
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


    public void vote() {
        hasVoted = true;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public boolean canVote() {
        return canVote;
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
            for (int j = 0; j < candidatesCount; j++) {
                Ballot tempBallot = new Ballot(this, j);
                tempBallot.encrypt(keyPair.getPublic());
                temp.add(tempBallot);
            }
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

    public void makeSignedBallotsDecrypted() {
        for (Ballot ballot : signedBallots) {
            ballot.decrypt(keyPair.getPrivate());
        }
    }

    public Ballot chooseSignedBallotWithCandidate(int candidate) {
        Ballot res = signedBallots.get(candidate);
        try {
            if (Integer.parseInt(res.getData().split(" ")[0]) != id)
                throw new SignedBallotsDoNotExistsException(name);
            return res;
        } catch (SignedBallotsDoNotExistsException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}