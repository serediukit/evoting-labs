import CantVoteException.VoterHasAlreadyVotedException;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

class Voter {
    private final boolean canVote;
    private boolean hasVoted;
    private boolean hasCounted;
    private int id;
    private final String name;
    private final KeyPair keyPair;
    private final byte[] key;
    private byte[] vote;
    private String encryptedVote;

    public Voter(String name, KeyPair keyPair) {
        this.name = name;
        this.keyPair = keyPair;
        canVote = true;
        hasVoted = false;
        hasCounted = false;
        key = getKeyFromRSAKey();
    }

    public Voter(String name, KeyPair keyPair, boolean canVote) {
        this.name = name;
        this.keyPair = keyPair;
        this.canVote = canVote;
        hasVoted = false;
        hasCounted = false;
        key = getKeyFromRSAKey();
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

    public void makeVote(int vote) throws VoterHasAlreadyVotedException {
        if (!hasVoted) {
            String voteString = Integer.toBinaryString(vote);
            this.vote = voteString.getBytes();
            for (int i = 0; i < this.vote.length; i++) {
                this.vote[i] -= 48;
                this.vote[i] = (byte) (this.vote[i] ^ key[i % key.length]);
            }
            hasVoted = true;
        } else {
            throw new VoterHasAlreadyVotedException(this.name);
        }
    }

    private byte[] getKeyFromRSAKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        byte[] res = publicKey.getModulus().toByteArray();
        for (int i = 0; i < res.length; i++) {
            res[i] %= 2;
        }
        return res;
    }

    public int getDecryptedGammaVote() {
        if (vote == null)
            return -1;
        if (vote.length == 0)
            return -1;
        byte[] temp = new byte[vote.length];
        for (int i = 0; i < vote.length; i++) {
            temp[i] = (byte) (vote[i] ^ key[i % key.length]);
            temp[i] += 48;
        }
        String res = new String(temp);
        return Integer.parseInt(res);
    }

    public void makeCounted() {
        hasCounted = true;
    }

    public boolean checkIfCounted() {
        return hasCounted;
    }
}