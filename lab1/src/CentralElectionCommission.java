import java.security.*;
import java.util.*;

import CantVoteException.*;

import javax.crypto.*;

class CentralElectionCommission {
    private Integer candidatesCount = 0;
    private Integer votersCount = 0;
    private Map<Integer, Candidate> candidates;
    private Map<Integer, Voter> voters;

    public CentralElectionCommission() {
        candidates = new HashMap<>();
        voters = new HashMap<>();
    }

    public void addCandidate(Candidate candidate) {
        candidates.put(candidatesCount, candidate);
        candidatesCount++;
    }

    public void addVoter(Voter voter) {
        voters.put(votersCount, voter);
        voter.setId(votersCount);
        votersCount++;
    }

    public void conductElection() {
        // Election phase: Voters cast their votes
        for (Voter voter : voters.values()) {
            if (voter.getEncryptedGammaVote() != -1) {
                System.out.println("Voter: " + voter.getName() + " is casting vote...");
                String vote = "Vote for " + voter.getEncryptedGammaVote(); // Assume all votes are for the first candidate
                String encryptedVote = encryptVote(vote, voter.getKeyPair().getPublic());
                voter.setEncryptedVote(encryptedVote);
            }
        }

        // Election phase: Central Election Commission counts and announces results
        List<Integer> candidateVotes = Collections.nCopies(candidatesCount, 0);
        for (Voter voter : voters.values()) {
            String decryptedVote = decryptVote(voter.getEncryptedVote(), voter.getKeyPair().getPrivate());
            if (isValidVote(decryptedVote) && decryptedVote != null) {
                int index = Integer.parseInt(decryptedVote);
                candidateVotes.set(index, candidateVotes.get(index) + 1);
            } else {
                System.out.println("Invalid vote detected for voter: " + voter.getName());
            }
        }

        System.out.println("Election Results:");
        for (int i = 0; i < candidatesCount; i++) {
            System.out.println("Candidate: " + candidates.get(i) + "    ---->    votes: " + candidateVotes.get(i));
        }
    }

    public void makeVote(Voter voter, int vote) {
        try {
            if (!voters.containsValue(voter)) {
                throw new VoterDoesNotExist();
            }
            if (!candidates.containsKey(vote)) {
                throw new CandidateDoesNotExist();
            }
            if (!voter.canVote()) {
                throw new CantVoteException();
            }
            if (!voters.get(voter.getId()).equals(voter)) {
                throw new OtherVoterException();
            }
            voter.makeVote(vote);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String encryptVote(String vote, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] encryptedBytes = cipher.doFinal(vote.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private String decryptVote(String encryptedVote, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedVote);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private boolean isValidVote(String decryptedVote) {
        // Implement validation logic (e.g., check if the vote is for a valid candidate)
        return true; // Placeholder implementation
    }
}